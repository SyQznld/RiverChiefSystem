package com.appler.riverchiefsystem.ui.fragment;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.api.IGetFilterCity;
import com.appler.riverchiefsystem.base.BaseFragment;
import com.appler.riverchiefsystem.bean.AllCitysData;
import com.appler.riverchiefsystem.bean.WeatherInfoByCityData;
import com.appler.riverchiefsystem.ui.adapter.WeaFutureAdapter;
import com.appler.riverchiefsystem.utils.CommonUtils;
import com.appler.riverchiefsystem.utils.LocationUtils;
import com.appler.riverchiefsystem.utils.MainHandler;
import com.appler.riverchiefsystem.utils.ToastUtils;
import com.appler.riverchiefsystem.utils.WeatherInfoRequestHttps;
import com.appler.riverchiefsystem.utils.customView.UploadDialogUtils;
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
import butterknife.Unbinder;

/**
 * 尚未开始
 */
public class SYQWeatherFragment extends BaseFragment {
    @BindView(R.id.srl_weather)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.sl_weather)
    StateLayout sl_weather;
    @BindView(R.id.rl_weather_city)
    RelativeLayout rl_weather_city;
    @BindView(R.id.tv_weather_city)
    TextView tvWeatherCity;
    @BindView(R.id.tv_weather_hour)
    TextView tvWeatherHour;
    @BindView(R.id.tv_weaToday_temp)
    TextView tvWeaTodayTemp;
    @BindView(R.id.tv_weaToday_wind)
    TextView tvWeaTodayWind;
    @BindView(R.id.tv_weaToday_humidity)
    TextView tvWeaTodayHumidity;
    @BindView(R.id.rv_weather_nextday)
    RecyclerView rvWeatherNextday;
    @BindView(R.id.tv_weaDetail_wind)
    TextView tvWeaDetailWind;
    @BindView(R.id.tv_weaDetail_dressing_index)
    TextView tvWeaDetailDressingIndex;
    @BindView(R.id.tv_weaDetail_dressing_advice)
    TextView tvWeaDetailDressingAdvice;
    @BindView(R.id.tv_weaDetail_uv_index)
    TextView tvWeaDetailUvIndex;
    @BindView(R.id.tv_weaDetail_wash_index)
    TextView tvWeaDetailWashIndex;
    @BindView(R.id.tv_weaDetail_travel_index)
    TextView tvWeaDetailTravelIndex;
    @BindView(R.id.tv_weaDetail_exercise_index)
    TextView tvWeaDetailExerciseIndex;
    @BindView(R.id.ll_weather)
    LinearLayout ll_weather;
    Unbinder unbinder;
    private String TAG = getClass().getSimpleName();

    private String lnglat = "";//经纬度（gps）
    private String cityName = "";//获取到的城市名称
    private List<String> filterCityList;  //过滤后的城市列表集合
    private Dialog uploadDialog;


    public SYQWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    protected int setFragmentView() {
        return R.layout.fragment_ysq_weather;
    }

    @Override
    protected void initFragmentView() {

        //定位设置
        LocationUtils locationUtils = new LocationUtils();
        lnglat = locationUtils.initGpsSetting(getActivity());

    }

    @Override
    protected void doFragmentBusiness() {

        initState();
        //下拉刷新 上拉加载
        initRefreshAndLoad();
    }


    /**
     * 判断网络
     */
    private void initState() {
        if (!CommonUtils.isNetConnected(getActivity())) {
            sl_weather.showNoNetworkView(R.string.no_net, R.drawable.ic_no_net);
            sl_weather.setRefreshListener(new StateLayout.OnViewRefreshListener() {
                @Override
                public void refreshClick() {
                    if (!CommonUtils.isNetConnected(getActivity())) {
                        ToastUtils.showToast(getActivity(), "请确保网络连接正常~");
                    } else {
                        sl_weather.showContentView();
                        requestWeatherInfo();
                    }
                }

                @Override
                public void loginClick() {

                }
            });
        } else {
            sl_weather.showContentView();
            requestWeatherInfo();
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


    @OnClick({R.id.rl_weather_city})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.rl_weather_city:  //下拉获取城市列表（过滤后的）
                if (null != filterCityList) {
                    CommonUtils.SetCitySinnper(filterCityList, tvWeatherCity, getActivity(), true, new IGetFilterCity() {
                        @Override
                        public void getFilterCities(final String string) {

                            WeatherInfoRequestHttps.getInstance().requestWeatherByCityName(string, new WeatherInfoRequestHttps.GetWeatherInfoByCityNameCallBack() {
                                @Override
                                public void onFailure(String s) {
                                    toastErrorMsg(s);
                                }

                                @Override
                                public void getInfoByCityName(String s) {
                                    Log.i(TAG, "getInfoByCityName: " + s);
                                    if (s.contains("resultcode")) {

                                        getHttpsWeaData(string);
                                    } else {   //错误
                                        try {
                                            JSONObject jsonObject = new JSONObject(s);
                                            String msg = jsonObject.getString("reason");
                                            ToastUtils.showShortToast(getActivity(), msg);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                        }
                    });
                }
                break;
        }

    }

    /**
     * 通过GPS坐标获取天气信息
     */
    private void requestWeatherInfo() {
//        lnglat = "113.634257, 34.869946";     //郑州
//        lnglat = "113.192954, 33.766207";     //平顶山
//        lnglat = "109.840405,40.658168";        //包头
        if (!"".equals(lnglat) && lnglat.contains(",")) {
            uploadDialog = UploadDialogUtils.createLoadingDialog(getActivity(), "正在加载");
            String[] split = lnglat.split(",");
            WeatherInfoRequestHttps.getInstance().requestCityWeatherByGps(split[0], split[1], new WeatherInfoRequestHttps.GetCityWeatherInfoByGpsCallBack() {
                @Override
                public void onFailure(final String s) {
                    toastErrorMsg(s);
                }

                @Override
                public void getCityWeatherInfo(final String string) {
                    getHttpsWeaData(string);
                }
            });
        } else {
            ll_weather.setVisibility(View.GONE);
            toastErrorMsg("未获取到经纬度信息，请到宽阔区域重试");
        }
    }


    /**
     * 填充基本信息
     */
    private void initWeatherInfo(String string) {
        WeatherInfoByCityData weatherInfoByCityData = new Gson().fromJson(string, new TypeToken<WeatherInfoByCityData>() {
        }.getType());
        WeatherInfoByCityData.ResultBean.TodayBean today = weatherInfoByCityData.getResult().getToday();
        WeatherInfoByCityData.ResultBean.SkBean sk = weatherInfoByCityData.getResult().getSk();
        String fa = "w" + today.getWeather_id().getFa();
        String fb = "w" + today.getWeather_id().getFb();


        //实况信息
        cityName = today.getCity();
        tvWeatherCity.setText(cityName);
        tvWeatherHour.setText(today.getDate_y());

        int mipmapId = getResources().getIdentifier(fb, "mipmap", getActivity().getPackageName());
        Resources res = getResources();
        Drawable img = res.getDrawable(mipmapId);
        // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
        tvWeaTodayTemp.setCompoundDrawables(img, null, null, null); //设置左图标
        tvWeaTodayTemp.setText(today.getWeather() + "    " + sk.getTemp() + "℃");
        tvWeaTodayWind.setText(sk.getWind_direction() + "    " + sk.getWind_strength());

        tvWeaTodayHumidity.setText(sk.getHumidity());
        if (!"".equals(cityName)) {
            rl_weather_city.setEnabled(true);
        } else {
            rl_weather_city.setEnabled(false);
        }

        //接下来几天
        List<WeatherInfoByCityData.ResultBean.FutureBean> futureBeanList = weatherInfoByCityData.getResult().getFuture();
        rvWeatherNextday.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvWeatherNextday.setAdapter(new WeaFutureAdapter(getActivity(), futureBeanList));

        //详情信息
        tvWeaDetailWind.setText(today.getWind());
        tvWeaDetailDressingIndex.setText(today.getDressing_index());
        tvWeaDetailDressingAdvice.setText(today.getDressing_advice());
        tvWeaDetailUvIndex.setText(today.getUv_index());
        tvWeaDetailWashIndex.setText(today.getWash_index());
        tvWeaDetailTravelIndex.setText(today.getTravel_index());
        tvWeaDetailExerciseIndex.setText(today.getExercise_index());
    }


    /**
     * 接口得到天气相关信息
     */
    private void getHttpsWeaData(final String string) {
        MainHandler.getInstance().post(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    String msg = jsonObject.getString("reason");
                    if ("successed!".equals(msg)) {
                        ll_weather.setVisibility(View.VISIBLE);
                        UploadDialogUtils.closeDialog(uploadDialog);

                        //填充数据
                        initWeatherInfo(string);

                    } else {
                        ll_weather.setVisibility(View.GONE);
                        UploadDialogUtils.closeDialog(uploadDialog);
                        ToastUtils.showShortToast(getActivity(), msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 得到当前城市关联城市 过滤后的
     */
    private void getFilterCities() {
        WeatherInfoRequestHttps.getInstance().requestAllCitys(new WeatherInfoRequestHttps.GetAllCitysCallBack() {
            @Override
            public void onFailure(final String s) {
                toastErrorMsg(s);
            }

            @Override
            public void getAllCitys(final String string) {
                MainHandler.getInstance().post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            String msg = jsonObject.getString("reason");
                            if ("successed".equals(msg)) {
                                filterCityList = new ArrayList<>();
                                AllCitysData allCitysData = new Gson().fromJson(string, new TypeToken<AllCitysData>() {
                                }.getType());
                                List<AllCitysData.ResultBean> resultBeanList = allCitysData.getResult();
                                for (AllCitysData.ResultBean resultBean : resultBeanList) {
                                    String city = resultBean.getCity();
                                    if (city.equals(cityName)) {
                                        filterCityList.add(resultBean.getDistrict());
                                    }
                                }

                            } else {
                                ToastUtils.showShortToast(getActivity(), msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    /**
     * 错误信息吐司
     */
    private void toastErrorMsg(final String s) {
        MainHandler.getInstance().post(new Runnable() {
            @Override
            public void run() {
                UploadDialogUtils.closeDialog(uploadDialog);
                ToastUtils.showShortToast(getActivity(), s);
                ll_weather.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

            //请求天气信息
//            requestWeatherInfo();

//            获取城市列表
//            getFilterCities();

        }
    }




}
