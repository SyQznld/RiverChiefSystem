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
import com.appler.riverchiefsystem.bean.SYQ_RiverData;
import com.appler.riverchiefsystem.presenter.SYQInfoPresenter;
import com.appler.riverchiefsystem.ui.adapter.SYQ_RiverInfoAdapter;
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
 * 水雨情--河道水位
 */
public class SYQRiversFragment extends BaseFragment implements SYQInfoView {
    private String TAG = getClass().getSimpleName();
    @BindView(R.id.et_syq_river)
    EditText etSyqRiver;
    @BindView(R.id.tv_syq_river_search)
    TextView tvSyqRiverSearch;
    @BindView(R.id.rv_syq_river)
    RecyclerView rvSyqRiver;
    @BindView(R.id.sl_syq_river)
    StateLayout slSyqRiver;
    @BindView(R.id.srl_syq_river)

    SmartRefreshLayout refreshLayout;
    private SYQ_RiverInfoAdapter riverInfoAdapter;
    private List<SYQ_RiverData.DataBean> dataBeanList = new ArrayList<>();
    private SYQInfoPresenter syqInfoPresenter;
    private String keyword = "";

    public SYQRiversFragment() {
        // Required empty public constructor
    }


    @Override
    protected int setFragmentView() {
        return R.layout.fragment_syqriver;
    }

    @Override
    protected void initFragmentView() {

    }

    @Override
    protected void doFragmentBusiness() {

        syqInfoPresenter = new SYQInfoPresenter(this);
        rvSyqRiver.setLayoutManager(new LinearLayoutManager(getActivity()));
        riverInfoAdapter = new SYQ_RiverInfoAdapter(getActivity(), dataBeanList);
        rvSyqRiver.setAdapter(riverInfoAdapter);

        initState();

        //下拉刷新 上拉加载
        initRefreshAndLoad();

    }


    /**
     * 判断网络
     */
    private void initState() {
        if (!CommonUtils.isNetConnected(getActivity())) {
            slSyqRiver.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
            slSyqRiver.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                @Override
                public void refreshClick() {
                    if (!CommonUtils.isNetConnected(getActivity())) {
                        ToastUtils.showToast(getActivity(), "请确保网络连接正常~");
                    } else {
                        dataBeanList.clear();
                        requestSYQRiverInfo();
                        slSyqRiver.showContentView();
                    }
                }

                @Override
                public void loginClick() {

                }
            });
        } else {
            dataBeanList.clear();
            requestSYQRiverInfo();
            slSyqRiver.showContentView();
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


    //请求河道信息
    private void requestSYQRiverInfo() {
        syqInfoPresenter.getRiversInfo(FlagConstant.FLAG_SYQ_RIVERS_LIST, keyword);
    }


    @OnClick(R.id.tv_syq_river_search)
    public void onViewClicked() {
        keyword = etSyqRiver.getText().toString();
        requestSYQRiverInfo();
    }

    @Override
    public void getSKSWInfo(String string) {
    }

    @Override
    public void getRainInfo(String string) {

    }

    @Override
    public void getRiversInfo(String string) {
        Log.i(TAG, "getRiversInfo: " + string);
        dataBeanList.clear();
        try {
            JSONObject jsonObject = new JSONObject(string);
            String msg = jsonObject.getString("msg");
            if ("success".equals(msg)) {
                SYQ_RiverData riverData = new Gson().fromJson(string, new TypeToken<SYQ_RiverData>() {
                }.getType());
                List<SYQ_RiverData.DataBean> data = riverData.getData();
                if (data.size() == 0) {
                    slSyqRiver.showEmptyView("", R.drawable.ic_no_data);
                } else {
                    slSyqRiver.showContentView();
                    dataBeanList.addAll(data);
                    riverInfoAdapter.notifyDataSetChanged();
                }
            } else {
                ToastUtils.showShortToast(getActivity(), msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
