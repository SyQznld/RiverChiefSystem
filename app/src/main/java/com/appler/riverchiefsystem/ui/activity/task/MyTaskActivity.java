package com.appler.riverchiefsystem.ui.activity.task;

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
import com.appler.riverchiefsystem.bean.TaskData;
import com.appler.riverchiefsystem.dao.AdminDao;
import com.appler.riverchiefsystem.dao.daoBean.AdminData;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.presenter.TaskListPresenter;
import com.appler.riverchiefsystem.ui.adapter.TaskListAdapter;
import com.appler.riverchiefsystem.utils.CommonUtils;
import com.appler.riverchiefsystem.utils.ModelUtil;
import com.appler.riverchiefsystem.utils.ToastUtils;
import com.appler.riverchiefsystem.utils.filter.FilterData;
import com.appler.riverchiefsystem.utils.filter.FilterEntity;
import com.appler.riverchiefsystem.utils.filter.FilterOneAdapter;
import com.appler.riverchiefsystem.utils.filter.FilterView;
import com.appler.riverchiefsystem.view.TaskListView;
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

/**
 * 我的任务页面
 */
public class MyTaskActivity extends BaseActivity implements TaskListView {
    @BindView(R.id.iv_toolbar_back)
    ImageView iv_back;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_title;
    @BindView(R.id.iv_toolbar_imageview)
    ImageView iv_add;
    @BindView(R.id.sv_search)
    SearchView sv_search;
    @BindView(R.id.srl_myTask)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.sl_myTask_list)
    StateLayout sl_myTask;
    @BindView(R.id.rv_myTask_list)
    RecyclerView rv_myTask;
    @BindView(R.id.fv_task)
    FilterView filterView;
    private String TAG = getClass().getSimpleName();
    private TaskListPresenter myTaskListPresenter;
    private TaskListAdapter taskListAdapter;
    private List<TaskData.DataBean> dataBeanList = new ArrayList<>();
    private String task_grade = "";//任务等级
    private String task_belong = "";//任务接收/派发类别

    private FilterData filterData;
    private List<FilterEntity> filterPersonList = new ArrayList<>();

    private String staff_id = "";
    private String keyword = "";//关键词

    @Override
    protected int bindLayout() {
        return R.layout.activity_my_task_list;
    }

    @Override
    protected void doBuissness(Context context) {
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText("我的任务");
        iv_add.setVisibility(View.GONE);
        AdminData admin = AdminDao.getAdmin();
        if (null != admin) {
            String staff_hierarchy = admin.getRolename();
            if (!Global.ROLENAME_CUN_HEZHANG.equals(staff_hierarchy)
                    && !Global.ROLENAME_ZHEN_HEZHANG.equals(staff_hierarchy)
                    && !Global.ROLENAME_XIANG_HEZHANG.equals(staff_hierarchy)) {
                staff_id = String.valueOf(admin.getUserId());
            }
        }
        myTaskListPresenter = new TaskListPresenter(this);


        rv_myTask.setLayoutManager(new LinearLayoutManager(context));
        taskListAdapter = new TaskListAdapter(context, dataBeanList, 3);
        rv_myTask.setAdapter(taskListAdapter);

//        //判断网络 显示加载或数据页面
//        initState();

        //下拉刷新 上拉加载
        initRefreshAndLoad();

        //筛选条件 过滤
        initFilter();

        //模糊搜索
        initSearchView();
    }


    /**
     * 判断网络
     */
    private void initState() {
        if (!CommonUtils.isNetConnected(MyTaskActivity.this)) {
            sl_myTask.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
            sl_myTask.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                @Override
                public void refreshClick() {
                    if (!CommonUtils.isNetConnected(MyTaskActivity.this)) {
                        ToastUtils.showToast(MyTaskActivity.this, "请确保网络连接正常~");
                    } else {
                        dataBeanList.clear();
                        requestTaskList();
                        sl_myTask.showContentView();
                    }
                }

                @Override
                public void loginClick() {

                }
            });
        } else {
            dataBeanList.clear();
            requestTaskList();
            sl_myTask.showContentView();
        }
    }


    //模糊搜索
    private void initSearchView() {
        sv_search.setQueryHint("请输入任务名称搜索");
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
                List<TaskData.DataBean> searchList = new ArrayList<>();
                for (int i = 0; i < dataBeanList.size(); i++) {
                    String task_detail = dataBeanList.get(i).getQuestion();
                    if (task_detail.contains(newText)) {
                        searchList.add(dataBeanList.get(i));
                    }
                }
                dataBeanList.clear();
                dataBeanList.addAll(searchList);
                taskListAdapter.notifyDataSetChanged();
                return false;
            }
        });
        sv_search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                //请求任务列表
                initState();
                tv_title.setVisibility(View.VISIBLE);
                tv_title.setText("我的任务");
                return false;
            }
        });
    }

    @OnClick({R.id.iv_toolbar_back, R.id.iv_toolbar_imageview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:
//                if (flag == 0  || flag == 3 || flag == 4) {
//                    Intent intent = new Intent(MyTaskActivity.this, MainActivity.class);
//                    intent.putExtra("flag", 4);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    finish();
//                }
                finish();
                break;
            case R.id.iv_toolbar_imageview:
                Intent intent = new Intent(MyTaskActivity.this, EditTaskActivity.class);
                startActivity(intent);
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
     * 筛选条件
     */
    private void initFilter() {
        List<FilterEntity> filterGradeList = ModelUtil.getTaskGrade(); //任务等级
        List<FilterEntity> sortBelongList = ModelUtil.getTaskBelong(); //任务分类  接收/派发

        filterData = new FilterData();
        filterData.setFilters(filterGradeList);//任务等级
        if (!AdminDao.getAdmin().getRolename().equals(Global.ROLENAME_CUN_HEZHANG)
                && !AdminDao.getAdmin().getRolename().equals(Global.ROLENAME_SHI_HEZHANG)) {
            filterData.setSorts(sortBelongList);//任务分类  接收/派发
        }

        AdminData admin = AdminDao.getAdmin();
        if (null != admin) {
            String staff_hierarchy = admin.getRolename();
            if (!Global.ROLENAME_CUN_HEZHANG.equals(staff_hierarchy)
                    && !Global.ROLENAME_ZHEN_HEZHANG.equals(staff_hierarchy)
                    && !Global.ROLENAME_XIANG_HEZHANG.equals(staff_hierarchy)) {
                filterPersonList.add(new FilterEntity("全部", staff_id));
                filterData.setOrgFilter(filterPersonList);
                //人员设置默认显示
                FilterOneAdapter filterAdapter = new FilterOneAdapter(MyTaskActivity.this, filterPersonList);
                filterAdapter.setSelectedEntity(filterPersonList.get(0));
            }
        }

        filterView.setFilterData(MyTaskActivity.this, filterData);
        // (真正的)筛选视图点击
        filterView.setOnFilterClickListener(new FilterView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                filterView.show(position);
            }
        });

        //设置默认显示
        FilterOneAdapter filterAdapter = new FilterOneAdapter(MyTaskActivity.this, filterGradeList);
        filterAdapter.setSelectedEntity(filterGradeList.get(0));
        filterView.setOnItemFilterClickListener(new FilterView.OnItemFilterClickListener() {
            @Override
            public void onItemFilterClick(FilterEntity entity) {
                dataBeanList.clear();
                String value = entity.getValue();
                if ("0".equals(value)) { //
                    task_grade = "";
                } else if ("1".equals(value)) {
                    task_grade = "待解决";
                } else if ("2".equals(value)) {
                    task_grade = "正在解决";
                } else if ("3".equals(value)) {
                    task_grade = "不能解决";
                } else if ("4".equals(value)) {
                    task_grade = "已解决";
                }
                //接口返回任务列表数据
                initState();
            }
        });

        //设置默认显示
        FilterOneAdapter sortAdapter = new FilterOneAdapter(MyTaskActivity.this, sortBelongList);
        sortAdapter.setSelectedEntity(sortBelongList.get(0));
        filterView.setOnItemSortClickListener(new FilterView.OnItemSortClickListener() {
            @Override
            public void onItemSortClick(FilterEntity entity) {
                dataBeanList.clear();
                String value = entity.getValue();
                if ("0".equals(value)) { //
                    task_belong = "";
                } else if ("1".equals(value)) {
                    task_belong = "接收";
                } else if ("2".equals(value)) {
                    task_belong = "派发";
                }else if ("3".equals(value)) {
                    task_belong = "交办";
                }
                //接口返回任务列表数据
                initState();
            }

        });

        //按人员
        filterView.setOnItemOrgFilterClickListener(new FilterView.OnItemOrgFilterClickListener() {
            @Override
            public void onItemOrgFilterClick(FilterEntity entity) {
                dataBeanList.clear();
                String key = entity.getKey();
                for (int i = 0; i < filterPersonList.size(); i++) {
                    String filterKey = filterPersonList.get(i).getKey();
                    if (filterKey.equals(key)) {
                        staff_id = filterPersonList.get(i).getValue();
                    }
                }
                //接口返回任务列表数据
                requestTaskList();
            }
        });

    }


    //任务列表
    @Override
    public void getTaskList(String string) {
        dataBeanList.clear();
        try {
            JSONObject jsonObject = new JSONObject(string);
            String msg = jsonObject.getString("msg");
            if ("success".equals(msg)) {
                TaskData taskData = new Gson().fromJson(string, new TypeToken<TaskData>() {
                }.getType());
                List<TaskData.DataBean> data = taskData.getData();
                if (data.size() == 0) {
                    sv_search.setVisibility(View.GONE);
                    sl_myTask.showEmptyView("", R.drawable.ic_no_data);
                } else {
                    sv_search.setVisibility(View.VISIBLE);
                    sl_myTask.showContentView();
                    dataBeanList.addAll(data);
                    taskListAdapter.notifyDataSetChanged();
                }
            } else {
                ToastUtils.showShortToast(MyTaskActivity.this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //根据权限返回下属人员
    @Override
    public void getChargePerson(String string) {
        Log.i(TAG, "getChargePerson: " + string);
//        if (!"".equals(string)) {
//            filterPersonList.clear();
//            ChargePersonData chargePersonData = new Gson().fromJson(string, new TypeToken<ChargePersonData>() {
//            }.getType());
//            final List<ChargePersonData.DataBean> data = chargePersonData.getData();
//            List<FilterEntity> list = new ArrayList<>();
//            for (int i = 0; i < data.size(); i++) {
//                String name = data.get(i).getStaff_name();
//                String id = data.get(i).getSys_staff_id();
//                list.add(new FilterEntity(name, id));
//            }
//            filterPersonList.addAll(list);
//        }
    }

    @Override
    public void updateTask(String string) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        AdminData admin = AdminDao.getAdmin();
        if (null != admin) {
            //判断网络 显示加载或数据页面
            initState();
        }
    }


    //接口返回问题列表数据
    private void requestTaskList() {
        AdminData admin = AdminDao.getAdmin();
        if (null != admin) {
            myTaskListPresenter.getTaskList(FlagConstant.FLAG_TASK_LIST, AdminDao.getAdminID(), task_grade, task_belong,keyword);
        }
    }
}
