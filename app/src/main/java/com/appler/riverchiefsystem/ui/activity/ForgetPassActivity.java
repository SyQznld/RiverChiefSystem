package com.appler.riverchiefsystem.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.api.FlagConstant;
import com.appler.riverchiefsystem.base.BaseActivity;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.loginHis.AccountDao;
import com.appler.riverchiefsystem.loginHis.LoginHistoryData;
import com.appler.riverchiefsystem.presenter.ForgetPassPresenter;
import com.appler.riverchiefsystem.utils.ToastUtils;
import com.appler.riverchiefsystem.view.ForgetPassView;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPassActivity extends BaseActivity implements ForgetPassView {
    @BindView(R.id.iv_forgetPass_back)
    ImageView iv_back;
    @BindView(R.id.et_forgetPass_username)
    EditText et_username;
    @BindView(R.id.et_forgetPass_number)
    EditText et_number;
    @BindView(R.id.et_forgetPass_newpass)
    EditText et_newPass;
    @BindView(R.id.btn_forgetPass_submit)
    Button btn_submit;
    private String TAG = getClass().getSimpleName();
    private ForgetPassPresenter forgetPassPresenter;

    @Override
    protected int bindLayout() {
        return R.layout.activity_forget_pass;
    }

    @Override
    protected void doBuissness(Context context) {
        forgetPassPresenter = new ForgetPassPresenter(this);

    }

    @OnClick({R.id.iv_forgetPass_back, R.id.btn_forgetPass_submit})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_forgetPass_back:  //返回
                finish();
                break;
            case R.id.btn_forgetPass_submit:  //提交
                String username = et_username.getText().toString();
                String number = et_number.getText().toString();
                String newPass = et_newPass.getText().toString();
                String msg = TextUtils.isEmpty(username) ? "用户名不能为空！" :
                        TextUtils.isEmpty(number) ? "警号不能为空！" :
                                TextUtils.isEmpty(newPass) ? "新密码不能为空！" : "";
                if (!TextUtils.isEmpty(msg)) {
                    showToast(msg);
                } else {
                    forgetPassPresenter.forgetPass(FlagConstant.FLAG_FORGET_PASSWORD, username, "1", "1", newPass);
                }
                break;
        }
    }

    @Override
    public void forgetPass(String string) {
        Log.i(TAG, "forgetPass: " + string);
        if (Global.PC_BACK_HTTP500.equals(string)) {
            ToastUtils.showShortToast(ForgetPassActivity.this, Global.TOAST_HTTP500);
        } else if (Global.PC_BACK_TIMEOUT.equals(string) || Global.PC_BACK_TIMEOUT_.equals(string)) {
            ToastUtils.showShortToast(ForgetPassActivity.this, Global.TOAST_TIMEOUT);
        } else if ("Success".equals(string)) {
            ToastUtils.showShortToast(ForgetPassActivity.this, "密码修改成功");
            LoginHistoryData historyInfo = new LoginHistoryData(et_username.getText().toString(), et_newPass.getText().toString());
            new AccountDao(ForgetPassActivity.this).insert(historyInfo);

            Intent intent = new Intent(ForgetPassActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        } else if ("Error".equals(string)) {
            ToastUtils.showShortToast(ForgetPassActivity.this, "密码修改失败");
        }
    }
}
