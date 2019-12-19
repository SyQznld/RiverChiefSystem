package com.appler.riverchiefsystem.ui.fragment;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.api.FlagConstant;
import com.appler.riverchiefsystem.base.BaseFragment;
import com.appler.riverchiefsystem.dao.daoBean.AdminData;
import com.appler.riverchiefsystem.bean.NoticeData;
import com.appler.riverchiefsystem.dao.AdminDao;
import com.appler.riverchiefsystem.presenter.NotificationListPresenter;
import com.appler.riverchiefsystem.ui.activity.NoticeDetailActivity;
import com.appler.riverchiefsystem.ui.adapter.NoticeListAdapter;
import com.appler.riverchiefsystem.utils.CommonUtils;
import com.appler.riverchiefsystem.utils.ToastUtils;
import com.appler.riverchiefsystem.view.NotificationListView;
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

/**
 * 通知公告页面
 */
public class NotificationFragment extends BaseFragment implements NotificationListView {
    @BindView(R.id.srl_notification)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.sl_notification)
    StateLayout sl_notification;
    @BindView(R.id.rv_notification)
    RecyclerView rv_notification;
    private String TAG = getClass().getSimpleName();
    private NotificationListPresenter notificationListPresenter;
    private NoticeListAdapter noticeListAdapter;
    private List<NoticeData.DataBean> dataBeanList = new ArrayList<>();

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    protected int setFragmentView() {
        return R.layout.fragment_notification;
    }

    @Override
    protected void doFragmentBusiness() {
        notificationListPresenter = new NotificationListPresenter(this);

        rv_notification.setLayoutManager(new LinearLayoutManager(getActivity()));
        noticeListAdapter = new NoticeListAdapter(getActivity(), dataBeanList);
        rv_notification.setAdapter(noticeListAdapter);

        noticeListAdapter.setNoticeItemClickListener(new NoticeListAdapter.NoticeItemClickListener() {
            @Override
            public void noticeItemClick(int position, NoticeData.DataBean noticeData) {
                Intent intent = new Intent(getActivity(), NoticeDetailActivity.class);
                intent.putExtra("noticeId", noticeData.getId());
                getActivity().startActivity(intent);
            }
        });

        initState();

        //下拉刷新 上拉加载
        initRefreshAndLoad();
    }

    @Override
    protected void initFragmentView() {

    }


    /**
     * 判断网络
     */
    private void initState() {
        if (!CommonUtils.isNetConnected(getActivity())) {
            sl_notification.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
            sl_notification.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                @Override
                public void refreshClick() {
                    if (!CommonUtils.isNetConnected(getActivity())) {
                        ToastUtils.showToast(getActivity(), "请确保网络连接正常~");
                    } else {
                        dataBeanList.clear();
                        requestNoticeList();
                        sl_notification.showContentView();
                    }
                }

                @Override
                public void loginClick() {

                }
            });
        } else {
            dataBeanList.clear();
            requestNoticeList();
            sl_notification.showContentView();
        }
    }


    /**
     * 下拉刷新上拉加载
     */
    private void initRefreshAndLoad() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //请求通知公告列表
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
    public void getPCNotificationList(String string) {

        dataBeanList.clear();
        try {
            JSONObject jsonObject = new JSONObject(string);
            String msg = jsonObject.getString("msg");
            if ("success".equals(msg)) {
                NoticeData noticeData = new Gson().fromJson(string, new TypeToken<NoticeData>() {
                }.getType());
                List<NoticeData.DataBean> data = noticeData.getData();
                if (data.size() == 0) {
                    sl_notification.showEmptyView("", R.drawable.ic_no_data);
                } else {
                    sl_notification.showContentView();
                    dataBeanList.addAll(data);
                    noticeListAdapter.notifyDataSetChanged();
                }
            } else {
                ToastUtils.showShortToast(getActivity(),msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMessageList(String string) {
    }

    @Override
    public void deleteNotificationItem(String string) {

    }

    @Override
    public void deleteMessageItem(String string) {

    }

    @Override
    public void updateMessageState(String string) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //请求消息列表
            requestNoticeList();
        }
    }

    //请求消息列表
    private void requestNoticeList() {
        AdminData admin = AdminDao.getAdmin();
        if (null != admin) {
            notificationListPresenter.getPCNoticeList(FlagConstant.FLAG_NOTIFICATION_LIST,"");
        }
    }

}
