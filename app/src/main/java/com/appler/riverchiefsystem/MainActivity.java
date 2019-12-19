package com.appler.riverchiefsystem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appler.riverchiefsystem.api.FlagConstant;
import com.appler.riverchiefsystem.base.BaseActivity;
import com.appler.riverchiefsystem.base.BaseApplication;
import com.appler.riverchiefsystem.bean.EventBusData;
import com.appler.riverchiefsystem.bean.MessageData;
import com.appler.riverchiefsystem.dao.AdminDao;
import com.appler.riverchiefsystem.dao.daoBean.AdminData;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.presenter.MainPresenter;
import com.appler.riverchiefsystem.ui.activity.LoginActivity;
import com.appler.riverchiefsystem.ui.adapter.PagerAdapter;
import com.appler.riverchiefsystem.ui.fragment.HomeFragment;
import com.appler.riverchiefsystem.ui.fragment.MessageFragment;
import com.appler.riverchiefsystem.ui.fragment.NotificationFragment;
import com.appler.riverchiefsystem.ui.fragment.PersonalFragment;
import com.appler.riverchiefsystem.ui.fragment.RiverPatrolFragment;
import com.appler.riverchiefsystem.utils.CommonUtils;
import com.appler.riverchiefsystem.utils.ToastUtils;
import com.appler.riverchiefsystem.utils.customView.Dialog;
import com.appler.riverchiefsystem.view.MainView;
import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainView {


    public static Activity activity;
    private static Button yes;
    private static Button no;
    @BindView(R.id.rl_title)
    RelativeLayout rl_title;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.bottomNavigationBar)
    BottomNavigationBar bottomNavigationBar;
    private String TAG = getClass().getSimpleName();
    private BaseApplication baseApplication;
    private PagerAdapter pagerAdapter;
    private List<Fragment> fragmentList;
    //    BadgeItem标签的消息数量
    private BadgeItem personelBadgeItem;
    private int unreadMsgCount = 0;
    private MainPresenter mainPresenter;
    private BottomNavigationItem unreadMsgBni;
    private int flag;
    private String IMEI;
    private android.app.Dialog dialog;
    private View v;
    /**
     * 返回键退出程序
     */
    private long exitTime;

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void doBuissness(Context context) {

        activity = this;
        baseApplication = (BaseApplication) getApplication();
        mainPresenter = new MainPresenter(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }


        flag = getIntent().getIntExtra("flag", 0);
        Log.i(TAG, "doBuissness flag: " + flag);
        if (flag == 1) {
            //利用EventBus更新数据 跳转定位到响应fragment
            eventBusIntent();
        } else {
            initState();
        }
        initViewPager();
        initNavigationBar(unreadMsgCount);


        LayoutInflater inflater = LayoutInflater.from(context);
        v = inflater.inflate(R.layout.dialog_only_login, null);// 得到加载view
        yes = v.findViewById(R.id.btn_login_yes);


    }

    /**
     * 判断网络
     */
    private void initState() {
        if (!CommonUtils.isNetConnected(MainActivity.this)) {
            //没网络
//            initNavigationBar(0);
//            eventBusIntent();
        } else {
            //有网络
            AdminData admin = AdminDao.getAdmin();
            if (null != admin) {
                mainPresenter.getUnreadMessageList(FlagConstant.FLAG_MESSAGE_LIST, AdminDao.getAdminID());
            }
        }
    }

    private void eventBusIntent() {
        if (flag == 1) {  //巡查列表点击显示轨迹
            Global.buttonPosition = 0;

            final String type = getIntent().getStringExtra("type");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //上报问题列表 添加marker
                    if ("上报定位".equals(type)) {
                        final String problemLnglat = getIntent().getStringExtra("problemLnglat");
                        final String problem_reason = getIntent().getStringExtra("problem_reason");
                        final String problem_remark = getIntent().getStringExtra("problem_remark");
                        final String problem_id = getIntent().getStringExtra("problem_id");
                        EventBusData eventBusData = new EventBusData("problemLocation");
                        eventBusData.setGpsTrack(problemLnglat);
                        eventBusData.setHeduanName(problem_remark);
                        eventBusData.setRiverName(problem_reason);
                        eventBusData.setType(type);
                        eventBusData.setProblemId(problem_id);
                        EventBus.getDefault().post(eventBusData);

                    } else {
                        //巡河记录或者巡查列表 绘制线
                        final String patrol_record_gps = getIntent().getStringExtra("patrolGps");
                        final String heduan = getIntent().getStringExtra("heduan");
                        final String riverName = getIntent().getStringExtra("riverName");

                        EventBusData event = null;
                        if ("河湖信息".equals(type)) {
                            event = new EventBusData("riverGpsTrack");
                        } else if ("巡查".equals(type)) {
                            event = new EventBusData("patrolGpsTrack");
                        }
                        event.setGpsTrack(patrol_record_gps);
                        event.setHeduanName(heduan);
                        event.setRiverName(riverName);
                        event.setType(type);
                        EventBus.getDefault().post(event);
                    }

                }
            }, 1000);
        } else if (flag == 2) {  //个人信息修改 返回到个人中心同步更新
            rl_title.setVisibility(View.VISIBLE);
            Global.buttonPosition = 4;
            tv_title.setText(R.string.personal_name);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    EventBusData event = new EventBusData("personelInfo");
                    EventBus.getDefault().post(event);
                }
            }, 300);
        } else if (flag == 3) { //更新未读消息数量
            Global.buttonPosition = 3;
        } else if (flag == 4) { //更新待办任务任务列表显示
            Global.buttonPosition = 2;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    EventBusData event = new EventBusData("updateTODOTaskList");
                    EventBus.getDefault().post(event);
                }
            }, 500);
        } else {
            Global.buttonPosition = 2;
            rl_title.setVisibility(View.VISIBLE);
            tv_title.setText(R.string.app_name);
        }

    }

    private void initViewPager() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new RiverPatrolFragment());
        fragmentList.add(new NotificationFragment());
        fragmentList.add(new HomeFragment());
        fragmentList.add(new MessageFragment());
        fragmentList.add(new PersonalFragment());
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragmentList, this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(fragmentList.size());
//      viewPager.setCurrentItem(Global.buttonPosition);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }


            @Override
            public void onPageSelected(int position) {
                Global.buttonPosition = position;
                switch (position) {
                    case 0:
                        bottomNavigationBar.selectTab(0);
                        break;
                    case 1:
                        bottomNavigationBar.selectTab(1);
                        break;
                    case 2:
                        bottomNavigationBar.selectTab(2);
                        break;
                    case 3:
                        bottomNavigationBar.selectTab(3);
                        break;
                    case 4:
                        bottomNavigationBar.selectTab(4);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @SuppressLint("ResourceType")
    private void initNavigationBar(int unreadMsgCount) {

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
//         TODO 设置背景色样式
//        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        bottomNavigationBar.setActiveColor("#5e79f7") //选中按钮的颜色
                .setInActiveColor("#FFA7A7A7")//未选中按钮的颜色
                .setBarBackgroundColor("#FFFFFF");//导航栏背景色

        if (unreadMsgCount > 0) {
            personelBadgeItem = new BadgeItem()
                    .setBorderWidth(2)
                    .setBackgroundColor(Color.parseColor("#E84E40"))
                    .setText(String.valueOf(unreadMsgCount))
                    .setHideOnSelect(false); //  控制便签被点击时 消失 | 不消失

            unreadMsgBni = new BottomNavigationItem(R.drawable.ic_message, "消息")
                    .setActiveColorResource(R.color.colorPrimary)
                    .setBadgeItem(personelBadgeItem);
        } else {
            unreadMsgBni = new BottomNavigationItem(R.drawable.ic_message, "消息");
        }
        int beforePosition = 2;
        if (-1 != Global.buttonPosition) {
            beforePosition = Global.buttonPosition;
        }

        if (0 < unreadMsgCount) {
            bottomNavigationBar.clearAll();
            bottomNavigationBar
                    .addItem(new BottomNavigationItem(R.drawable.ic_main_xuncha, "巡查"))
                    .addItem(new BottomNavigationItem(R.drawable.ic_main_notification, "通知"))
                    .addItem(new BottomNavigationItem(R.drawable.ic_main_home, "首页"))
                    .addItem(unreadMsgBni)
                    .addItem(new BottomNavigationItem(R.drawable.ic_main_personel, "个人"))
                    .setFirstSelectedPosition(beforePosition)
                    .initialise();
        } else {
            bottomNavigationBar.clearAll();
            bottomNavigationBar
                    .addItem(new BottomNavigationItem(R.drawable.ic_main_xuncha, "巡查"))
                    .addItem(new BottomNavigationItem(R.drawable.ic_main_notification, "通知"))
                    .addItem(new BottomNavigationItem(R.drawable.ic_main_home, "首页"))
                    .addItem(new BottomNavigationItem(R.drawable.ic_message, "消息"))
                    .addItem(new BottomNavigationItem(R.drawable.ic_main_personel, "个人"))
                    .setFirstSelectedPosition(beforePosition)
                    .initialise();
        }

        bottomNavigationBar.selectTab(beforePosition);
        viewPager.setCurrentItem(beforePosition);

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                Global.buttonPosition = position;
                switch (position) {
                    case 0:
                        viewPager.setCurrentItem(0);
                        rl_title.setVisibility(View.GONE);
                        break;
                    case 1:
                        rl_title.setVisibility(View.VISIBLE);
                        viewPager.setCurrentItem(1);
                        tv_title.setText(R.string.notification_name);
                        break;
                    case 2:
                        rl_title.setVisibility(View.VISIBLE);
                        viewPager.setCurrentItem(2);
                        tv_title.setText(R.string.app_name);
                        break;
                    case 3:
                        viewPager.setCurrentItem(3);
                        rl_title.setVisibility(View.GONE);
                        break;
                    case 4:
                        rl_title.setVisibility(View.VISIBLE);
                        viewPager.setCurrentItem(4);
                        tv_title.setText(R.string.personal_name);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            baseApplication.exitApp();
        }
    }


    @Override
    public void getUnreadMessageList(String string) {
        Log.i(TAG, "getUnreadMessageList: " + string);
        try {
            JSONObject jsonObject = new JSONObject(string);
            String msg = jsonObject.getString("msg");
            if ("success".equals(msg)) {
                List<MessageData.DataBean> unReadList = new ArrayList<>();
                MessageData notificationData = new Gson().fromJson(string, new TypeToken<MessageData>() {
                }.getType());
                List<MessageData.DataBean> data = notificationData.getData();
                for (int i = 0; i < data.size(); i++) {
                    String message_state = data.get(i).getMessage_state();
                    if ("否".equals(message_state)) {
                        //未读
                        unReadList.add(data.get(i));
                    }
                }
                unreadMsgCount = unReadList.size();
                initNavigationBar(unreadMsgCount);
//                利用EventBus更新数据 跳转定位到响应fragment
                eventBusIntent();
            } else {
                ToastUtils.showShortToast(MainActivity.this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onlyLoginMessage(String string) {
        Log.i(TAG, "onlyLoginMessage: " + string);

        if ("Error".equals(string)) {
            dialog = Dialog.createLoadingDialog(this, "此账号已在别处登录,请退出重新登录", v);
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AdminData admin = AdminDao.getAdmin();
                            if (null != admin) {
                                Log.i(TAG, "run: " + admin.getName());
//                                personelInfoPresenter.exitApp(FlagConstant.FLAG_EXIT_APP, admin.getStaffName());
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                Global.buttonPosition = -1;
                                AdminDao.deleteAdminData(admin.getId());
                                AdminDao.deleAllData();
                                startActivity(intent);
                                MainActivity.activity.finish();
                            }

                        }
                    }, 800);
                }
            });

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
    }


    /**
     * EventBus监听
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(EventBusData eventBusData) {
        switch (eventBusData.getMessage()) {
            case "updateMessageState":      //更新消息未读
                flag = 3;
                initState();
                break;

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}