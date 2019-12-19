package com.appler.riverchiefsystem.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.bean.UserBean;
import com.appler.riverchiefsystem.utils.CommonUtils;
import com.appler.riverchiefsystem.utils.PhoneFormatCheckUtils;
import com.appler.riverchiefsystem.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 通讯录
 */
public class ContactsListAdapter extends BaseQuickAdapter<UserBean.DataBean, BaseViewHolder> {
    private Context context;
    private List<UserBean.DataBean> data;

    public ContactsListAdapter(Context context, @Nullable List<UserBean.DataBean> data) {
        super(R.layout.contact_item, data);
        this.context = context;
        this.data = data;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(final BaseViewHolder helper, final UserBean.DataBean item) {
        CardView cv_contacts = helper.getView(R.id.cv_contacts);
        TextView tv_contacts_name = helper.getView(R.id.tv_contacts_name);
        TextView tv_contacts_department = helper.getView(R.id.tv_contacts_department);
        TextView tv_contacts_zhize = helper.getView(R.id.tv_contacts_zhize);
        TextView tv_contacts_tel = helper.getView(R.id.tv_contacts_tel);

        tv_contacts_name.setText("姓名：  " + item.getName());
        tv_contacts_department.setText("职责：  " + item.getDepartment());
        tv_contacts_zhize.setText("角色：  " + item.getRolename());
        final String telephone = item.getTelephone();
        tv_contacts_tel.setText("电话：  " + telephone);
        cv_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean chinaPhoneLegal = PhoneFormatCheckUtils.isChinaPhoneLegal(telephone);   //校验手机号码
                if ( chinaPhoneLegal) {   //合法
                    CommonUtils.callPhone(context,telephone);
                }  else {
                    ToastUtils.showShortToast(context, "手机号码格式不对，请重试");
                }
            }
        });
    }
}