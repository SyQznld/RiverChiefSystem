package com.appler.riverchiefsystem.ui.fragment;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.api.FlagConstant;
import com.appler.riverchiefsystem.base.BaseFragment;
import com.appler.riverchiefsystem.bean.EventBusData;
import com.appler.riverchiefsystem.bean.MessageData;
import com.appler.riverchiefsystem.dao.AdminDao;
import com.appler.riverchiefsystem.dao.daoBean.AdminData;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.presenter.NotificationListPresenter;
import com.appler.riverchiefsystem.ui.activity.ProblemDetailActivity;
import com.appler.riverchiefsystem.ui.activity.task.TaskDetailActivity;
import com.appler.riverchiefsystem.ui.adapter.MessageListAdapter;
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

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * 消息中心
 */
public class MessageFragment extends BaseFragment implements NotificationListView {
    private String TAG = getClass().getSimpleName();
    Unbinder unbinder;
    @BindView(R.id.tv_fragTitle)
    TextView tv_fragTitle;
    @BindView(R.id.sv_fragMsg)
    SearchView svFragMsg;
    @BindView(R.id.rv_fragMsg)
    RecyclerView rvFragMsg;
    @BindView(R.id.sl_fragMsg)
    StateLayout slFragMsg;
    @BindView(R.id.srl_fragMsg)
    SmartRefreshLayout srlFragMsg;


    private NotificationListPresenter notificationListPresenter;
    private MessageListAdapter messageListAdapter;
    private List<MessageData.DataBean> dataBeanList = new ArrayList<>();

    private String message_name = "";
    private int messageState;//消息已读未读状态
    @Override
    protected void initFragmentView() {

    }
    @Override
    protected int setFragmentView() {
        return R.layout.fragment_message;
    }




    @Override
    protected void doFragmentBusiness() {
        notificationListPresenter = new NotificationListPresenter(this);
//        //请求消息列表
//        initState();

        rvFragMsg.setLayoutManager(new LinearLayoutManager(getActivity()));
        messageListAdapter = new MessageListAdapter(getActivity(), dataBeanList);
        rvFragMsg.setAdapter(messageListAdapter);
        messageListAdapter.setNotificationItemClickListener(new MessageListAdapter.OnNotificationItemClickListener() {
            //点击查看消息详情
            @Override
            public void checkMessageDetail(int position, MessageData.DataBean dataBean) {
                if ("否".equals(dataBean.getMessage_state())) {
                    notificationListPresenter.updateMessageState(FlagConstant.FLAG_UPDATE_MESSAGE_STATE, dataBean.getId());
                }
                //点击查看通知详情
                String typeId = dataBean.getPid();
                String message_type = dataBean.getMessage_type();
                Intent intent = null;
                if (Global.MESSAGE_TYPE_TASK.equals(message_type)) {
                    intent = new Intent(getActivity(), TaskDetailActivity.class);
                    intent.putExtra("msgId", typeId);
                    startActivity(intent);
                } else if (Global.MESSAGE_TYPE_SHANGBAO.equals(message_type)) {
                    intent = new Intent(getActivity(), ProblemDetailActivity.class);
                    intent.putExtra("msgId", typeId);
                    startActivity(intent);
                }

            }

            //删除单条消息
            @Override
            public void deleteMessageItem(int position, MessageData.DataBean dataBean) {
                notificationListPresenter.deleteSingleMessage(FlagConstant.FLAG_DELETE_SINGLE_MESSAGE, dataBean.getId());
            }

            @Override
            public void checkNotificationDetail(int position, MessageData.DataBean dataBean) {
            }

            @Override
            public void deleteNotificationItem(int position, MessageData.DataBean dataBean) {
            }

        });


        //下拉刷新 上拉加载
        initRefreshAndLoad();

        //模糊搜索
        initSearchView();

    }


