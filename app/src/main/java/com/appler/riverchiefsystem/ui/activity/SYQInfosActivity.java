package com.appler.riverchiefsystem.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.base.BaseActivity;
import com.appler.riverchiefsystem.ui.adapter.PagerAdapter;
import com.appler.riverchiefsystem.ui.fragment.SYQRainFragment;
import com.appler.riverchiefsystem.ui.fragment.SYQRiversFragment;
import com.appler.riverchiefsystem.ui.fragment.SYQShuikuFragment;
import com.appler.riverchiefsystem.ui.fragment.SYQWeatherFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SYQInfosActivity extends BaseActivity {
    @BindView(R.id.iv_toolbar_back)
    ImageView iv_back;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_title;
    @BindView(R.id.sv_search)
    SearchView sv_search;
    @BindView(R.id.mic_task_list)
    MagicIndicator magicIndicator;
    @BindView(R.id.vp_task_list)
    ViewPager viewPager;
    private String TAG = getClass().getSimpleName();
    private List<String> titleList;
    private List<Fragment> fragmentList = new ArrayList<>();
    private PagerAdapter pagerAdapter;

    private SYQWeatherFragment SYQWeatherFragment;
    private SYQShuikuFragment SYQShuikuFragment;
    private SYQRiversFragment SYQRiversFragment;
    private SYQRainFragment syqRainFragment;


    @Override
    protected int bindLayout() {
        return R.layout.activity_syq_info;
    }

    @Override
    protected void doBuissness(Context context) {

        tv_title.setText("水雨情");
        sv_search.setVisibility(View.GONE);

        //ViewPager设置
        initViewPager();


        //viewPager 标题及数据
        initMagicIndicator();

    }


    @OnClick({R.id.iv_toolbar_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_back:
                finish();
                break;
        }
    }


    private void initViewPager() {
        fragmentList = new ArrayList<>();

        SYQWeatherFragment = new SYQWeatherFragment();
        fragmentList.add(SYQWeatherFragment);
        syqRainFragment = new SYQRainFragment();
        fragmentList.add(syqRainFragment);
        SYQRiversFragment = new SYQRiversFragment();
        fragmentList.add(SYQRiversFragment);
        SYQShuikuFragment = new SYQShuikuFragment();
        fragmentList.add(SYQShuikuFragment);


        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragmentList, this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(fragmentList.size());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        viewPager.setCurrentItem(0);
                        break;
                    case 1:
                        viewPager.setCurrentItem(1);
                        break;
                    case 2:
                        viewPager.setCurrentItem(2);
                        break;
                    case 3:
                        viewPager.setCurrentItem(3);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void initMagicIndicator() {
        titleList = new ArrayList<>();

        titleList.add("天气信息");
        titleList.add("雨情");
        titleList.add("河道水位");
        titleList.add("水库水位");


        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);//即标题平分屏幕宽度的模式
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titleList == null ? 0 : titleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(titleList.get(index));
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setNormalColor(Color.parseColor("#88000000"));
                simplePagerTitleView.setSelectedColor(Color.BLUE);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                        switch (index) {
                            case 0:
                                viewPager.setCurrentItem(0);
                                break;
                            case 1:
                                viewPager.setCurrentItem(1);
                                break;
                            case 2:
                                viewPager.setCurrentItem(2);
                                break;
                            case 3:
                                viewPager.setCurrentItem(3);
                                break;

                        }
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 4));
                indicator.setLineWidth(UIUtil.dip2px(context, 30));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(Color.BLUE);
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

}
