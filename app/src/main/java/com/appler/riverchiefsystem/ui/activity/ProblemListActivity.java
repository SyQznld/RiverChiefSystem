package com.appler.riverchiefsystem.ui.activity;

import android.content.Context;
import android.content.Intent;
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
import com.appler.riverchiefsystem.dao.daoBean.AdminData;
import com.appler.riverchiefsystem.bean.ProblemData;
import com.appler.riverchiefsystem.dao.AdminDao;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.presenter.ProblemListPresenter;
import com.appler.riverchiefsystem.ui.adapter.ProblemListAdapter;
import com.appler.riverchiefsystem.utils.CommonUtils;
import com.appler.riverchiefsystem.utils.ModelUtil;
import com.appler.riverchiefsystem.utils.ToastUtils;
import com.appler.riverchiefsystem.utils.filter.FilterData;
import com.appler.riverchiefsystem.utils.filter.FilterEntity;
import com.appler.riverchiefsystem.utils.filter.FilterOneAdapter;
import com.appler.riverchiefsystem.utils.filter.FilterView;
import com.appler.riverchiefsystem.view.ProblemListView;
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

public class ProblemListActivity extends BaseActivity implements ProblemListView {
    @BindView(R.id.iv_toolbar_back)
    ImageView iv_back;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_title;
    @BindView(R.id.iv_toolbar_imageview)
    ImageView iv_add;
    @BindView(R.id.sv_search)
    SearchView sv_search;
    @BindView(R.id.sl_problem_list)
    StateLayout sl_problem;
    @BindView(R.id.rv_problem_list)
    RecyclerView rv_problem;
    @BindView(R.id.srl_problem)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.fv_problem)
    FilterView filterView;
    private String TAG = getClass().getSimpleName();
    private ProblemListPresenter problemListPresenter;
    private ProblemListAdapter problemListAdapter;
    private List<ProblemData.DataBean> problemList = new ArrayList<>();
    private String problem_state = ""; //解决程度
    private String problemType = ""; //问题类型
    private String problem_belong = ""; //问题分类  接收/上报
    private FilterData filterData;
    private int flag;
    private String patrol_id = "";
    private String keyword = "";  //关键词


    @Override
    protected int bindLayout() {
        return R.layout.activity_problem_list;
    }

    @Override
    protected void doBuissness(Context context) {

        tv_title.setText("问题记录");
        iv_add.setVisibility(View.VISIBLE);
        iv_add.setImageResource(R.drawable.ic_add);

        String rolename = AdminDao.getAdmin().getRolename();
        //村/乡/镇河长 添加问题
        if (rolename.equals(Global.ROLENAME_CUN_HEZHANG)
                || rolename.equals(Global.ROLENAME_XIANG_HEZHANG)
                || rolename.equals(Global.ROLENAME_ZHEN_HEZHANG)) {
            iv_add.setVisibility(View.VISIBLE);
        } else {
            iv_add.setVisibility(View.GONE);
        }
//
//        problemListPresenter = new ProblemListPresenter(this);
//        flag = getIntent().getIntExtra("flag", 0);
//        patrol_id = getIntent().getStringExtra("patrol_id");
//        //接口返回问题列表数据
//        initState();




        rv_problem.setLayoutManager(new LinearLayoutManager(context));
        problemListAdapter = new ProblemListAdapter(context, problemList);
        rv_problem.setAdapter(problemListAdapter);


        //下拉刷新 上拉加载
        initRefreshAndLoad();

        //筛选条件 过滤
        initFilter();

        //模糊搜索
        initSearchView();


    }

    //模糊搜索
    private void initSearchView() {
        sv_search.setQueryHint("请输入问题描述搜索");
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
                List<ProblemData.DataBean> searchList = new ArrayList<>();
                for (int i = 0; i < problemList.size(); i++) {
                    String problem_descibe = problemList.get(i).get问题描述();
                    if (problem_descibe.contains(newText)) {
                        searchList.add(problemList.get(i));
                    }
                }
                problemList.clear();
                problemList.addAll(searchList);
                problemListAdapter.notifyDataSetChanged();
//                keyword = newText;
                return false;
            }
        });
        sv_search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                tv_title.setVisibility(View.VISIBLE);
                tv_title.setText("问题记录");

                //请求问题列表
                initState();
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
                Intent intent = new Intent(ProblemListActivity.this, AddProblemActivity.class);
                intent.putExtra("flag", flag);
                intent.putExtra("patrol_id", patrol_id);
                startActivity(intent);
                break;
        }
    }


    /**
     * 判断网络
     */
    private void initState() {
        if (!CommonUtils.isNetConnected(ProblemListActivity.this)) {
            sl_problem.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
            sl_problem.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                @Override
                public void refreshClick() {
                    if (!CommonUtils.isNetConnected(ProblemListActivity.this)) {
                        ToastUtils.showToast(ProblemListActivity.this, "请确保网络连接正常~");
                    } else {
                        problemList.clear();
                        requestProblemList();
                        sl_problem.showContentView();
                    }
                }

                @Override
                public void loginClick() {

                }
            });
        } else {
            problemList.clear();
            requestProblemList();
            sl_problem.showContentView();
        }
    }


    /**
     * 下拉刷新上拉加载
     */
    private void initRefreshAndLoad() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                AdminData admin = AdminDao.getAdmin();
                if (null != admin) {
                    //接口返回问题列表数据
                    initState();
                }
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
        List<FilterEntity> typesList = ModelUtil.getRiverProblemType();  //等级 河流类型
        List<FilterEntity> filterResoveList = ModelUtil.getProblemResove(); //解决程度
        List<FilterEntity> sortList = ModelUtil.getProblemBelong(); //分类 接收/上报
        filterData = new FilterData();
        filterData.setTypes(typesList);//分类  道路类型
        filterData.setFilters(filterResoveList);//阶段  问题解决程度
        if (flag == 0
                && !AdminDao.getAdmin().getRolename().equals(Global.ROLENAME_CUN_HEZHANG)
                && !AdminDao.getAdmin().getRolename().equals(Global.ROLENAME_SHI_HEZHANG)) {
            filterData.setSorts(sortList);//分类
        }
        filterView.setFilterData(ProblemListActivity.this, filterData);

        // (真正的)筛选视图点击
        filterView.setOnFilterClickListener(new FilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                filterView.show(position);
            }
        });

        //设置默认显示
        FilterOneAdapter typesAdapter = new FilterOneAdapter(ProblemListActivity.this, typesList);
        typesAdapter.setSelectedEntity(typesList.get(0));
        FilterOneAdapter filterAdapter = new FilterOneAdapter(ProblemListActivity.this, filterResoveList);
        filterAdapter.setSelectedEntity(filterResoveList.get(0));
        FilterOneAdapter sortAdapter = new FilterOneAdapter(ProblemListActivity.this, sortList);
        sortAdapter.setSelectedEntity(sortList.get(0));

        //问题类型
        filterView.setOnItemTypesClickListener(new FilterView.OnItemTypesClickListener() {
            @Override
            public void onItemTypesClick(FilterEntity entity) {
                problemList.clear();
                String value = entity.getValue();
                if ("0".equals(value)) { //
                    problemType = "";
                } else if ("1".equals(value)) {
                    problemType = "公示牌是否设置完整";
                } else if ("2".equals(value)) {
                    problemType = "水质是否清洁";
                } else if ("3".equals(value)) {
                    problemType = "岸边有无乱堆乱放现象";
                } else if ("4".equals(value)) {
                    problemType = "有无漂浮物";
                } else if ("5".equals(value)) {
                    problemType = "有无排污口";
                }
                //接口返回任务列表数据
                initState();
            }
        });


        filterView.setOnItemSortClickListener(new FilterView.OnItemSortClickListener() {
            @Override
            public void onItemSortClick(FilterEntity entity) {
                problemList.clear();
                String value = entity.getValue();
                if ("0".equals(value)) { //
                    problem_belong = "";
                } else if ("1".equals(value)) {
                    problem_belong = "上报";
                } else if ("2".equals(value)) {
                    problem_belong = "接收";
                }
                //接口返回任务列表数据
                initState();
            }
        });


        //问题解决程度           待处理、正在处理、已解决
        filterView.setOnItemFilterClickListener(new FilterView.OnItemFilterClickListener() {
            @Override
            public void onItemFilterClick(FilterEntity entity) {
                problemList.clear();
                String value = entity.getValue();
                if ("0".equals(value)) { //
                    problem_state = "";
                } else if ("1".equals(value)) {
                    problem_state = "待处理";
                } else if ("2".equals(value)) {
                    problem_state = "正在处理";
                } else if ("3".equals(value)) {
                    problem_state = "已解决";
                }
                //接口返回任务列表数据
                initState();
            }
        });
    }

    /**
     * 返回问题列表 请求接口测试
     */
    @Override
    public void getProblemList(String string) {
        Log.i(TAG, "getProblemList: " + string);
        problemList.clear();
        try {
            JSONObject jsonObject = new JSONObject(string);
            String msg = jsonObject.getString("msg");
            if ("success".equals(msg)) {
                ProblemData problemData = new Gson().fromJson(string, new TypeToken<ProblemData>() {
                }.getType());
                List<ProblemData.DataBean> data = problemData.getData();
                if (data.size() == 0) {
                    sl_problem.showEmptyView("", R.drawable.ic_no_data);
                } else {
                    sl_problem.showContentView();
                    problemList.addAll(data);
                    problemListAdapter.notifyDataSetChanged();
                }
            } else {
                ToastUtils.showShortToast(ProblemListActivity.this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void getPatrolProblemList(String string) {
        Log.i(TAG, "getPatrolProblemList: " + string);
        problemList.clear();
        try {
            JSONObject jsonObject = new JSONObject(string);
            String msg = jsonObject.getString("msg");
            if ("success".equals(msg)) {
                ProblemData problemData = new Gson().fromJson(string, new TypeToken<ProblemData>() {
                }.getType());
                List<ProblemData.DataBean> data = problemData.getData();
                if (data.size() == 0) {
                    sv_search.setVisibility(View.GONE);
                    sl_problem.showEmptyView("", R.drawable.ic_no_data);
                } else {
                    sv_search.setVisibility(View.VISIBLE);
                    sl_problem.showContentView();
                    problemList.addAll(data);
                    problemListAdapter.notifyDataSetChanged();
                }
            } else {
                ToastUtils.showShortToast(ProblemListActivity.this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();




        problemListPresenter = new ProblemListPresenter(this);
        flag = getIntent().getIntExtra("flag", 0);
        patrol_id = getIntent().getStringExtra("patrol_id");
        //接口返回问题列表数据
        initState();
    }


    //请求问题列表接口数据
    private void requestProblemList() {
        if (flag == 1) {  //巡查记录关联问题列表
            problemListPresenter.getPatrolProblemList(FlagConstant.FLAG_PATROL_PROBLEM_LIST, patrol_id, problem_state,
                    problemType,
                    keyword);
        } else {   //个人相关问题
            AdminData admin = AdminDao.getAdmin();
            if (null != admin) {
                problemListPresenter.getProblemList(
                        FlagConstant.FLAG_PROBLEM_LIST,
                        AdminDao.getAdminID(),
                        problem_state,
                        problemType,
                        problem_belong,
                        keyword);
            }
        }
    }

}
