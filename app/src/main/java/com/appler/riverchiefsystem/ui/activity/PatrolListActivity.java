package com.appler.riverchiefsystem.ui.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.api.FlagConstant;
import com.appler.riverchiefsystem.base.BaseActivity;
import com.appler.riverchiefsystem.bean.ChargePersonData;
import com.appler.riverchiefsystem.bean.PatrolData;
import com.appler.riverchiefsystem.dao.AdminDao;
import com.appler.riverchiefsystem.dao.daoBean.AdminData;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.presenter.PatrolListPresenter;
import com.appler.riverchiefsystem.ui.adapter.PatrolListAdapter;
import com.appler.riverchiefsystem.utils.CommonUtils;
import com.appler.riverchiefsystem.utils.ToastUtils;
import com.appler.riverchiefsystem.utils.filter.FilterData;
import com.appler.riverchiefsystem.utils.filter.FilterEntity;
import com.appler.riverchiefsystem.utils.filter.FilterOneAdapter;
import com.appler.riverchiefsystem.utils.filter.FilterView;
import com.appler.riverchiefsystem.view.PatrolListView;
import com.fingdo.statelayout.StateLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PatrolListActivity extends BaseActivity implements PatrolListView {
    @BindView(R.id.iv_toolbar_back)
    ImageView iv_back;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_title;
    @BindView(R.id.iv_toolbar_imageview)
    ImageView iv_add;
    @BindView(R.id.sv_search)
    SearchView sv_search;
    @BindView(R.id.sl_patrol_list)
    StateLayout sl_patrol;
    @BindView(R.id.rv_patrol_list)
    RecyclerView rv_patrol;
    @BindView(R.id.srl_patrol)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.fv_patrol)
    FilterView filterView;
    private String TAG = getClass().getSimpleName();
    private PatrolListPresenter patrolListPresenter;
    private PatrolListAdapter patrolListAdapter;
    private List<PatrolData.DataBean> patrolList = new ArrayList<>();

    private FilterData filterData;
    private List<FilterEntity> filterPersonList = new ArrayList<>();

    private int staff_id;

    @Override
    protected int bindLayout() {
        AdminData admin = AdminDao.getAdmin();
        if (null != admin) {
            String roleName = admin.getRolename();
            if (!Global.ROLENAME_CUN_HEZHANG.equals(roleName)) {
                return R.layout.activity_patrol_list;
            } else {
                return R.layout.patrol_list_nofilter;
            }
        }
        return R.layout.activity_patrol_list;
    }

    @Override
    protected void doBuissness(Context context) {
        patrolListPresenter = new PatrolListPresenter(this);
        tv_title.setText("巡查记录");
        patrolListAdapter = new PatrolListAdapter(PatrolListActivity.this, patrolList);
        rv_patrol.setLayoutManager(new LinearLayoutManager(this));
        rv_patrol.setAdapter(patrolListAdapter);
        initState();

        //下拉刷新 上拉加载
        initRefreshAndLoad();

        AdminData admin = AdminDao.getAdmin();
        if (null != admin) {
            String roleName = admin.getRolename();
            if (!Global.ROLENAME_CUN_HEZHANG.equals(roleName)) {
                filterView.setVisibility(View.VISIBLE);
                //筛选条件 过滤
                initFilter();
            } else {
                filterView.setVisibility(View.GONE);
            }
        }

        //模糊搜索
        initSearchView();

    }


    //模糊搜索
    private void initSearchView() {
        sv_search.setQueryHint("请输入河段名称搜索");
        //修改searchView的文字颜色
        SearchView.SearchAutoComplete mSearchAutoComplete = sv_search.findViewById(R.id.search_src_text);
        //设置输入框内提示文字样式
        mSearchAutoComplete.setHintTextColor(getResources().getColor(android.R.color.white));//设置提示文字颜色
        mSearchAutoComplete.setTextColor(getResources().getColor(android.R.color.white));//设置内容文字颜色
        mSearchAutoComplete.setTextSize(15);//设置内容文字大小
        sv_search.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_title.setVisibility(View.GONE);
            }
        });
        sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<PatrolData.DataBean> searchList = new ArrayList<>();
                for (int i = 0; i < patrolList.size(); i++) {
                    String road_name = patrolList.get(i).getAddress();
                    if (road_name.contains(newText)) {
                        searchList.add(patrolList.get(i));
                    }
                }
                patrolList.clear();
                patrolList.addAll(searchList);
                patrolListAdapter.notifyDataSetChanged();
                return false;
            }
        });
        sv_search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                tv_title.setVisibility(View.VISIBLE);
                initState();
                tv_title.setText("巡查记录");
                return false;
            }
        });
    }

    @OnClick({R.id.iv_toolbar_back, R.id.iv_toolbar_imageview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:
                finish();
                break;
            case R.id.iv_toolbar_imageview:
                break;
        }
    }


    /**
     * 判断网络
     */
    private void initState() {
        if (!CommonUtils.isNetConnected(PatrolListActivity.this)) {
            sl_patrol.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
            sl_patrol.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                @Override
                public void refreshClick() {
                    if (!CommonUtils.isNetConnected(PatrolListActivity.this)) {
                        ToastUtils.showToast(PatrolListActivity.this, "请确保网络连接正常~");
                    } else {
                        patrolList.clear();
                        requestPatrolList();
                    }
                }

                @Override
                public void loginClick() {

                }
            });
        } else {
            patrolList.clear();
            requestPatrolList();
        }
    }


    /**
     * 下拉刷新上拉加载
     */
    private void initRefreshAndLoad() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //请求巡查列表数据
                initState();
                refreshLayout.finishRefresh();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
            }
        });
    }


    /**
     * 筛选条件
     */
    private void initFilter() {
        // 筛选数据
        filterData = new FilterData();
        filterPersonList.clear();
        filterPersonList.add(new FilterEntity("全部", String.valueOf(AdminDao.getAdminID())));
        filterData.setOrgFilter(filterPersonList);

        //人员设置默认显示
        FilterOneAdapter filterAdapter = new FilterOneAdapter(PatrolListActivity.this, filterPersonList);
        filterAdapter.setSelectedEntity(filterPersonList.get(0));

        filterView.setFilterData(PatrolListActivity.this, filterData);
        // (真正的)筛选视图点击
        filterView.setOnFilterClickListener(new FilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                filterView.show(position);
            }
        });


        //按人员
        filterView.setOnItemOrgFilterClickListener(new FilterView.OnItemOrgFilterClickListener() {
            @Override
            public void onItemOrgFilterClick(FilterEntity entity) {
                patrolList.clear();
                String key = entity.getKey();
                for (int i = 0; i < filterPersonList.size(); i++) {
                    String filterKey = filterPersonList.get(i).getKey();
                    if (filterKey.equals(key)) {
                        staff_id = Integer.parseInt(filterPersonList.get(i).getValue());
                    }
                }
                //请求巡查列表数据
                requestPatrolList();
            }
        });
    }


    /**
     * 巡查记录列表 请求接口返回
     */
    @Override
    public void getPatrolList(String string) {
        Log.i(TAG, "getPatrolList: " + string);
        patrolList.clear();
        try {
            JSONObject jsonObject = new JSONObject(string);
            String msg = jsonObject.getString("msg");
            if ("success".equals(msg)) {
                PatrolData patrolData = new Gson().fromJson(string, new TypeToken<PatrolData>() {
                }.getType());
                List<PatrolData.DataBean> data = patrolData.getData();
                if (data.size() == 0) {
                    sv_search.setVisibility(View.GONE);
                    sl_patrol.showEmptyView("", R.drawable.ic_no_data);
                } else {
                    sv_search.setVisibility(View.VISIBLE);
                    sl_patrol.showContentView();
                    patrolList.addAll(data);
                    patrolListAdapter.notifyDataSetChanged();
                }
            } else {
                ToastUtils.showShortToast(PatrolListActivity.this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //下属人员
    @Override
    public void getChargePerson(String string) {
        Log.i(TAG, "getChargePerson: " + string);

        try {
            JSONObject jsonObject = new JSONObject(string);
            String msg = jsonObject.getString("msg");
            if ("success".equals(msg)) {
                ChargePersonData chargePersonData = new Gson().fromJson(string, new TypeToken<ChargePersonData>() {
                }.getType());
                final List<ChargePersonData.DataBean> data = chargePersonData.getData();
                List<FilterEntity> list = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    String name = data.get(i).getFuzeren();
                    String id = data.get(i).getUser_id();
                    list.add(new FilterEntity(name, id));
                }
                filterPersonList.addAll(list);
            } else {
                ToastUtils.showShortToast(PatrolListActivity.this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //请求巡查列表数据
    private void requestPatrolList() {
        AdminData admin = AdminDao.getAdmin();
        if (null != admin) {
            staff_id = admin.getUserId();
            Log.i(TAG, "requestPatrolList: " + staff_id);
            String roleName = admin.getRolename();
            if (!Global.ROLENAME_CUN_HEZHANG.equals(roleName)) {
                patrolListPresenter.getChargePerson(FlagConstant.FLAG_CHARGE_PERSON, staff_id);
            }
            patrolListPresenter.getPatrolList(FlagConstant.FLAG_PATROL_LIST, staff_id, "");
        }
    }
}
