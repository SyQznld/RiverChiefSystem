package com.appler.riverchiefsystem.ui.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.appler.riverchiefsystem.MainActivity;
import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.api.FlagConstant;
import com.appler.riverchiefsystem.base.BaseFragment;
import com.appler.riverchiefsystem.bean.EventBusData;
import com.appler.riverchiefsystem.bean.VersionData;
import com.appler.riverchiefsystem.dao.AdminDao;
import com.appler.riverchiefsystem.dao.daoBean.AdminData;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.loginHis.AccountDao;
import com.appler.riverchiefsystem.loginHis.LoginHistoryData;
import com.appler.riverchiefsystem.presenter.PersonelInfoPresenter;
import com.appler.riverchiefsystem.ui.activity.AdminDetailActivity;
import com.appler.riverchiefsystem.ui.activity.AssessActivity;
import com.appler.riverchiefsystem.ui.activity.LoginActivity;
import com.appler.riverchiefsystem.utils.ClearCacheUtil;
import com.appler.riverchiefsystem.utils.CommonUtils;
import com.appler.riverchiefsystem.utils.ToastUtils;
import com.appler.riverchiefsystem.utils.version.VersionUpdateUtils;
import com.appler.riverchiefsystem.view.PersonelInfoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人中心
 */
public class PersonalFragment extends BaseFragment implements PersonelInfoView {

    @BindView(R.id.rl_admin)
    RelativeLayout rl_admin;
    @BindView(R.id.civ_admin)
    CircleImageView civ_admin;
    @BindView(R.id.tv_admin_name)
    TextView tv_admin_name;
    @BindView(R.id.tv_admin_role)
    TextView tv_admin_role;
    @BindView(R.id.tv_admin_section)
    TextView tv_admin_section;
    @BindView(R.id.rl_assess)
    RelativeLayout rl_assess;
    @BindView(R.id.tv_admin_version)
    TextView tv_admin_version;
    @BindView(R.id.rl_versionUpdate)
    RelativeLayout rl_versionUpdate;
    @BindView(R.id.rl_resetPwd)
    RelativeLayout rl_resetPwd;
    @BindView(R.id.tv_admin_cache)
    TextView tv_admin_cache;
    @BindView(R.id.rl_clearCache)
    RelativeLayout rl_clearCache;
    @BindView(R.id.rl_exit)
    RelativeLayout rl_exit;
    private String TAG = getClass().getSimpleName();
    private String newStr;
    private String newSureStr;

    private PersonelInfoPresenter personelInfoPresenter;


    public PersonalFragment() {
        // Required empty public constructor
    }


