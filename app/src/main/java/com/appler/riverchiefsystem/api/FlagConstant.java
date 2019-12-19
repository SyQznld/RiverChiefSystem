package com.appler.riverchiefsystem.api;


/**
 * 河长制 接口请求flag
 */

public class FlagConstant {

    public static final String FLAG_LOGIN = "Login";             //登录

    public static final String FLAG_FORGET_PASSWORD = "forgetPassword";             //忘记密码

    public static final String FLAG_SUPERIOR_PERSON = "getlingdao";    //根据权限返回上级领导 superior
    public static final String FLAG_ALL_ZONGLUZHNAG_PERSON = "getzongluzhang";    //支队科研所 返回全部总路长
    public static final String FLAG_YIERJI_LUZHANG_PERSON = "getlzsx";    //总路长返回所有下属 一二级路长


    public static final String FLAG_PATROL_ADDOREDIT = "patrol_record_add";    //增加或编辑巡查记录

    /**
     * 首页
     */
    public static final String FLAG_CHARGE_PERSON = "hezhang_list";    //根据权限返回下属河长
    public static final String FLAG_HOME_TODOTASK_LIST = "gettaskthree";  //首页待办任务列表  优先尚未开始其次进行中
    public static final String FLAG_PROBLEM_LIST = "shangbao";  //问题记录
    public static final String FLAG_PATROL_PROBLEM_LIST = "getShangBao";  //巡查关联的问题记录
    public static final String FLAG_PROBLEM_ADD = "add_shangbao";  //添加问题
    public static final String FLAG_SYQ_SKSW_LIST = "getshuiku";  //水库水位
    public static final String FLAG_SYQ_RIVERS_LIST = "getriver";  //水情
    public static final String FLAG_SYQ_RAIN_LIST = "getrain";  //雨情
    public static final String FLAG_CHARGE_RIVERS_LIST = "getHeDao";  //个人负责的河湖列表
    public static final String FLAG_CONTACTS_LIST = "getsys_user";  //通讯录
    public static final String FLAG_TASK_LIST = "renwu";  //任务列表
    public static final String FLAG_TASK_EDIT = "edit_renwu";  //编辑任务
    public static final String FLAG_PATROL_LIST = "patrol_record";  //巡查记录
    public static final String TASKTYPE_NOBEGIN = "待解决";
    public static final String TASKTYPE_BNJJ = "不能解决";
    public static final String TASKTYPE_UNFINISHED = "正在解决";
    public static final String TASKTYPE_FINISH = "已解决";






    /**
     * 消息
     * */
    public static final String FLAG_NOTIFICATION_LIST = "GetIndexPage";  //通知公告列表
    public static final String FLAG_MESSAGE_LIST = "getmessage";  //消息 列表
    public static final String FLAG_MESSAGEITEM_DETAIL = "xiangqing";  //列表消息 点击后详情
    public static final String FLAG_DELETE_SINGLE_MESSAGE = "DeleteMessage";  //删除单条消息
    public static final String FLAG_UPDATE_MESSAGE_STATE = "UpMessage";  //更显消息已读未读状态




    /**
     * 个人
     */
    public static final String FLAG_EDIT_PERSONEL_INFO = "ChangeInformation";  //编辑用户信息
    public static final String FLAG_EDIT_PASSWORD = "ChangePassword";  //修改用户密码
    public static final String FLAG_VERSION_UPDATE = "Version";  //版本更新


}
