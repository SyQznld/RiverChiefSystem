package com.appler.riverchiefsystem.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appler.riverchiefsystem.MainActivity;
import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.base.BaseActivity;
import com.appler.riverchiefsystem.dao.daoBean.AdminData;
import com.appler.riverchiefsystem.dao.AdminDao;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdminDetailActivity extends BaseActivity {
    @BindView(R.id.iv_toolbar_back)
    ImageView iv_back;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_title;
    @BindView(R.id.iv_toolbar_imageview)
    ImageView iv_edit;
    @BindView(R.id.sv_search)
    SearchView sv_search;
    @BindView(R.id.civ_admin_detail_head)
    CircleImageView civ_head;
    @BindView(R.id.tv_admin_detail_name)
    TextView tv_name;
    @BindView(R.id.tv_admin_detail_role)
    TextView tv_role;
    @BindView(R.id.tv_admin_detail_tel)
    TextView tv_tel;
    @BindView(R.id.tv_admin_detail_section)
    TextView tv_section;
    @BindView(R.id.tv_admin_detail_sex)
    TextView tv_sex;
    @BindView(R.id.tv_admin_detail_hierarchy)
    TextView tv_hierarchy;
    @BindView(R.id.tv_admin_detail_number)
    TextView tv_number;
    @BindView(R.id.tv_admin_detail_iccard)
    TextView tv_icard;
    private String TAG = getClass().getSimpleName();

    @Override
    protected int bindLayout() {
        return R.layout.activity_admin_detail;
    }

    @Override
    protected void doBuissness(Context context) {

        tv_title.setText("个人详情");
        iv_edit.setImageResource(R.drawable.ic_edit);
        sv_search.setVisibility(View.GONE);
    }

    private void initAdminInfo() {
        AdminData admin = AdminDao.getAdmin();
        if (null != admin) {
//            String staff_photo = admin.getStaffPhoto();
            Glide.with(this).load("").apply(new RequestOptions().error(R.mipmap.hezhang)).into(civ_head);
            tv_name.setText(admin.getName());
            tv_role.setText(admin.getRolename());
            tv_tel.setText(admin.getTelephone());
            tv_section.setText(admin.getDepartment());
        }
    }


    @OnClick({R.id.iv_toolbar_back, R.id.iv_toolbar_imageview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:
                Intent intent1 = new Intent(AdminDetailActivity.this, MainActivity.class);
                intent1.putExtra("flag", 2);
                startActivity(intent1);
                finish();
                break;
            case R.id.iv_toolbar_imageview:  //编辑个人信息
                Intent intent = new Intent(this, EditAdminInfoActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //显示登录用户详情
        initAdminInfo();
    }
}
