package com.appler.riverchiefsystem.api;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 后台接口测试
 * 20190919  修改
 */

public interface ApiService {

    /**
     * 登录相关
     */
    //    登录接口
    @FormUrlEncoded
    @POST("handler/IsAndroid.ashx")
    Observable<ResponseBody> appLogin(
            @Field("flag") String flag,
            @Field("username") String UserName,
            @Field("password") String Password);


    //退出登录
    @FormUrlEncoded
    @POST("appLoginOut")
    Observable<ResponseBody> appLoginOut(
            @Field("user") String user,
            @Field("password") String password);

    //顶掉上一个用户
    @FormUrlEncoded
    @POST("updateRegiser")
    Observable<ResponseBody> updateRegiser(
            @Field("username") String username,
            @Field("imei") String imei);


    /**
     * 巡查
     */

    //巡查记录
    @FormUrlEncoded
    @POST("handler/IsAndroid.ashx")
    Observable<ResponseBody> getPatrolList(
            @Field("flag") String flag,
            @Field("ID") int ID,
            @Field("keyword") String keyword);


    //巡查记录
    @FormUrlEncoded
    @POST("handler/IsAndroid.ashx")
    Observable<ResponseBody> getChargeHezhangList(
            @Field("flag") String flag,
            @Field("ID") int ID);


    //添加巡查记录，开始巡查
    @GET("updatePatrolrecord")
    Observable<ResponseBody> updatePatrolrecord(
            @Query("sysStaffId") int sysStaffId,
            @Query("patrolRecordBegintime") String patrolRecordBegintime,
            @Query("patrolRecordEndtime") String patrolRecordEndtime,
            @Query("patrolRecordGps") String patrolRecordGps,
            @Query("patrolRecordDetail") String patrolRecordDetail,
            @Query("sysRoadId") int sysRoadId,
            @Query("sysPatrolRecordId") int sysPatrolRecordId,
            @Query("patrolRecordPicture") String patrolRecordPicture,
            @Query("patrolRecordVideo") String patrolRecordVideo);


    /**
     * 通知模块
     */
    @FormUrlEncoded
    @POST("handler/IsAndroid.ashx")
    Observable<ResponseBody> getPCNoticeList(
            @Field("flag") String flag,
            @Field("keyword") String keyword);


    /**
     * 首页模块
     */
    //所有用户
    @FormUrlEncoded
    @POST("handler/IsAndroid.ashx")
    Observable<ResponseBody> getAllUsers(
            @Field("flag") String flag,
            @Field("keyword") String keyword);

    // 水库水位
    @FormUrlEncoded
    @POST("handler/IsAndroid.ashx")
    Observable<ResponseBody> getYSQ_SKSW(
            @Field("flag") String flag,
            @Field("keyword") String keyword);

    // 雨情
    @FormUrlEncoded
    @POST("handler/IsAndroid.ashx")
    Observable<ResponseBody> getYSQ_Rain(
            @Field("flag") String flag,
            @Field("keyword") String keyword);

    // 水情
    @FormUrlEncoded
    @POST("handler/IsAndroid.ashx")
    Observable<ResponseBody> getYSQ_River(
            @Field("flag") String flag,
            @Field("keyword") String keyword);


    //个人负责河段
    @FormUrlEncoded
    @POST("handler/IsAndroid.ashx")
    Observable<ResponseBody> getChargeRivers(
            @Field("flag") String flag,
            @Field("id") int id);


    // 待办任务
    @FormUrlEncoded
    @POST("handler/IsAndroid.ashx")
    Observable<ResponseBody> getHomeTodoList(
            @Field("flag") String flag,
            @Field("id") int userId);


    //我的任务
    @FormUrlEncoded
    @POST("handler/IsAndroid.ashx")
    Observable<ResponseBody> getTaskList(
            @Field("flag") String flag,
            @Field("ID") int ID,
            @Field("type") String type,
            @Field("fzren") String fzren,
            @Field("keyword") String keyword);




    //问题记录  上报
    @FormUrlEncoded
    @POST("handler/IsAndroid.ashx")
    Observable<ResponseBody> getProblemList(
            @Field("flag") String flag,
            @Field("ID") int ID,
            @Field("zt_type") String state,
            @Field("wt_type") String wt_type,
            @Field("fzren") String fzren,
            @Field("keyword") String keyword);

