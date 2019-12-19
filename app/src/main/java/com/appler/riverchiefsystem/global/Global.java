package com.appler.riverchiefsystem.global;


public class Global {


    public static final String APPNAME = "河流巡查系统";
    public static final String APKNAME = APPNAME + ".apk";

    public static final String ROLENAME_CUN_HEZHANG = "村河长";
    public static final String ROLENAME_XIANG_HEZHANG = "乡河长";
    public static final String ROLENAME_ZHEN_HEZHANG = "镇河长";
    public static final String ROLENAME_QU_HEZHANG = "区河长";
    public static final String ROLENAME_XIAN_HEZHANG = "县河长";
    public static final String ROLENAME_SHI_HEZHANG = "市河长";


    public static final String MESSAGE_TYPE_SHANGBAO = "上报";
    public static final String MESSAGE_TYPE_TASK = "任务";

    public static final String WEATHER_APPKEY = "612d9c53c4ddf4a76746270eab7ccf8a";


    //动画加载
    public static final int LOADING_DOING = 1000;
    public static final int LOADING_SUCCESS = 1001;
    public static final int LOADING_FAIL = 1002;

    public static final int PATROL_PHOTO_CODE = 900;
    public static final int TASK_EDIT_CODE = 901;
    public static final int PROBLEM_EDIT_CODE = 902;


    //http://119.29.231.80:5532/handler/IsAndroid.ashx     公司服务器
//    public static final String URL = "http://192.168.1.15:8003/";      //  ZL
    public static final String URL = "http://119.29.231.80:5532/";      //  服务器
    public static final String Upload_Url = URL + "handler/IsAndroid.ashx/";   //请求接口
    public static final String UPLOAD_FILEPATH = "uploadfiles";   //后台保存图片路径
    public static final String NOTICE_Url = URL + "lib/Map/hezhangzhi/News/Detail.html?id=";   //通知公告模块 消息拼接路径





















    //任务
    public static final int ADDTASK_FILECHOOSE_CODE = 2001;    //添加任务   选择附件
    public static final int ADDTASK_RECEIVE_LZ = 2002;         //添加任务   选择接受任务路长
    public static final int ADDTASK_ZDKYS_RECEIVE_LZ = 2006;         //支队科研所添加任务   选择接受任务路长
    public static final int TASKZG_FILECHOOSE_CODE = 2003;    //任务整改    选择附件
    public static final int TASKZG_VERIFIER_CODE = 2004;      //任务整改    选择任务整改方案审核人
    public static final int ADDPROBLEM_ACCEPT_CODE = 2005;      //添加问题   选择问题接收人


    //每个大队部门名称
    public static final String SECTION_ONE = "一大队";           //一大队
    public static final String SECTION_TWO = "二大队";         //二大队
    public static final String SECTION_THREE = "三大队";       //三大队
    public static final String SECTION_FOUR = "四大队";        //四大队
    public static final String SECTION_FIVE = "五大队";        //五大队
    public static final String SECTION_SIX = "六大队";          //六大队
    public static final String SECTION_GQJGXF = "港区交管巡防大队";      // 港区交管巡防大队

    public static final String ZDKYS = "支队科研所";      // 港区交管巡防大队
    public static final String BDD = "八大队";      // 八大队
    public static final String JTK = "交通科";      // 交通科

    //每个大队中心点坐标位置
    public static final String URBAN_CENTER_ONE = "113.67241 34.77596";           //一大队
    public static final String URBAN_CENTER_TWO = "113.565424 34.751369";         //二大队
    public static final String URBAN_CENTER_THREE = "113.655088 34.697010";       //三大队
    public static final String URBAN_CENTER_FOUR = "113.784929 34.693019";        //四大队
    public static final String URBAN_CENTER_FIVE = "113.654211 34.858047";        //五大队
    public static final String URBAN_CENTER_SIX = "113.812055 34.783525";          //六大队
    public static final String URBAN_CENTER_GQJGXF = "113.918490 34.531564";      // 港区交管巡防大队


    //判断接口返回值
    public static final String PC_BACK_HTTP500 = "HTTP 500 Internal Server Error";      // HTTP 500 Internal Server Error
    public static final String PC_BACK_TIMEOUT = "time out";      // 连接超时
    public static final String PC_BACK_TIMEOUT_ = "timeout";      // 连接超时
    public static final String PC_BACK_LENGTHTOOLONG_ = "expected 6533 bytes but received 8192";      // 字节太长
    public static final String PC_BACK_LENGTHTOOLONG = "expected 2069 bytes but received 8192";      // 字节太长
    public static final String FILE_TOO_LONG = "bytes but received";      // 字节太长
    public static final String TOAST_HTTP500 = "服务器连接错误，请稍后再试";      //  吐司提示  HTTP 500 Internal Server Error
    public static final String TOAST_TIMEOUT = "连接超时，请检查网络连接";      //  吐司提示  超时
    public static final String TOAST_LENGTHTOOLONG = "文件太大，请稍后再试或删除太大文件";      //  吐司提示  字节太长


    public static int buttonPosition = -1; //下部导航栏标志位


}
