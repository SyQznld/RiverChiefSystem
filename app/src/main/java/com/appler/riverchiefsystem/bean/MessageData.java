package com.appler.riverchiefsystem.bean;


import java.util.List;

public class MessageData {


    /**
     * code : 0
     * msg : success
     * data : [{"id":"384","pid":"122","message_type":"问题","message_name":"更多","message_state":"是","sys_staff_id":"70","create_time":"2019年05月20日 10时25分49秒","isdelete":"0"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 384
         * pid : 122
         * message_type : 问题
         * message_name : 更多
         * message_state : 是
         * sys_staff_id : 70
         * create_time : 2019年05月20日 10时25分49秒
         * isdelete : 0
         */

        private String id;
        private String pid;
        private String message_type;
        private String message_name;
        private String message_state;
        private String sys_staff_id;
        private String create_time;
        private String isdelete;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getMessage_type() {
            return message_type;
        }

        public void setMessage_type(String message_type) {
            this.message_type = message_type;
        }

        public String getMessage_name() {
            return message_name;
        }

        public void setMessage_name(String message_name) {
            this.message_name = message_name;
        }

        public String getMessage_state() {
            return message_state;
        }

        public void setMessage_state(String message_state) {
            this.message_state = message_state;
        }

        public String getSys_staff_id() {
            return sys_staff_id;
        }

        public void setSys_staff_id(String sys_staff_id) {
            this.sys_staff_id = sys_staff_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getIsdelete() {
            return isdelete;
        }

        public void setIsdelete(String isdelete) {
            this.isdelete = isdelete;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", pid='" + pid + '\'' +
                    ", message_type='" + message_type + '\'' +
                    ", message_name='" + message_name + '\'' +
                    ", message_state='" + message_state + '\'' +
                    ", sys_staff_id='" + sys_staff_id + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", isdelete='" + isdelete + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MessageData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