    //巡查关联的问题记录  上报
    @FormUrlEncoded
    @POST("handler/IsAndroid.ashx")
    Observable<ResponseBody> getPatrolProblemList(
            @Field("flag") String flag,
            @Field("ID") String ID,
            @Field("zt_type") String state,
            @Field("wt_type") String wt_type,
            @Field("keyword") String keyword);


    //    获取问题类型
    @GET("selectAllByProblemtype")
    Observable<ResponseBody> selectAllByProblemtype();

    //    获取问题类型指向人
    @GET("selectdefault")
    Observable<ResponseBody> selectdefault(
            @Query("probleDetail") String probleDetail);

    /**
     * 消息模块
     */
    //消息列表
    @FormUrlEncoded
    @POST("handler/IsAndroid.ashx")
    Observable<ResponseBody> getMessageList(
            @Field("flag") String flag,
            @Field("id") int userId);


    //消息列表点击详情  以及模块分类点击后详情
//   上报、任务
    @FormUrlEncoded
    @POST("handler/IsAndroid.ashx")
    Observable<ResponseBody> getItemDetail(
            @Field("flag") String flag,
            @Field("ID") String ID,
            @Field("type") String type);


    //更新消息已读未读状态
    @FormUrlEncoded
    @POST("handler/IsAndroid.ashx")
    Observable<ResponseBody> updateMessageState(
            @Field("flag") String flag,
            @Field("ID") String msgId);


    //删除单条消息
    @FormUrlEncoded
    @POST("handler/IsAndroid.ashx")
    Observable<ResponseBody> deleteSingleMessage(
            @Field("flag") String flag,
            @Field("ID") String msgId);


    /**
     * 个人中心
     */
    //修改个人信息
    @FormUrlEncoded
    @POST("handler/IsAndroid.ashx")
    Observable<ResponseBody> editPersonelInfo(
            @Field("flag") String flag,
            @Field("ID") int ID,
            @Field("uphone") String uphone);


    //修改密码
    @FormUrlEncoded
    @POST("handler/IsAndroid.ashx")
    Observable<ResponseBody> editPassword(
            @Field("flag") String flag,
            @Field("ID") int ID,
            @Field("OPassword") String Oldstaff_password,
            @Field("NPassword") String Newstaff_password);



    //版本更新
    @FormUrlEncoded
    @POST("handler/IsAndroid.ashx")
    Observable<ResponseBody> versionUpdate(
            @Field("flag") String flag);

























    //忘记密码
    @GET("handler/app.ashx/")
    Observable<ResponseBody> forgetPass(
            @Query("flag") String flag,
            @Query("staff_name") String staff_name,
            @Query("condition") String condition,
            @Query("Verification") String Verification,
            @Query("staff_password") String staff_password);






    //登录用户根据权限返回上级领导
    @GET("handler/app.ashx/")
    Observable<ResponseBody> getSuperiorPerson(
            @Query("flag") String flag,
            @Query("sys_staff_id") String sys_staff_id);


    //支队科研所 返回所有总路长
    @GET("handler/app.ashx/")
    Observable<ResponseBody> getAllZongluzhang(
            @Query("flag") String flag);

    //总路长返回管辖所有人员（一级路长和二级路长）
    @GET("handler/app.ashx/")
    Observable<ResponseBody> getYiErJiLuzhang(
            @Query("flag") String flag,
            @Query("section_name") String section_name);




    /**
     * 主页
     */

    //整改详情
    @GET("handler/app.ashx/")
    Observable<ResponseBody> getSuperviseDetail(
            @Query("flag") String flag,
            @Query("ID") String taskId);



    /**
     * 支队科研所
     */
    //问题列表
    @GET("handler/app.ashx/")
    Observable<ResponseBody> getZDKYSProList(
            @Query("flag") String flag,
            @Query("ID") String sys_patrol_record_id,
            @Query("problem_task") String problem_grade,
            @Query("problem_descibe") String problem_descibe);


    //支队科研所 将问题转化成任务状态 否改为是  添加一条新的任务
    @GET("handler/app.ashx/")
    Observable<ResponseBody> zdkysUpdateProTask(
            @Query("flag") String flag,
            @Query("sys_problem_id") String sys_patrol_record_id,
            @Query("launch_staff_id") String launch_staff_id,
            @Query("task_createtime") String task_createtime,
            @Query("task_expecttime") String task_expecttime,
            @Query("task_detail") String task_detail);





}
