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
import com.appler.riverchiefsystem.bean.UserBean;
import com.appler.riverchiefsystem.presenter.ChargeRiversPresenter;
import com.appler.riverchiefsystem.ui.adapter.ContactsListAdapter;
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

public class ContactsActivity extends BaseActivity implements ChargeRiversView {

private String TAG = getClass().getSimpleName();
    @BindView(R.id.iv_toolbar_back)
    ImageView ivToolbarBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_title;
    @BindView(R.id.sv_search)
    SearchView sv_search;
    @BindView(R.id.rv_contacts_list)
    RecyclerView rvContactsList;
    @BindView(R.id.sl_contacts_list)
    StateLayout slContactsList;
    @BindView(R.id.srl_contacts)
    SmartRefreshLayout refreshLayout;
    private ChargeRiversPresenter chargeRiversPresenter;
    private String keyword = "";
    private ContactsListAdapter contactsListAdapter;
    private List<UserBean.DataBean> contactsList = new ArrayList<>();

    @Override
    protected int bindLayout() {
        return R.layout.activity_contacts;
    }

    @Override
    protected void doBuissness(Context context) {

        chargeRiversPresenter = new ChargeRiversPresenter(this);
        tv_title.setText("通讯录");
        rvContactsList.setLayoutManager(new LinearLayoutManager(ContactsActivity.this));
        contactsListAdapter = new ContactsListAdapter(ContactsActivity.this, contactsList);
        rvContactsList.setAdapter(contactsListAdapter);

        initState();

        //下拉刷新 上拉加载
        initRefreshAndLoad();

        //模糊搜索
        initSearchView();
    }


    @OnClick(R.id.iv_toolbar_back)
    public void onViewClicked() {
        finish();
    }

    //模糊搜索
    private void initSearchView() {
        sv_search.setQueryHint("请输入人员名称搜索");
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
                List<UserBean.DataBean> searchList = new ArrayList<>();
                for (int i = 0; i < contactsList.size(); i++) {
                    String name = contactsList.get(i).getName();
                    if (name.contains(newText)) {
                        searchList.add(contactsList.get(i));
                    }
                }
                contactsList.clear();
                contactsList.addAll(searchList);
                contactsListAdapter.notifyDataSetChanged();
                return false;
            }
        });
        sv_search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                tv_title.setVisibility(View.VISIBLE);
                initState();
                tv_title.setText("通讯录");
                return false;
            }
        });
    }

    /**
     * 判断网络
     */
    private void initState() {
        if (!CommonUtils.isNetConnected(ContactsActivity.this)) {
            slContactsList.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
            slContactsList.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                @Override
                public void refreshClick() {
                    if (!CommonUtils.isNetConnected(ContactsActivity.this)) {
                        ToastUtils.showToast(ContactsActivity.this, "请确保网络连接正常~");
                    } else {
                        contactsList.clear();
                        slContactsList.showContentView();
                        requestContactsList();
                    }
                }

                @Override
                public void loginClick() {

                }
            });
        } else {
            contactsList.clear();
            slContactsList.showContentView();
            requestContactsList();

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

    }

    @Override
    public void getAllUsers(String string) {
        Log.i(TAG, "getAllUsers: " + string);
        contactsList.clear();
        try {
            JSONObject jsonObject = new JSONObject(string);
            String msg = jsonObject.getString("msg");
            if ("success".equals(msg)) {
                UserBean userBean = new Gson().fromJson(string, new TypeToken<UserBean>() {
                }.getType());
                List<UserBean.DataBean> data = userBean.getData();
                if (data.size() == 0) {
                    slContactsList.showEmptyView("", R.drawable.ic_no_data);
                } else {
                    slContactsList.showContentView();
                    contactsList.addAll(data);
                    contactsListAdapter.notifyDataSetChanged();
                }
            } else {
                ToastUtils.showShortToast(ContactsActivity.this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    private void requestContactsList() {
        chargeRiversPresenter.getAllUsers(FlagConstant.FLAG_CONTACTS_LIST, keyword);
    }
}
