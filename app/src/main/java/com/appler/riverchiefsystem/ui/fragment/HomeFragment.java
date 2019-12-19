package com.appler.riverchiefsystem.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.api.FlagConstant;
import com.appler.riverchiefsystem.base.BaseFragment;
import com.appler.riverchiefsystem.bean.EventBusData;
import com.appler.riverchiefsystem.bean.TaskData;
import com.appler.riverchiefsystem.dao.AdminDao;
import com.appler.riverchiefsystem.dao.daoBean.AdminData;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.presenter.TodoTaskListPresenter;
import com.appler.riverchiefsystem.ui.activity.ChargeRiversActivity;
import com.appler.riverchiefsystem.ui.activity.ContactsActivity;
import com.appler.riverchiefsystem.ui.activity.PatrolListActivity;
import com.appler.riverchiefsystem.ui.activity.ProblemListActivity;
import com.appler.riverchiefsystem.ui.activity.SYQInfosActivity;
import com.appler.riverchiefsystem.ui.activity.task.MyTaskActivity;
import com.appler.riverchiefsystem.ui.adapter.TaskListAdapter;
import com.appler.riverchiefsystem.utils.CommonUtils;
import com.appler.riverchiefsystem.utils.ToastUtils;
import com.appler.riverchiefsystem.view.TodoTaskListView;
import com.bumptech.glide.Glide;
import com.fingdo.statelayout.StateLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 首页页面
 */
public class HomeFragment extends BaseFragment implements TodoTaskListView {

