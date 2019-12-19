package com.appler.riverchiefsystem.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appler.riverchiefsystem.MainActivity;
import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.api.FlagConstant;
import com.appler.riverchiefsystem.base.BaseActivity;
import com.appler.riverchiefsystem.base.BaseApplication;
import com.appler.riverchiefsystem.dao.daoBean.AdminData;
import com.appler.riverchiefsystem.bean.UserBean;
import com.appler.riverchiefsystem.dao.AdminDao;
import com.appler.riverchiefsystem.loginHis.AccountDao;
import com.appler.riverchiefsystem.loginHis.LoginHistoryData;
import com.appler.riverchiefsystem.presenter.LoginPresenter;
import com.appler.riverchiefsystem.utils.ToastUtils;
import com.appler.riverchiefsystem.utils.version.VersionUpdateUtils;
import com.appler.riverchiefsystem.view.LoginView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView {
    private String TAG = getClass().getSimpleName();
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.tv_version)
    TextView tv_version;
    @BindView(R.id.pb_login)
    ProgressBar pb_login;
    private LoginPresenter loginPresenter;
    private BaseApplication baseApplication;
    private String username;
    private String password;

    @Override
    protected int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void doBuissness(Context context) {
        baseApplication = (BaseApplication) getApplication();
        loginPresenter = new LoginPresenter(this);
        /**
         * 历史登录用户
         * */
        List<LoginHistoryData> loginHistoryDataList = new AccountDao(this).queryAll();
        if (loginHistoryDataList != null && loginHistoryDataList.size() > 0) {
            et_username.setText(loginHistoryDataList.get(loginHistoryDataList.size() - 1).getUserName());
            et_password.setText(loginHistoryDataList.get(loginHistoryDataList.size() - 1).getPass());
        }
        //初始化基本设置
        initBasicSetting(context);

        //获取当前系统版本号
        try {
            tv_version.setText("V " + VersionUpdateUtils.getVersionName());
        } catch (Exception e) {
            e.printStackTrace();
        }

//         杨冲 村河长         马楼 乡河长
    }

    @OnClick({R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login: //登录
                login();
                break;
        }
    }

    private void login() {
        username = et_username.getText().toString();
        password = et_password.getText().toString();
        String msg = TextUtils.isEmpty(username) ? "账号不能为空！" : TextUtils.isEmpty(password) ? "密码不能为空！" : "";
        if (!TextUtils.isEmpty(msg)) {
            pb_login.setVisibility(View.GONE);
            showToast(msg);
        } else {
            btn_login.setEnabled(false);
            pb_login.setVisibility(View.VISIBLE);
            loginPresenter.getLogin(FlagConstant.FLAG_LOGIN, username, password);
        }
    }

    /**
     * 初始化基本设置
     */
    private void initBasicSetting(Context context) {
        Window window = getWindow();
        String version = Build.VERSION.RELEASE.substring(0, 1);
        if (Integer.parseInt(version) >= 6) {
            //动态申请用户权限
            setpermission();
        }

        //默认API 最低19
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup contentView = window.getDecorView().findViewById(Window.ID_ANDROID_CONTENT);
            contentView.getChildAt(0).setFitsSystemWindows(false);
        }

        //如果已经登录成功，直接进入首页
        if (null != AdminDao.getAdmin()) {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * 动态设置权限
     */
    public void setpermission() {
        XXPermissions.with(LoginActivity.this)
                .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                .permission(Permission.REQUEST_INSTALL_PACKAGES) //支持请求安装权限和悬浮窗权限
//                .permission( Permission.SYSTEM_ALERT_WINDOW) //支持悬浮窗权限
                .permission(Permission.Group.STORAGE) //支持多个权限组进行请求，不指定则默以清单文件中的危险权限进行请求
                .permission(Permission.Group.CALENDAR)
                .permission(Permission.Group.CAMERA)
                .permission(Permission.Group.LOCATION)
                .permission(Permission.Group.PHONE)
                .permission(Permission.Group.MICROPHONE)
                .permission(Permission.Group.SMS)
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                        } else {
                            Toast.makeText(LoginActivity.this, "获取权限成功，部分权限未正常授予", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            Toast.makeText(LoginActivity.this, "被永久拒绝授权，请手动授予权限", Toast.LENGTH_SHORT).show();
                            //如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.gotoPermissionSettings(LoginActivity.this);
                        } else {
                            Toast.makeText(LoginActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    /**
     * 请求接口返回数据
     */
    @Override
    public void getLogin(String string) {
        Log.i(TAG, "getLogin: " + string);
        if (string.contains("{")){
            try {
                JSONObject jsonObject = new JSONObject(string);
                String msg = jsonObject.getString("msg");
                if ("success".equals(msg)) {
                    UserBean userBean = new Gson().fromJson(string, new TypeToken<UserBean>() {
                    }.getType());
                    List<UserBean.DataBean> dataList = userBean.getData();
                    if (null != dataList && dataList.size() > 0) {
                        UserBean.DataBean data = dataList.get(0);
                        AdminData admin = AdminDao.getAdmin();
                        if (admin == null) {
                            admin = new AdminData();

                            setAdminInfo(data, admin);

                            AdminDao.insertAdminData(admin);
                        } else {

                            setAdminInfo(data, admin);

                            AdminDao.updateAdminData(admin);
                        }
                        //保存用户名密码
                        List<LoginHistoryData> loginHistoryDataList = new AccountDao(this).queryAll();
                        if (loginHistoryDataList != null && loginHistoryDataList.size() > 0) {
                            for (int i = 0; i < loginHistoryDataList.size(); i++) {
                                if (!loginHistoryDataList.get(i).getUserName().equals(et_username.getText().toString())) {
                                    new AccountDao(LoginActivity.this).deleteNoCurrAccount(loginHistoryDataList.get(i).getUserName());
                                }
                            }
                        }
                        LoginHistoryData historyInfo = new LoginHistoryData(et_username.getText().toString(), et_password.getText().toString());
                        new AccountDao(this).insert(historyInfo);


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                pb_login.setVisibility(View.GONE);
                                btn_login.setEnabled(true);
                            }
                        }, 1000);
                    }

                } else {
                    btn_login.setEnabled(true);
                    pb_login.setVisibility(View.GONE);
                    ToastUtils.showShortToast(LoginActivity.this, msg);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else {
            btn_login.setEnabled(true);
            pb_login.setVisibility(View.GONE);
            ToastUtils.showShortToast(LoginActivity.this, string);
        }

    }


    /**
     * 设置人员信息
     */
    private void setAdminInfo(UserBean.DataBean userBean, AdminData user) {
        user.setUserId(userBean.getId());
        user.setUsername(userBean.getUsername());
        user.setName(userBean.getName());
        user.setPassword(userBean.getPassword());
        user.setDepartment(userBean.getDepartment());
        user.setTelephone(userBean.getTelephone());
        user.setRole_id(userBean.getRole_id());
        user.setRolename(userBean.getRolename());
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
        baseApplication.exitApp();
    }
}
