package com.appler.riverchiefsystem.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.api.FlagConstant;
import com.appler.riverchiefsystem.base.BaseActivity;
import com.appler.riverchiefsystem.dao.daoBean.AdminData;
import com.appler.riverchiefsystem.bean.ChooseData;
import com.appler.riverchiefsystem.bean.ChooseUserData;
import com.appler.riverchiefsystem.dao.AdminDao;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.presenter.ChooseUsersPresenter;
import com.appler.riverchiefsystem.ui.adapter.ChooseUserAdapter;
import com.appler.riverchiefsystem.view.ChooseUsersView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseUserActivity extends BaseActivity implements ChooseUsersView {
    @BindView(R.id.iv_toolbar_back)
    ImageView iv_back;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_title;
    @BindView(R.id.sv_search)
    SearchView sv_search;
    @BindView(R.id.et_choose_search)
    EditText et_search;
    @BindView(R.id.rv_choose_users)
    RecyclerView rv_users;
    @BindView(R.id.ll_choose)
    LinearLayout ll_choose;
    @BindView(R.id.tv)
    TextView tv_text;
    @BindView(R.id.tv_choose_number)
    TextView tv_number;
    @BindView(R.id.btn_choose_complete)
    Button btn_complete;
    private String TAG = getClass().getSimpleName();
    private ChooseUsersPresenter chooseUsersPresenter;
    private ChooseUserAdapter chooseUserAdapter;
    private List<ChooseData> mList;
    private List<ChooseData> chooseDataList;
    private List<ChooseData> allDatalist = new ArrayList<>();


    @Override
    protected int bindLayout() {
        return R.layout.activity_choose_user;
    }

    @Override
    protected void doBuissness(Context context) {
        tv_title.setText("人员选择");
        sv_search.setVisibility(View.GONE);

        String type = getIntent().getStringExtra("type");

        mList = new ArrayList<>();
        chooseDataList = new ArrayList<>();
        rv_users.setLayoutManager(new LinearLayoutManager(this));
        chooseUserAdapter = new ChooseUserAdapter(context, mList);
        rv_users.setAdapter(chooseUserAdapter);

        chooseUsersPresenter = new ChooseUsersPresenter(this);


        AdminData admin = AdminDao.getAdmin();
        if (null != admin) {
            //支队科研所 返回全部总路长
            if ("zdkys".equals(type)) {
                chooseUsersPresenter.getAllZongluzhang(FlagConstant.FLAG_ALL_ZONGLUZHNAG_PERSON);
            } else if (Global.MESSAGE_TYPE_SHANGBAO.equals(type)) {
                //问题相关 上级领导
                chooseUsersPresenter.getSuperiorPerson(FlagConstant.FLAG_SUPERIOR_PERSON, String.valueOf(admin.getUserId()));
            } else {
                if ("总路长".equals(admin.getRolename())) {
                    //任务相关  总路长权限返回一二级路长
                    chooseUsersPresenter.getYiErJiLuzhang(
                            FlagConstant.FLAG_YIERJI_LUZHANG_PERSON,
                            admin.getDepartment());
                } else {
                    //任务相关  显示登录角色的下级人员列表
                    chooseUsersPresenter.getChargeUsers(
                            FlagConstant.FLAG_CHARGE_PERSON,
                            admin.getUserId());
                }

            }
        }


        tv_number.setVisibility(View.GONE);
        tv_text.setVisibility(View.GONE);
        chooseUserAdapter.setOnItemClickListener(new ChooseUserAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, List<ChooseData> datas) {
                /**单选人员
                 * */
                chooseDataList.removeAll(chooseDataList);
                for (ChooseData data : datas) {
                    data.setSelect(false);
                }
                ChooseData chooseData = datas.get(position);
                chooseData.setSelect(true);
                btn_complete.setEnabled(true);
                setBtnBackGround(100);
                chooseUserAdapter.notifyDataSetChanged();
                Log.i(TAG, "onItemClickListener: " + chooseData);
                chooseDataList.add(chooseData);
                Log.i(TAG, "onItemClickListener: " + chooseDataList);
            }
        });


        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String editable = s.toString();
                if ("".equals(editable) || null == editable) {
                    mList = new ArrayList<>();
                    mList = allDatalist;
                    chooseUserAdapter.notifyAdapter(mList, false);
                } else {
                    mList = new ArrayList<>();
                    String inputStr = et_search.getText().toString();
                    for (int i = 0; i < allDatalist.size(); i++) {
                        ChooseUserData.DataBean dataBean = allDatalist.get(i).getChooseUserData();
                        String staff_name = dataBean.getStaff_name();
                        String section_name = dataBean.getSection_name();
                        if (staff_name.contains(inputStr) || section_name.contains(inputStr)) {
                            mList.add(allDatalist.get(i));
                        }
                    }
                    chooseUserAdapter.notifyAdapter(mList, false);
                }
            }
        });
    }

    @OnClick({R.id.iv_toolbar_back, R.id.btn_choose_complete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_choose_complete://选择人员 完成按钮返回选择人员
                ChooseUserData.DataBean chooseUserData = chooseDataList.get(0).getChooseUserData();
                Intent intent = new Intent();
                intent.putExtra("chooseUserData", chooseUserData);
//                intent.putParcelableArrayListExtra("chooseUser", (ArrayList<? extends Parcelable>) chooseDataList);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.iv_toolbar_back:
                finish();
                break;
        }
    }

    private void setBtnBackGround(int size) {
        if (size != 0) {
            btn_complete.setBackgroundColor(Color.parseColor("#2a7fd7"));
            btn_complete.setEnabled(true);
            btn_complete.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else {
            btn_complete.setBackgroundColor(Color.parseColor("#888888"));
            btn_complete.setEnabled(false);
            btn_complete.setTextColor(ContextCompat.getColor(this, R.color.textcolor));
        }


    }


    /**
     * 问题相关 所有人员
     */
    @Override
    public void getAllUsers(String string) {
        Log.i(TAG, "getAllUsers: " + string);
        if (!"".equals(string) && string.contains("staff")) {
            List<ChooseData> chooseDataList = new ArrayList<>();
            ChooseUserData chooseUserData = new Gson().fromJson(string, new TypeToken<ChooseUserData>() {
            }.getType());

            List<ChooseUserData.DataBean> dataBeanList = chooseUserData.getData();

            for (ChooseUserData.DataBean dataBean : dataBeanList) {
                ChooseData chooseData = new ChooseData();
                chooseData.setChooseUserData(dataBean);
                chooseDataList.add(chooseData);
            }

            mList = chooseDataList;
            allDatalist = chooseDataList;
            chooseUserAdapter.notifyAdapter(mList, false);
        }
    }


    /**
     * 任务相关 下属人员
     */
    @Override
    public void getChargeUsers(String string) {
        Log.i(TAG, "getChargeUsers: " + string);
        if (!"".equals(string) && string.contains("staff")) {
            List<ChooseData> chooseDataList = new ArrayList<>();
            ChooseUserData chooseUserData = new Gson().fromJson(string, new TypeToken<ChooseUserData>() {
            }.getType());

            List<ChooseUserData.DataBean> dataBeanList = chooseUserData.getData();

            for (ChooseUserData.DataBean dataBean : dataBeanList) {
                ChooseData chooseData = new ChooseData();
                chooseData.setChooseUserData(dataBean);
                chooseDataList.add(chooseData);
            }

            mList = chooseDataList;
            allDatalist = chooseDataList;
            chooseUserAdapter.notifyAdapter(mList, false);
        }
    }

    /**
     * 问题相关 上级领导
     */
    @Override
    public void getSuperiorPerson(String string) {
        Log.i(TAG, "getSuperiorPerson: " + string);
        if (!"".equals(string) && string.contains("staff")) {
            List<ChooseData> chooseDataList = new ArrayList<>();
            ChooseUserData chooseUserData = new Gson().fromJson(string, new TypeToken<ChooseUserData>() {
            }.getType());

            List<ChooseUserData.DataBean> dataBeanList = chooseUserData.getData();

            for (ChooseUserData.DataBean dataBean : dataBeanList) {
                ChooseData chooseData = new ChooseData();
                chooseData.setChooseUserData(dataBean);
                chooseDataList.add(chooseData);
            }

            mList = chooseDataList;
            allDatalist = chooseDataList;
            chooseUserAdapter.notifyAdapter(mList, false);
        }
    }

    /**
     * 所有总路长 支队科研所
     */
    @Override
    public void getAllZongluzhang(String string) {
        Log.i(TAG, "getAllZongluzhang: " + string);
        if (!"".equals(string) && string.contains("staff")) {
            List<ChooseData> chooseDataList = new ArrayList<>();
            ChooseUserData chooseUserData = new Gson().fromJson(string, new TypeToken<ChooseUserData>() {
            }.getType());

            List<ChooseUserData.DataBean> dataBeanList = chooseUserData.getData();

            for (ChooseUserData.DataBean dataBean : dataBeanList) {
                ChooseData chooseData = new ChooseData();
                chooseData.setChooseUserData(dataBean);
                chooseDataList.add(chooseData);
            }

            mList = chooseDataList;
            allDatalist = chooseDataList;
            chooseUserAdapter.notifyAdapter(mList, false);
        }
    }

    /**
     * 总路长返回所有一二级路长
     */

    @Override
    public void getYiErJiLuzhang(String string) {
        Log.i(TAG, "getYiErJiLuzhang: " + string);
        if (!"".equals(string) && string.contains("staff")) {
            List<ChooseData> chooseDataList = new ArrayList<>();
            ChooseUserData chooseUserData = new Gson().fromJson(string, new TypeToken<ChooseUserData>() {
            }.getType());

            List<ChooseUserData.DataBean> dataBeanList = chooseUserData.getData();

            for (ChooseUserData.DataBean dataBean : dataBeanList) {
                ChooseData chooseData = new ChooseData();
                chooseData.setChooseUserData(dataBean);
                chooseDataList.add(chooseData);
            }
            mList = chooseDataList;
            allDatalist = chooseDataList;
            chooseUserAdapter.notifyAdapter(mList, false);
        }
    }
}