    /**
     * 判断网络
     */
    private void initState() {
        if (!CommonUtils.isNetConnected(getActivity())) {
            slFragMsg.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
            slFragMsg.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                @Override
                public void refreshClick() {
                    if (!CommonUtils.isNetConnected(getActivity())) {
                        ToastUtils.showToast(getActivity(), "请确保网络连接正常~");
                    } else {
                        dataBeanList.clear();
                        requestMessageList();
                    }
                }

                @Override
                public void loginClick() {

                }
            });
        } else {
            dataBeanList.clear();
            requestMessageList();
        }
    }


    //模糊搜索
    private void initSearchView() {
        svFragMsg.setQueryHint("请输入关键词搜索");
        //修改searchView的文字颜色
        SearchView.SearchAutoComplete mSearchAutoComplete = svFragMsg.findViewById(R.id.search_src_text);
        //设置输入框内提示文字样式
        mSearchAutoComplete.setHintTextColor(getResources().getColor(android.R.color.white));//设置提示文字颜色
        mSearchAutoComplete.setTextColor(getResources().getColor(android.R.color.white));//设置内容文字颜色
        mSearchAutoComplete.setTextSize(15);//设置内容文字大小
        svFragMsg.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_fragTitle.setVisibility(View.GONE);
            }
        });
        svFragMsg.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<MessageData.DataBean> searchList = new ArrayList<>();
                for (int i = 0; i < dataBeanList.size(); i++) {
                    String message_name = dataBeanList.get(i).getMessage_name();
                    if (message_name.contains(newText)) {
                        searchList.add(dataBeanList.get(i));
                    }
                }
                dataBeanList.clear();
                dataBeanList.addAll(searchList);
                messageListAdapter.notifyDataSetChanged();
                return false;
            }
        });
        svFragMsg.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                //请求消息列表
                initState();
                tv_fragTitle.setVisibility(View.VISIBLE);
                tv_fragTitle.setText("消息列表");
                return false;
            }
        });
    }

    /**
     * 下拉刷新上拉加载
     */
    private void initRefreshAndLoad() {
        srlFragMsg.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout srlFragMsg) {
                //请求消息列表
                initState();
                srlFragMsg.finishRefresh();
            }
        });
        srlFragMsg.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout srlFragMsg) {
                srlFragMsg.finishLoadMore();
            }
        });
    }


    //返回消息列表数据
    @Override
    public void getMessageList(String string) {
        dataBeanList.clear();
        try {
            JSONObject jsonObject = new JSONObject(string);
            String msg = jsonObject.getString("msg");
            if ("success".equals(msg)) {
                MessageData notificationData = new Gson().fromJson(string, new TypeToken<MessageData>() {
                }.getType());
                List<MessageData.DataBean> data = notificationData.getData();
                if (data.size() == 0) {
                    svFragMsg.setVisibility(View.GONE);
                    slFragMsg.showEmptyView("", R.drawable.ic_no_data);
                } else {
                    svFragMsg.setVisibility(View.VISIBLE);
                    slFragMsg.showContentView();
                    dataBeanList.addAll(data);
                    messageListAdapter.notifyDataSetChanged();
                }
                EventBusData event = new EventBusData("updateMessageState");
                EventBus.getDefault().post(event);
            } else {
                ToastUtils.showShortToast(getActivity(), msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //删除单条消息
    @Override
    public void deleteMessageItem(String string) {
        Log.i(TAG, "deleteMessageItem: " + string);
        if ("Success".equals(string)) {
            requestMessageList();  //删除成功，刷新数据

            EventBusData event = new EventBusData("updateMessageState");
            EventBus.getDefault().post(event);
        } else {
            ToastUtils.showShortToast(getActivity(), string);
        }
    }

    @Override
    public void updateMessageState(String string) {
        Log.i(TAG, "updateMessageState: " + string);
        if ("Success".equals(string)) {
            requestMessageList();  //更新成功，刷新数据
        } else {
            ToastUtils.showShortToast(getActivity(), string);
        }
    }


    @Override
    public void getPCNotificationList(String string) {

    }

    @Override
    public void deleteNotificationItem(String string) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //请求消息列表
            initState();
        }
    }


    //请求消息列表
    private void requestMessageList() {
        AdminData admin = AdminDao.getAdmin();
        if (null != admin) {
            notificationListPresenter.getMessageList(FlagConstant.FLAG_MESSAGE_LIST, AdminDao.getAdminID());
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}