    Unbinder unbinder;
    @BindView(R.id.tv_gps)
    TextView tv_gps;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_myTask)
    TextView tv_myTask;
    @BindView(R.id.tv_problemList)
    TextView tv_problemList;
    @BindView(R.id.tv_patrolList)
    TextView tv_patrolList;
    @BindView(R.id.rl_home_task)
    RelativeLayout rl_home_task;
    @BindView(R.id.sl_home_task)
    StateLayout sl_home_task;
    @BindView(R.id.rv_home_task)
    RecyclerView rv_home_task;
    @BindView(R.id.srl_hometodo_task)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_yuqing)
    TextView tvYuqing;
    @BindView(R.id.tv_rivers)
    TextView tvRivers;
    @BindView(R.id.tv_contacts)
    TextView tvContacts;
    Unbinder unbinder1;
    private String TAG = getClass().getSimpleName();
    private ArrayList<Integer> list_path;
    private ArrayList<String> list_titles;


    private TodoTaskListPresenter todoTaskListPresenter;
    private List<TaskData.DataBean> dataBeanList = new ArrayList<>();
    private TaskListAdapter taskListAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected int setFragmentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void doFragmentBusiness() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        todoTaskListPresenter = new TodoTaskListPresenter(this);

        //判断核实模块是否显示，只有领导用户显示
        AdminData admin = AdminDao.getAdmin();
        if (null != admin) {
            String rolename = admin.getRolename();
            if (!rolename.equals(Global.ROLENAME_CUN_HEZHANG)
                    && !rolename.equals(Global.ROLENAME_XIANG_HEZHANG)
                    && !rolename.equals(Global.ROLENAME_ZHEN_HEZHANG)) {  //领导权限
                tv_patrolList.setVisibility(View.INVISIBLE);
            } else {
                tv_patrolList.setVisibility(View.VISIBLE);
            }
        }


        rv_home_task.setLayoutManager(new LinearLayoutManager(getActivity()));
        taskListAdapter = new TaskListAdapter(getActivity(), dataBeanList, 1);
        rv_home_task.setAdapter(taskListAdapter);

        initState();

        initRefreshAndLoad();//刷新数据

    }

    @Override
    protected void initFragmentView() {
        //初始化轮播图设置
        initBanner();

    }


    @OnClick({R.id.tv_yuqing, R.id.tv_rivers, R.id.tv_contacts,
            R.id.tv_myTask, R.id.tv_problemList, R.id.tv_patrolList,
            R.id.rl_home_task})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_yuqing:  //水雨情
                Intent intent = new Intent(getActivity(), SYQInfosActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_rivers:  //河湖信息
                Intent riverIntent = new Intent(getActivity(), ChargeRiversActivity.class);
                startActivity(riverIntent);
                break;
            case R.id.tv_contacts:  //通讯录
                Intent contactIntent = new Intent(getActivity(), ContactsActivity.class);
                startActivity(contactIntent);
                break;
            case R.id.tv_myTask:  //我的任务
                CommonUtils.startActivity(getActivity(), MyTaskActivity.class);
                break;
            case R.id.tv_problemList:  //问题记录
                CommonUtils.startActivity(getActivity(), ProblemListActivity.class);
                break;
            case R.id.tv_patrolList:  //巡查记录
                CommonUtils.startActivity(getActivity(), PatrolListActivity.class);
                break;
            case R.id.rl_home_task:  //查看全部待办任务
                CommonUtils.startActivity(getActivity(), MyTaskActivity.class);
                break;

        }
    }


    /**
     * 下拉刷新上拉加载
     */
    private void initRefreshAndLoad() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //判断网络 显示加载或数据页面
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
     * 判断网络
     */
    private void initState() {
        if (!CommonUtils.isNetConnected(getActivity())) {
            sl_home_task.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
            sl_home_task.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                @Override
                public void refreshClick() {
                    if (!CommonUtils.isNetConnected(getActivity())) {
                        ToastUtils.showToast(getActivity(), "请确保网络连接正常~");
                    } else {
                        dataBeanList.clear();
                        requestTaskList();
                        sl_home_task.showContentView();
                    }
                }

                @Override
                public void loginClick() {

                }
            });
        } else {
            dataBeanList.clear();
            requestTaskList();
            sl_home_task.showContentView();
        }
    }


    /**
     * 轮播图相关设置
     */
    private void initBanner() {
        //放图片地址的集合
        list_path = new ArrayList<>();
        list_titles = new ArrayList<>();


        list_path.add(R.mipmap.banner1);
        list_path.add(R.mipmap.banner2);
        list_titles.add("");
        list_titles.add("");


        //设置内置样式，共有六种可以点入方法内逐一体验使用。
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new MyLoader());
        //设置图片网址或地址的集合
        banner.setImages(list_path);
        banner.setBannerTitles(list_titles);
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default);

        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {

                    }
                })
                //必须最后调用的方法，启动轮播图。
                .start();
    }


    @Override
    public void getHomeTodoTaskList(String string) {
        Log.i(TAG, "getHomeTodoTaskList: " + string);
        dataBeanList.clear();
        try {
            JSONObject jsonObject = new JSONObject(string);
            String msg = jsonObject.getString("msg");
            if ("success".equals(msg)) {
                TaskData taskData = new Gson().fromJson(string, new TypeToken<TaskData>() {
                }.getType());
                List<TaskData.DataBean> data = taskData.getData();
                if (data.size() == 0) {
                    sl_home_task.showEmptyView("", R.drawable.ic_no_data);
                } else {
                    sl_home_task.showContentView();
                    dataBeanList.addAll(data);
                    taskListAdapter.notifyDataSetChanged();
                }
            } else {
                ToastUtils.showShortToast(getActivity(), msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * EventBus监听
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(EventBusData eventBusData) {
        switch (eventBusData.getMessage()) {
            case "updateTODOTaskList":    //更新待办任务任务列表显示
                initState();
                break;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

            todoTaskListPresenter = new TodoTaskListPresenter(this);
            requestTaskList();
        }

    }

    private void requestTaskList() {
        AdminData admin = AdminDao.getAdmin();
        if (null != admin) {
            todoTaskListPresenter.getHomeTodoTaskList(FlagConstant.FLAG_HOME_TODOTASK_LIST, AdminDao.getAdminID());  //96  测试id
        }
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);//以原图填满ImageView为目的，如果原图size大于ImageView的size，按比例缩小，居中显示在ImageView上;如果原图size小于ImageView的size，则按比例拉升原图的宽和高，填充ImageView居中显示。
            Glide.with(context).load(path).into(imageView);
        }

    }
}
