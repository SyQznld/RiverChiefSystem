package com.appler.riverchiefsystem.ui.fragment;


import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.api.FlagConstant;
import com.appler.riverchiefsystem.base.BaseFragment;
import com.appler.riverchiefsystem.bean.SYQ_SKSWData;
import com.appler.riverchiefsystem.presenter.SYQInfoPresenter;
import com.appler.riverchiefsystem.ui.adapter.SYQ_SkswInfoAdapter;
import com.appler.riverchiefsystem.utils.CommonUtils;
import com.appler.riverchiefsystem.utils.ToastUtils;
import com.appler.riverchiefsystem.view.SYQInfoView;
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
 * 水雨情--水库水位
 */
public class SYQShuikuFragment extends BaseFragment implements SYQInfoView {
    @BindView(R.id.et_syq_sksw)
    EditText etSyqSksw;
    @BindView(R.id.tv_syq_sksw_search)
    TextView tvSyqSkswSearch;
    @BindView(R.id.rv_syq_sksw)
    RecyclerView rvSyqSksw;
    @BindView(R.id.sl_syq_sksw)
    StateLayout slSyqSksw;
    @BindView(R.id.srl_syq_sksw)
    SmartRefreshLayout refreshLayout;
    private String TAG = getClass().getSimpleName();
    private SYQ_SkswInfoAdapter skswInfoAdapter;
    private List<SYQ_SKSWData.DataBean> dataBeanList = new ArrayList<>();
    private SYQInfoPresenter syqInfoPresenter;
    private String keyword = "";

    public SYQShuikuFragment() {
        // Required empty public constructor
    }


    @Override
    protected int setFragmentView() {
        return R.layout.fragment_task_unfinished;
    }

    @Override
    protected void initFragmentView() {

    }

    @Override
    protected void doFragmentBusiness() {
        syqInfoPresenter = new SYQInfoPresenter(this);
        rvSyqSksw.setLayoutManager(new LinearLayoutManager(getActivity()));
        skswInfoAdapter = new SYQ_SkswInfoAdapter(getActivity(), dataBeanList);
        rvSyqSksw.setAdapter(skswInfoAdapter);

        initState();

        //下拉刷新 上拉加载
        initRefreshAndLoad();
    }


    /**
     * 判断网络
     */
    private void initState() {
        if (!CommonUtils.isNetConnected(getActivity())) {
            slSyqSksw.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
            slSyqSksw.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                @Override
                public void refreshClick() {
                    if (!CommonUtils.isNetConnected(getActivity())) {
                        ToastUtils.showToast(getActivity(), "请确保网络连接正常~");
                    } else {
                        dataBeanList.clear();
                        requestSkList();
                        slSyqSksw.showContentView();
                    }
                }

                @Override
                public void loginClick() {

                }
            });
        } else {
            dataBeanList.clear();
            requestSkList();
            slSyqSksw.showContentView();
        }
    }


    /**
     * 下拉刷新上拉加载
     */
    private void initRefreshAndLoad() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

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


    //请求水库列表
    private void requestSkList() {
        syqInfoPresenter.getSKSWInfo(FlagConstant.FLAG_SYQ_SKSW_LIST, keyword);
    }




    @OnClick(R.id.tv_syq_sksw_search)
    public void onViewClicked() {
        keyword = etSyqSksw.getText().toString();
        requestSkList();
    }

    @Override
    public void getSKSWInfo(String string) {
        Log.i(TAG, "getSKSWInfo: " + string);
        dataBeanList.clear();
        try {
            JSONObject jsonObject = new JSONObject(string);
            String msg = jsonObject.getString("msg");
            if ("success".equals(msg)) {
                SYQ_SKSWData skswData = new Gson().fromJson(string, new TypeToken<SYQ_SKSWData>() {
                }.getType());
                List<SYQ_SKSWData.DataBean> data = skswData.getData();
                if (data.size() == 0) {
                    slSyqSksw.showEmptyView("", R.drawable.ic_no_data);
                } else {
                    slSyqSksw.showContentView();
                    dataBeanList.addAll(data);
                    skswInfoAdapter.notifyDataSetChanged();
                }
            } else {
                ToastUtils.showShortToast(getActivity(), msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getRainInfo(String string) {

    }

    @Override
    public void getRiversInfo(String string) {

    }
}
