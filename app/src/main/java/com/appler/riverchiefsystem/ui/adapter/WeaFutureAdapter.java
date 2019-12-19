package com.appler.riverchiefsystem.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.bean.WeatherInfoByCityData;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 未来几天的天气预报
 * */
public class WeaFutureAdapter extends BaseQuickAdapter<WeatherInfoByCityData.ResultBean.FutureBean, BaseViewHolder> {
    private Context context;
    private List<WeatherInfoByCityData.ResultBean.FutureBean> data;

    public WeaFutureAdapter(Context context, @Nullable List<WeatherInfoByCityData.ResultBean.FutureBean> data) {
        super(R.layout.wea_futureday_item, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final WeatherInfoByCityData.ResultBean.FutureBean item) {
        TextView tv_temperature = helper.getView(R.id.tv_nextday_temperature);
        TextView tv_weather = helper.getView(R.id.tv_nextday_weather);
        TextView tv_week = helper.getView(R.id.tv_nextday_week);
        TextView tv_wind = helper.getView(R.id.tv_nextday_wind);

        tv_temperature.setText(item.getTemperature());

        String fb = "w" + item.getWeather_id().getFb();
        int mipmapId = context.getResources().getIdentifier(fb, "mipmap", context.getPackageName());
        Resources res = context.getResources();
        Drawable img = res.getDrawable(mipmapId);
        // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
        tv_weather.setCompoundDrawables(img, null, null, null); //设置左图标
        tv_weather.setText(item.getWeather());

        tv_week.setText(item.getWeek());
        tv_wind.setText(item.getWind());
    }
}