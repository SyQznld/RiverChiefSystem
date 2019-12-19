package com.appler.riverchiefsystem.utils;

import com.appler.riverchiefsystem.utils.filter.FilterEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据源
 * category 月份
 * sort  分类
 * org_filter 人员
 * filter 阶段
 */
public class ModelUtil {


    /**
     * 任务
     * 阶段（问题级别）  轻度、中度、重度
     */
    //任务解决程度
    public static List<FilterEntity> getTaskGrade() {
        List<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity("全部", "0"));
        list.add(new FilterEntity("待解决", "1"));
        list.add(new FilterEntity("正在解决", "2"));
        list.add(new FilterEntity("不能解决", "3"));
        list.add(new FilterEntity("已解决", "4"));
        return list;
    }

    /**
     * 任务
     * 分类 我接收的 我发送的
     */
    //任务归属
    public static List<FilterEntity> getTaskBelong() {
        List<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity("全部", "0"));
        list.add(new FilterEntity("我接收的", "1"));
        list.add(new FilterEntity("我派发的", "2"));
        list.add(new FilterEntity("我交办的", "3"));
        return list;
    }




    //    公示牌是否设置完整、水质是否清洁、岸边有无乱堆乱放现象、有无漂浮物、有无排污口
    public static List<FilterEntity> getRiverProblemType() {
        List<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity("全部", "0"));
        list.add(new FilterEntity("公示牌是否设置完整", "1"));
        list.add(new FilterEntity("水质是否清洁", "2"));
        list.add(new FilterEntity("岸边有无乱堆乱放现象", "3"));
        list.add(new FilterEntity("有无漂浮物", "4"));
        list.add(new FilterEntity("有无排污口", "5"));
        return list;
    }



    //问题解决程度
    public static List<FilterEntity> getProblemResove() {
        List<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity("全部", "0"));
        list.add(new FilterEntity("待处理", "1"));
        list.add(new FilterEntity("正在处理", "2"));
        list.add(new FilterEntity("已解决", "3"));
        return list;
    }


    public static List<FilterEntity> getProblemBelong() {
        List<FilterEntity> list = new ArrayList<>();
        list.add(new FilterEntity("全部", "0"));
        list.add(new FilterEntity("我上报的", "1"));
        list.add(new FilterEntity("我接收的", "2"));
        return list;
    }

}
