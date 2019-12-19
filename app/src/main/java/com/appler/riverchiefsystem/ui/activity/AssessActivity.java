package com.appler.riverchiefsystem.ui.activity;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.base.BaseActivity;
import com.appler.riverchiefsystem.bean.AssessData;
import com.appler.riverchiefsystem.utils.customView.CombinedChartViewManager;
import com.github.mikephil.charting.charts.CombinedChart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AssessActivity extends BaseActivity {
    private String TAG = getClass().getSimpleName();
    @BindView(R.id.iv_toolbar_tv_back)
    ImageView iv_toolbar_tv_back;
    @BindView(R.id.tv_toolbar_tv_title)
    TextView tv_toolbar_tv_title;
    @BindView(R.id.cc_self)
    CombinedChart cc_self;
    @BindView(R.id.cc_section)
    CombinedChart cc_section;


    @Override
    protected int bindLayout() {
        return R.layout.activity_assess;
    }

    @Override
    protected void doBuissness(Context context) {
        tv_toolbar_tv_title.setText("考核管理");
        List<AssessData> selfData = new ArrayList<>();
        selfData.add(new AssessData("卢向升", "2019-01", "96"));
        selfData.add(new AssessData("卢向升", "2019-02", "85"));
        selfData.add(new AssessData("卢向升", "2019-03", "90"));
        selfData.add(new AssessData("卢向升", "2019-04", "97"));
        selfData.add(new AssessData("卢向升", "2019-05", "95"));
        selfData.add(new AssessData("卢向升", "2019-06", "89"));
        selfData.add(new AssessData("卢向升", "2019-07", "93"));
        selfData.add(new AssessData("卢向升", "2019-08", "92"));
        selfData.add(new AssessData("卢向升", "2019-09", "87"));
        selfData.add(new AssessData("卢向升", "2019-10", "93"));
        initBarchart(selfData, "个人", cc_self);


        List<AssessData> sectionData = new ArrayList<>();
        sectionData.add(new AssessData("李会良", "2019-10", "94"));
        sectionData.add(new AssessData("卢向升", "2019-10", "92"));
        sectionData.add(new AssessData("张毅", "2019-10", "89"));
        sectionData.add(new AssessData("李明", "2019-10", "91"));
        sectionData.add(new AssessData("徐延杰", "2019-10", "90"));
        sectionData.add(new AssessData("张汤", "2019-10", "85"));
        sectionData.add(new AssessData("张瑶", "2019-10", "93"));
        sectionData.add(new AssessData("祝山山", "2019-10", "89"));
        sectionData.add(new AssessData("祝学雷", "2019-10", "90"));
        sectionData.add(new AssessData("杨勇", "2019-10", "85"));
        sectionData.add(new AssessData("蔡丽娜", "2019-10", "85"));
        sectionData.add(new AssessData("徐广才", "2019-10", "90"));
        sectionData.add(new AssessData("祝学圣", "2019-10", "92"));
        sectionData.add(new AssessData("杨冲", "2019-10", "93"));
        initBarchart(sectionData, "科室", cc_section);

    }


    //    直方图和曲线图初始化
    private void initBarchart(List<AssessData> data, String type, CombinedChart combinedChart) {

        //x轴数据
        List<String> xData = new ArrayList<>();
        //直方图y轴数据集合
        List<List<Float>> yBarDatas = new ArrayList<>();
        //y轴数
        List<Float> yBarData = new ArrayList<>();

        //线形图y轴数据集合
        List<List<Float>> yLineDatas = new ArrayList<>();
        //y轴数
        List<Float> yLineData = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            String value = null;
            if ("个人".equals(type)) {
                value = data.get(i).getMonth();
            } else if ("科室".equals(type)) {
                value = data.get(i).getUserName();
            }
            if (null == value || "".equals(value)) {
                xData.add("");
            } else {
                xData.add(value);
            }
            double bardata = Float.parseFloat(data.get(i).getScore());
            double linedata = bardata / 2;
            yBarData.add((float) bardata);
            yLineData.add((float) linedata);
        }
        yBarDatas.add(yBarData);
        yLineDatas.add(yLineData);


        //颜色集合
        List<Integer> colors = new ArrayList<>();
        if ("个人".equals(type)) {
            colors.add(getResources().getColor(R.color.blue_200));
            colors.add(getResources().getColor(R.color.qiandao));
        } else if ("科室".equals(type)) {
            colors.add(getResources().getColor(R.color.green_200));
            colors.add(getResources().getColor(R.color.purple_200));
        }



        //自己的考核
        CombinedChartViewManager combineChartManager1 = new CombinedChartViewManager(combinedChart);
        combineChartManager1.showCombinedChart(xData, yBarDatas.get(0), yLineDatas.get(0),
                "直方图", "中位数", colors.get(0), colors.get(1));
    }


    @OnClick({R.id.iv_toolbar_tv_back})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_toolbar_tv_back:
                finish();
                break;
        }
    }
}
