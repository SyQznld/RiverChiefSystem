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
import com.appler.riverchiefsystem.bean.ChargeRiversData;
import com.appler.riverchiefsystem.dao.AdminDao;
import com.appler.riverchiefsystem.presenter.ChargeRiversPresenter;
import com.appler.riverchiefsystem.ui.adapter.ChargeRiversListAdapter;
import com.appler.riverchiefsystem.utils.CommonUtils;
import com.appler.riverchiefsystem.utils.ToastUtils;
import com.appler.riverchiefsystem.view.ChargeRiversView;
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

public class ChargeRiversActivity extends BaseActivity implements ChargeRiversView {
    private String TAG = getClass().getSimpleName();

    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_title;
    @BindView(R.id.sv_search)
    SearchView sv_search;
    @BindView(R.id.rv_chargeRivers)
    RecyclerView rvChargeRivers;
    @BindView(R.id.sl_chargeRivers)
    StateLayout slChargeRivers;
    @BindView(R.id.srl_chargeRivers)
    SmartRefreshLayout refreshLayout;
    private ChargeRiversPresenter chargeRiversPresenter;
    private String keyword = "";
    private ChargeRiversListAdapter chargeRiversListAdapter;
    private List<ChargeRiversData.DataBean> chargeRiverList = new ArrayList<>();


    @Override
    protected int bindLayout() {
        return R.layout.activity_charge_rivers;
    }

    @Override
    protected void doBuissness(Context context) {
        chargeRiversPresenter = new ChargeRiversPresenter(this);
        tv_title.setText("河湖信息");
        rvChargeRivers.setLayoutManager(new LinearLayoutManager(ChargeRiversActivity.this));
        chargeRiversListAdapter = new ChargeRiversListAdapter(ChargeRiversActivity.this, chargeRiverList);
        rvChargeRivers.setAdapter(chargeRiversListAdapter);

        initState();

        //下拉刷新 上拉加载
        initRefreshAndLoad();

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
                List<ChargeRiversData.DataBean> searchList = new ArrayList<>();
                for (int i = 0; i < chargeRiverList.size(); i++) {
                    String name = chargeRiverList.get(i).get河段名称();
                    if (name.contains(newText)) {
                        searchList.add(chargeRiverList.get(i));
                    }
                }
                chargeRiverList.clear();
                chargeRiverList.addAll(searchList);
                chargeRiversListAdapter.notifyDataSetChanged();
                return false;
            }
        });
        sv_search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                tv_title.setVisibility(View.VISIBLE);
                initState();
                tv_title.setText("负责河段");
                return false;
            }
        });
    }

    /**
     * 判断网络
     */
    private void initState() {
        if (!CommonUtils.isNetConnected(ChargeRiversActivity.this)) {
            slChargeRivers.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
            slChargeRivers.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                @Override
                public void refreshClick() {
                    if (!CommonUtils.isNetConnected(ChargeRiversActivity.this)) {
                        ToastUtils.showToast(ChargeRiversActivity.this, "请确保网络连接正常~");
                    } else {
                        chargeRiverList.clear();
                        requestChargeRiversList();
                        slChargeRivers.showContentView();
                    }
                }

                @Override
                public void loginClick() {

                }
            });
        } else {
            chargeRiverList.clear();
            requestChargeRiversList();
            slChargeRivers.showContentView();

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

    @Override
    public void getChargeRivers(String string) {
        Log.i(TAG, "getChargeRivers: " + string);
        chargeRiverList.clear();
        try {
            JSONObject jsonObject = new JSONObject(string);
            String msg = jsonObject.getString("msg");
            if ("success".equals(msg)) {
                ChargeRiversData chargeRiversData = new Gson().fromJson(string, new TypeToken<ChargeRiversData>() {
                }.getType());
                List<ChargeRiversData.DataBean> data = chargeRiversData.getData();
                if (data.size() == 0) {
                    slChargeRivers.showEmptyView("", R.drawable.ic_no_data);
                } else {
                    slChargeRivers.showContentView();
                    chargeRiverList.addAll(data);
                    chargeRiversListAdapter.notifyDataSetChanged();
                }
            } else {
                ToastUtils.showShortToast(ChargeRiversActivity.this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAllUsers(String string) {

    }

  

    @OnClick(R.id.iv_toolbar_back)
    public void onViewClicked() {
        finish();
    }
    private void requestChargeRiversList() {
        chargeRiversPresenter.getChargeRivers(FlagConstant.FLAG_CHARGE_RIVERS_LIST, AdminDao.getAdminID());
    }
}
