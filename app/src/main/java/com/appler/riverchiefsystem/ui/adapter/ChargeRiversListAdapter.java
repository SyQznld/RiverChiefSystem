package com.appler.riverchiefsystem.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.appler.riverchiefsystem.MainActivity;
import com.appler.riverchiefsystem.R;
import com.appler.riverchiefsystem.bean.ChargeRiversData;
import com.appler.riverchiefsystem.dao.AdminDao;
import com.appler.riverchiefsystem.global.Global;
import com.appler.riverchiefsystem.ui.activity.RiverDetailActivity;
import com.appler.riverchiefsystem.utils.CoordinateUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 负责河流
 */
public class ChargeRiversListAdapter extends BaseQuickAdapter<ChargeRiversData.DataBean, BaseViewHolder> {
    private Context context;
    private List<ChargeRiversData.DataBean> data;

    public ChargeRiversListAdapter(Context context, @Nullable List<ChargeRiversData.DataBean> data) {
        super(R.layout.charge_rivers_item, data);
        this.context = context;
        this.data = data;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(final BaseViewHolder helper, final ChargeRiversData.DataBean item) {
        CardView cv_river = helper.getView(R.id.cv_chargeRiver);

        ImageView iv_chargeRiver_guji = helper.getView(R.id.iv_chargeRiver_guji);
        TextView tv_chargeRiver_chargeRiverName = helper.getView(R.id.tv_chargeRiver_chargeRiverName);
        GradientDrawable gdOne = (GradientDrawable) tv_chargeRiver_chargeRiverName.getBackground();
        gdOne.setColor(Color.parseColor(randomColor()));
        TextView tv_chargeRiver_heduanName = helper.getView(R.id.tv_chargeRiver_heduanName);
        TextView tv_chargeRiver_xiangName = helper.getView(R.id.tv_chargeRiver_xiangName);
        TextView tv_chargeRiver_cunName = helper.getView(R.id.tv_chargeRiver_cunName);
        TextView tv_chargeRiver_hezhang = helper.getView(R.id.tv_chargeRiver_hezhang);
        tv_chargeRiver_chargeRiverName.setText(item.get河流名称());
        tv_chargeRiver_heduanName.setText(item.get河段名称());
        tv_chargeRiver_xiangName.setText(item.get所在乡镇());
        tv_chargeRiver_cunName.setText(item.get所经村名());
        String rolename = AdminDao.getAdmin().getRolename();


        if (rolename.equals(Global.ROLENAME_XIANG_HEZHANG) || rolename.equals(Global.ROLENAME_ZHEN_HEZHANG)) {
            tv_chargeRiver_hezhang.setText("村河长：" + item.get村河长());
        } else if (rolename.equals(Global.ROLENAME_CUN_HEZHANG)) {
            tv_chargeRiver_hezhang.setText("镇河长：" + item.get镇河长());
        }

        //LINESTRING (113.029102558474 33.7355714111158, 113.047023412 33.7382687900001, 113.050901919019 33.7388171135183)
        iv_chargeRiver_guji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //河段轨迹显示   wgs84坐标
                List<String> list = new ArrayList<>();
                String ogr_geometry = item.getOgr_geometry();
//                String ogr_geometry = "LINESTRING (112.7063026758536 33.72903930438314, 112.6952383344261 33.7171019901731, 112.6888710909993 33.71423358007935, 112.6840348263222 33.71238072071098, 112.6810740867298 33.71233782704598, 112.6765369284871 33.71475055676323, 112.6731248626964 33.71970998794188, 112.6695705001772 33.72493129146461, 112.6654434885016 33.7282213742025, 112.662023156769 33.73088935008712, 112.6575395884259 33.73349951686345, 112.6545628495582 33.73545752348475, 112.6446712952196 33.74183744101458, 112.6420568980753 33.74377759830465, 112.6383976677076 33.74608276876489, 112.6373200906571 33.74713040778946, 112.6361582094628 33.74755457892, 112.635236817181 33.74777243325244, 112.6342226183284 33.7474031795184, 112.6333840478223 33.74665919468117, 112.6317057933627 33.74620907399936, 112.6305417970863 33.7454453138536, 112.6300217890579 33.74393820412342, 112.629494459703 33.74136798066023, 112.629110492755 33.7375263486791, 112.629051846196 33.7358358732646, 112.6289786319908 33.73515005033871)";
                if (ogr_geometry.contains("(") && ogr_geometry.contains(")")) {
                    String restr = ogr_geometry.substring(ogr_geometry.indexOf("(") + 1, ogr_geometry.indexOf(")"));
                    String s = restr.replaceAll(", ", ",");
                    if (s.contains(",")) {
                        String[] split = s.split(",");
                        for (int i = 0; i < split.length; i++) {
                            String s1 = split[i];
                            String[] s2 = s1.split(" ");
                            //坐标转换 将WGS84坐标转换为高德坐标
                            LatLng latLng = CoordinateUtil.GPSToGaode(context, new LatLng(Double.parseDouble(s2[1]), Double.parseDouble(s2[0])));//112.97043429904514      33.582860243055556
                            list.add(String.valueOf(latLng.longitude) + " " + String.valueOf(latLng.latitude));
                        }
                    }
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("flag", 1);
                    intent.putExtra("patrolGps", TextUtils.join(",", list));
                    intent.putExtra("heduan", item.get河段名称());
                    intent.putExtra("riverName", item.get河流名称());
                    intent.putExtra("type", "河湖信息");
                    context.startActivity(intent);
                }
            }
        });
        //河段详情信息
        cv_river.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RiverDetailActivity.class);
                intent.putExtra("river", item);
                context.startActivity(intent);
            }
        });
    }





    /**
     * 随机生成颜色值
     */
    public static String randomColor() {
        //红色
        String red;
        //绿色
        String green;
        //蓝色
        String blue;
        //生成随机对象
        Random random = new Random();
        //生成红色颜色代码
        red = Integer.toHexString(random.nextInt(256)).toUpperCase();
        //生成绿色颜色代码
        green = Integer.toHexString(random.nextInt(256)).toUpperCase();
        //生成蓝色颜色代码
        blue = Integer.toHexString(random.nextInt(256)).toUpperCase();

        //判断红色代码的位数
        red = red.length() == 1 ? "0" + red : red;
        //判断绿色代码的位数
        green = green.length() == 1 ? "0" + green : green;
        //判断蓝色代码的位数
        blue = blue.length() == 1 ? "0" + blue : blue;
        //生成十六进制颜色值
        String color = "#" + red + green + blue;
        return color;
    }


}