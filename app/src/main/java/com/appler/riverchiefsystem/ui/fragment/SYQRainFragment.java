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
import com.appler.riverchiefsystem.bean.SYQ_RainData;
import com.appler.riverchiefsystem.presenter.SYQInfoPresenter;
import com.appler.riverchiefsystem.ui.adapter.SYQ_RainInfoAdapter;
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
 * 水雨情--雨情
 */
public class SYQRainFragment extends BaseFragment implements SYQInfoView {


    @BindView(R.id.et_syq_rain)
    EditText etSyqRain;
    @BindView(R.id.tv_syq_rain_search)
    TextView tvSyqRainSearch;
    @BindView(R.id.rv_syq_rain)
    RecyclerView rvSyqRain;
    @BindView(R.id.sl_syq_rain)
    StateLayout slSyqRain;
    @BindView(R.id.srl_syq_rain)
    SmartRefreshLayout refreshLayout;
    private String TAG = getClass().getSimpleName();
    private SYQ_RainInfoAdapter rainInfoAdapter;
    private List<SYQ_RainData.DataBean> dataBeanList = new ArrayList<>();
    private SYQInfoPresenter syqInfoPresenter;
    private String keyword = "";

    public SYQRainFragment() {
        // Required empty public constructor
    }


    @Override
    protected int setFragmentView() {
        return R.layout.fragment_syqrain;
    }

    @Override
    protected void initFragmentView() {

    }

    @Override
    protected void doFragmentBusiness() {

        syqInfoPresenter = new SYQInfoPresenter(this);
        rvSyqRain.setLayoutManager(new LinearLayoutManager(getActivity()));
        rainInfoAdapter = new SYQ_RainInfoAdapter(getActivity(), dataBeanList);
        rvSyqRain.setAdapter(rainInfoAdapter);

        initState();

        //下拉刷新 上拉加载
        initRefreshAndLoad();
    }


    /**
     * 判断网络
     */
    private void initState() {
        if (!CommonUtils.isNetConnected(getActivity())) {
            slSyqRain.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
            slSyqRain.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                @Override
                public void refreshClick() {
                    if (!CommonUtils.isNetConnected(getActivity())) {
                        ToastUtils.showToast(getActivity(), "请确保网络连接正常~");
                    } else {
                        dataBeanList.clear();
                        requestSYQRainInfo();
                        slSyqRain.showContentView();
                    }
                }

                @Override
                public void loginClick() {

                }
            });
        } else {
            dataBeanList.clear();
            requestSYQRainInfo();
            slSyqRain.showContentView();
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


    //请求雨情信息
    private void requestSYQRainInfo() {
        syqInfoPresenter.getRainInfo(FlagConstant.FLAG_SYQ_RAIN_LIST, keyword);
    }




    @OnClick(R.id.tv_syq_rain_search)
    public void onViewClicked() {
        keyword = etSyqRain.getText().toString();
        requestSYQRainInfo();
    }

    @Override
    public void getSKSWInfo(String string) {
    }

    @Override
    public void getRainInfo(String string) {
        Log.i(TAG, "getRainInfo: " + string);
        dataBeanList.clear();
        try {
            JSONObject jsonObject = new JSONObject(string);
            String msg = jsonObject.getString("msg");
            if ("success".equals(msg)) {
                SYQ_RainData rainData = new Gson().fromJson(string, new TypeToken<SYQ_RainData>() {
                }.getType());
                List<SYQ_RainData.DataBean> data = rainData.getData();
                if (data.size() == 0) {
                    slSyqRain.showEmptyView("", R.drawable.ic_no_data);
                } else {
                    slSyqRain.showContentView();
                    dataBeanList.addAll(data);
                    rainInfoAdapter.notifyDataSetChanged();
                }
            } else {
                ToastUtils.showShortToast(getActivity(), msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getRiversInfo(String string) {

    }
}
