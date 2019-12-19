package com.appler.riverchiefsystem.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.api.FlagConstant;
import com.appler.riverchiefsystem.base.BaseActivity;
import com.appler.riverchiefsystem.dao.AdminDao;
import com.appler.riverchiefsystem.dao.daoBean.AdminData;
import com.appler.riverchiefsystem.presenter.PersonelInfoPresenter;
import com.appler.riverchiefsystem.utils.PhoneFormatCheckUtils;
import com.appler.riverchiefsystem.utils.ToastUtils;
import com.appler.riverchiefsystem.utils.customView.UploadDialogUtils;
import com.appler.riverchiefsystem.view.PersonelInfoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditAdminInfoActivity extends BaseActivity implements PersonelInfoView {
    //调用系统相机
    private static final int TAKE_PHOTO = 1;
    //调用系统相册-选择图片
    private static final int IMAGE = 0;
    @BindView(R.id.iv_toolbar_tv_back)
    ImageView iv_back;
    @BindView(R.id.tv_toolbar_tv_title)
    TextView tv_title;
    @BindView(R.id.tv_toolbar_tv_textview)
    TextView tv_submit;
    @BindView(R.id.rl_editInfo_head)
    RelativeLayout rl_head;
    @BindView(R.id.civ_editInfo_head)
    CircleImageView civ_head;
    @BindView(R.id.tv_editInfo_name)
    TextView tv_editInfo_name;
    @BindView(R.id.et_editInfo_tel)
    EditText et_tel;
    @BindView(R.id.tv_editInfo_post)
    TextView tv_post;
    @BindView(R.id.tv_editInfo_section)
    TextView tv_section;
    private String TAG = getClass().getSimpleName();
    private Dialog uploadDialog;
    private PersonelInfoPresenter personelInfoPresenter;

    private String tel = "";


    @Override
    protected int bindLayout() {
        return R.layout.activity_edit_admin_info;
    }

    @Override
    protected void doBuissness(final Context context) {
        personelInfoPresenter = new PersonelInfoPresenter(this);
        tv_title.setText("用户编辑");
        tv_submit.setText("提交");


        //初始化人员信息
        initPersonelInfo(context);
    }


    private void initPersonelInfo(final Context context) {
        AdminData admin = AdminDao.getAdmin();
        if (null != admin) {
            Glide.with(this).load("").apply(new RequestOptions().error(R.mipmap.hezhang)).into(civ_head);
            tv_editInfo_name.setText(admin.getName());
            et_tel.setText(admin.getTelephone());
            tv_post.setText(admin.getRolename());
            tv_section.setText(admin.getDepartment());

            tv_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showShortToast(context, "暂无权限修改角色");
                }
            });
            tv_section.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showShortToast(context, "暂无权限修改所属部门");
                }
            });

        }
    }

    @OnClick({R.id.iv_toolbar_tv_back, R.id.tv_toolbar_tv_textview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_tv_back:
                finish();
                break;

            case R.id.tv_toolbar_tv_textview: //编辑用户信息，提交请求接口测试
                tel = et_tel.getText().toString();
                boolean chinaPhoneLegal = PhoneFormatCheckUtils.isChinaPhoneLegal(tel);   //校验手机号码
                if (chinaPhoneLegal) {
                    uploadDialog = UploadDialogUtils.createLoadingDialog(EditAdminInfoActivity.this, "正在提交");
                    AdminData admin = AdminDao.getAdmin();
                    if (null != admin) {
                        editPersonInfo(tel);
                    }
                } else {
                    ToastUtils.showShortToast(EditAdminInfoActivity.this, "请输入正确的手机号");
                }
                break;
        }
    }

    /**
     * 编辑个人信息 提交请求接口测试
     */
    private void editPersonInfo(String tel) {
        personelInfoPresenter.editPersonelInfo(FlagConstant.FLAG_EDIT_PERSONEL_INFO, AdminDao.getAdminID(),  tel);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (uploadDialog != null) {
            uploadDialog.dismiss();
        }
    }

    @Override
    public void editPassword(String string) {

    }

    @Override
    public void versionUpdate(String string) {

    }

    @Override
    public void exitApp(String string) {

    }


    @Override
    public void editPersonelInfo(String string) {
        Log.i(TAG, "editPersonelInfo: " + string);
        if ("Success".equals(string)) {
            UploadDialogUtils.closeDialog(uploadDialog);
            AdminData admin = AdminDao.getAdmin();
            admin.setTelephone(tel);
            AdminDao.updateAdminData(admin);
            finish();
        }else {
            UploadDialogUtils.closeDialog(uploadDialog);
            ToastUtils.showShortToast(EditAdminInfoActivity.this,"用户信息修改失败");
        }
    }

}