    @Override
    protected int setFragmentView() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void doFragmentBusiness() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        personelInfoPresenter = new PersonelInfoPresenter(this);
//        初始化基本信息
        initBasicInfo();
    }

    private void initBasicInfo() {
        //获取用户信息、当前系统版本号以及当前缓存
        try {
            AdminData admin = AdminDao.getAdmin();
            if (null != admin) {
                Glide.with(getActivity()).load("").apply(new RequestOptions().error(R.mipmap.hezhang)).into(civ_admin);
                tv_admin_name.setText(admin.getName());

                tv_admin_role.setText(admin.getRolename());
                tv_admin_section.setText(admin.getDepartment());
            }

            tv_admin_version.setText(VersionUpdateUtils.getVersionName());
            tv_admin_cache.setText(ClearCacheUtil.getTotalCacheSize(getActivity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initFragmentView() {

    }


    @OnClick({R.id.rl_admin, R.id.rl_assess,R.id.rl_versionUpdate, R.id.rl_resetPwd, R.id.rl_clearCache, R.id.rl_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_admin:    //人员详情
                CommonUtils.startActivity(getActivity(), AdminDetailActivity.class);
                break;
            case R.id.rl_assess:   //绩效考核
                CommonUtils.startActivity(getActivity(), AssessActivity.class);
                break;
            case R.id.rl_versionUpdate:   //版本更新
                personelInfoPresenter.versionUpdate(FlagConstant.FLAG_VERSION_UPDATE);
                break;
            case R.id.rl_resetPwd:    //重置密码
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                View dialog = inflater.inflate(R.layout.reset_password_layout, null);
                final EditText et_pwd_old = dialog.findViewById(R.id.et_pwd_old);
                final EditText et_pwd_new = dialog.findViewById(R.id.et_pwd_new);
                final EditText et_pwd_new_sure = dialog.findViewById(R.id.et_pwd_new_sure);

                builder.setTitle("修改密码");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AdminData admin = AdminDao.getAdmin();
                        String oldStr = et_pwd_old.getText().toString();

                        newStr = et_pwd_new.getText().toString();
                        newSureStr = et_pwd_new_sure.getText().toString();
                        if ("".equals(oldStr)) {
                            ToastUtils.showShortToast(getActivity(), "原密码不能为空~");
                        } else if ("".equals(newStr)) {
                            ToastUtils.showShortToast(getActivity(), "新密码不能为空~");
                        } else if ("".equals(newSureStr)) {
                            ToastUtils.showShortToast(getActivity(), "请输入新密码确认~");
                        } else if (!newStr.equals(newSureStr)) {
                            ToastUtils.showShortToast(getActivity(), "请保证两次密码输入一致~");
                        } else {
                            if (null != admin) {
                                personelInfoPresenter.editPassword(FlagConstant.FLAG_EDIT_PASSWORD, admin.getUserId(), oldStr, newStr);
                            }
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setView(dialog);
                builder.show();

                break;
            case R.id.rl_clearCache://清除缓存
                clearCache();
                break;
            case R.id.rl_exit: //退出登录
                exitLogin();
                break;

        }
    }


    /**
     * EventBus监听
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(EventBusData eventBusData) {
        switch (eventBusData.getMessage()) {
            case "personelInfo":    //编辑后更新个人信息
                initBasicInfo();
                break;
            case "updateUnreadMsg":   //进入消息页面后更新未读消息数量
                initBasicInfo();
                break;
        }
    }


    /**
     * 退出登录
     */
    private void exitLogin() {
        new MaterialDialog.Builder(getActivity())
                .title("退出登录")
                .content("确定退出当前用户！")
                .positiveText("确定")
                .negativeText("取消")
                .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    AdminData admin = AdminDao.getAdmin();
                                    if (null != admin) {
                                        AdminDao.deleteAdminData(admin.getId());
                                        AdminDao.deleAllData();
                                        Global.buttonPosition = -1;
                                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                                        startActivity(intent);
                                        MainActivity.activity.finish();
                                    }

                                }
                            }, 500);
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    AdminData admin = AdminDao.getAdmin();
//                                    if (null != admin) {
//                                        List<LoginHistoryData> loginHistoryDataList = new AccountDao(getActivity()).queryAll();
//                                        if (null != loginHistoryDataList) {
//                                            personelInfoPresenter.exitApp(loginHistoryDataList.get(0).getUserName(), loginHistoryDataList.get(0).getPass());
//                                        }
//                                    }
//
//                                }
//                            }, 500);
                        } else if (which == DialogAction.NEGATIVE) {
                            dialog.dismiss();
                        }
                    }
                }).show();
    }


    /**
     * 清除缓存
     */
    private void clearCache() {
        try {
            new MaterialDialog.Builder(getActivity())
                    .title("清除缓存")
                    .content("确定清除" + ClearCacheUtil.getTotalCacheSize(getActivity()) + "缓存！")
                    .positiveText("确定")
                    .negativeText("取消")
                    .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色

                    .onAny(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            if (which == DialogAction.POSITIVE) {
                                ClearCacheUtil.clearAllCache(getActivity());
                                tv_admin_cache.setText("0KB");

                            } else if (which == DialogAction.NEGATIVE) {
                                dialog.dismiss();
                            }

                        }
                    }).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 编辑密码  请求接口测试
     */
    @Override
    public void editPassword(String string) {
        Log.i(TAG, "editPassword: " + string);
        if ("Success".equals(string)) {
            ToastUtils.showShortToast(getActivity(), "密码修改成功");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    LoginHistoryData historyInfo = new LoginHistoryData(AdminDao.getAdmin().getName(), newStr);
                    new AccountDao(getActivity()).insert(historyInfo);


                    AdminData admin = AdminDao.getAdmin();
                    if (null != admin) {
                        AdminDao.deleteAdminData(admin.getId());
                        AdminDao.deleAllData();
//                        personelInfoPresenter.exitApp(FlagConstant.FLAG_EXIT_APP, admin.getStaffName());
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        MainActivity.activity.finish();
                    }
                }
            }, 800);
        } else if ("Error".equals(string)) {
            ToastUtils.showShortToast(getActivity(), "密码修改失败");
        } else if ("旧密码错误".equals(string)) {
            ToastUtils.showShortToast(getActivity(), "原密码输入错误");
        }
    }

    /**
     * 版本更新 请求接口测试
     */
    @Override
    public void versionUpdate(String string) {
        Log.i(TAG, "versionUpdate: " + string);

        if (!"".equals(string)) {
            final VersionData versionData = new Gson().fromJson(string, new TypeToken<VersionData>() {
            }.getType());
            Double urlVersion = Double.parseDouble(versionData.getVersion());
            Double localVersion = Double.parseDouble(VersionUpdateUtils.getVersionName());
            if (urlVersion > localVersion) {
                try {
                    new MaterialDialog.Builder(getActivity())
                            .title("版本更新")
                            .content(versionData.getUpdateinfo())
                            .positiveText("确定")
                            .negativeText("取消")
                            .widgetColor(Color.BLUE)//不再提醒的checkbox 颜色

                            .onAny(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    if (which == DialogAction.POSITIVE) {
                                        //服务器版本大于当前安装版本，版本更新，传入url
                                        VersionUpdateUtils.downFile(getActivity(), versionData.getLoadurl());
                                    } else if (which == DialogAction.NEGATIVE) {
                                        dialog.dismiss();
                                    }

                                }
                            }).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                ToastUtils.showShortToast(getActivity(), "当前版本已是最新版本");
            }
        }

    }

    @Override
    public void editPersonelInfo(String string) {
        Log.i(TAG, "editPersonelInfo: " + string);
    }

    @Override
    public void exitApp(String string) {
        Log.i(TAG, "exitApp: " + string);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AdminData admin = AdminDao.getAdmin();
                if (null != admin) {
                    AdminDao.deleteAdminData(admin.getId());
                    AdminDao.deleAllData();
                    Global.buttonPosition = -1;
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    MainActivity.activity.finish();
                }

            }
        }, 500);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {   //onResume
            initBasicInfo();
        } else {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
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
