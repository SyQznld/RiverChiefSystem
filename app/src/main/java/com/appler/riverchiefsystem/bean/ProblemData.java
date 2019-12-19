package com.appler.riverchiefsystem.bean;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ProblemData implements Parcelable {


    /**
     * code : 0
     * msg : success
     * data : [{"id":"28","上报理由":"有无漂浮物","事件类型":"河长上报","上报人姓名":"杨冲","河道名称":"沙河周庄村","事件状态":"正在处理","phone":"15738690162","地址":"周庄村","问题描述":"漂浮物上报测试0930222","picture":"uploadfiles/Picture/河长上报20190930093339 20190930093340.jpg","states":"1","updatetime":"2019/09/30 14:12:18.000","fuzeren":"76","备注":"漂浮物0930222","处理方式":"上报","sb_id":"83","video":"","jb_id":"","gid":"29","river_id":"16","patrol_record_id":"173","经度":"","纬度":"","fz_name":"卢向升"},{"id":"27","上报理由":"水质是否清洁","事件类型":"河长上报","上报人姓名":"杨冲","河道名称":"沙河周庄村","事件状态":"正在处理","phone":"15738690162","地址":"周庄村","问题描述":"上报测试0930111","picture":"uploadfiles/Picture/河长上报20190930092926 20190930092926.jpg","states":"1","updatetime":"2019/09/30 09:29:28.000","fuzeren":"76","备注":"备注测试0930111","处理方式":"交办","sb_id":"83","video":"","jb_id":"141","gid":"","river_id":"16","patrol_record_id":"173","经度":"","纬度":"","fz_name":"卢向升"},{"id":"26","上报理由":"有无排污口","事件类型":"河长上报","上报人姓名":"杨冲","河道名称":"沙河周庄村","事件状态":"待处理","phone":"15738690162","地址":"周庄村","问题描述":"上报测试0929","picture":"uploadfiles/Picture/河长上报20190929150010 20190929150006.jpg","states":"0","updatetime":"2019/09/29 15:00:11.000","fuzeren":"76","备注":"备注测试0929","处理方式":"","sb_id":"83","video":"","jb_id":"","gid":"","river_id":"16","patrol_record_id":"172","经度":"","纬度":"","fz_name":"卢向升"},{"id":"25","上报理由":"公示牌是否设置完整","事件类型":"河长上报","上报人姓名":"杨冲","河道名称":"沙河周庄村","事件状态":"待处理","phone":"15738690162","地址":"周庄村","问题描述":"测试0928111","picture":"uploadfiles/Picture/河长上报20190928104731 20190928102700.jpg","states":"0","updatetime":"2019/09/28 10:47:30.000","fuzeren":"76","备注":"备注测试","处理方式":"","sb_id":"83","video":"","jb_id":"","gid":"","river_id":"16","patrol_record_id":"170","经度":"","纬度":"","fz_name":"卢向升"},{"id":"24","上报理由":"公示牌是否设置完整","事件类型":"河长上报","上报人姓名":"杨冲","河道名称":"沙河周庄村","事件状态":"待处理","phone":"15738690162","地址":"周庄村","问题描述":"测试0928111","picture":"uploadfiles/Picture/河长上报20190928104520 20190928102700.jpg","states":"0","updatetime":"2019/09/28 10:45:12.000","fuzeren":"76","备注":"备注测试","处理方式":"","sb_id":"83","video":"","jb_id":"","gid":"","river_id":"16","patrol_record_id":"170","经度":"","纬度":"","fz_name":"卢向升"},{"id":"23","上报理由":"公示牌是否设置完整","事件类型":"河长上报","上报人姓名":"杨冲","河道名称":"沙河周庄村","事件状态":"待处理","phone":"15738690162","地址":"周庄村","问题描述":"测试0928111","picture":"uploadfiles/Picture/河长上报20190928104346 20190928102700.jpg","states":"0","updatetime":"2019/09/28 10:43:35.000","fuzeren":"76","备注":"备注测试","处理方式":"","sb_id":"83","video":"","jb_id":"","gid":"","river_id":"16","patrol_record_id":"170","经度":"","纬度":"","fz_name":"卢向升"},{"id":"21","上报理由":"公示牌是否设置完整","事件类型":"河长上报","上报人姓名":"杨冲","河道名称":"沙河周庄村","事件状态":"待处理","phone":"15738690162","地址":"周庄村","问题描述":"测试0928111","picture":"uploadfiles/Picture/河长上报20190928104012 20190928102700.jpg","states":"0","updatetime":"2019/09/28 10:39:41.000","fuzeren":"76","备注":"备注测试","处理方式":"","sb_id":"83","video":"","jb_id":"","gid":"","river_id":"16","patrol_record_id":"170","经度":"","纬度":"","fz_name":"卢向升"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    protected ProblemData(Parcel in) {
        code = in.readInt();
        msg = in.readString();
        data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Creator<ProblemData> CREATOR = new Creator<ProblemData>() {
        @Override
        public ProblemData createFromParcel(Parcel in) {
            return new ProblemData(in);
        }

        @Override
        public ProblemData[] newArray(int size) {
            return new ProblemData[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeTypedList(data);
    }

    public static class DataBean implements Parcelable{
        /**
         * id : 28
         * 上报理由 : 有无漂浮物
         * 事件类型 : 河长上报
         * 上报人姓名 : 杨冲
         * 河道名称 : 沙河周庄村
         * 事件状态 : 正在处理
         * phone : 15738690162
         * 地址 : 周庄村
         * 问题描述 : 漂浮物上报测试0930222
         * picture : uploadfiles/Picture/河长上报20190930093339 20190930093340.jpg
         * states : 1
         * updatetime : 2019/09/30 14:12:18.000
         * fuzeren : 76
         * 备注 : 漂浮物0930222
         * 处理方式 : 上报
         * sb_id : 83
         * video :
         * jb_id :
         * gid : 29
         * river_id : 16
         * patrol_record_id : 173
         * 经度 :
         * 纬度 :
         * fz_name : 卢向升
         */

        private String id;
        private String 上报理由;
        private String 事件类型;
        private String 上报人姓名;
        private String 河道名称;
        private String 事件状态;
        private String phone;
        private String 地址;
        private String 问题描述;
        private String picture;
        private String states;
        private String updatetime;
        private String fuzeren;
        private String 备注;
        private String 处理方式;
        private String sb_id;
        private String video;
        private String jb_id;
        private String gid;
        private String river_id;
        private String patrol_record_id;
        private String 经度;
        private String 纬度;
        private String fz_name;

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            id = in.readString();
            上报理由 = in.readString();
            事件类型 = in.readString();
            上报人姓名 = in.readString();
            河道名称 = in.readString();
            事件状态 = in.readString();
            phone = in.readString();
            地址 = in.readString();
            问题描述 = in.readString();
            picture = in.readString();
            states = in.readString();
            updatetime = in.readString();
            fuzeren = in.readString();
            备注 = in.readString();
            处理方式 = in.readString();
            sb_id = in.readString();
            video = in.readString();
            jb_id = in.readString();
            gid = in.readString();
            river_id = in.readString();
            patrol_record_id = in.readString();
            经度 = in.readString();
            纬度 = in.readString();
            fz_name = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String get上报理由() {
            return 上报理由;
        }

        public void set上报理由(String 上报理由) {
            this.上报理由 = 上报理由;
        }

        public String get事件类型() {
            return 事件类型;
        }

        public void set事件类型(String 事件类型) {
            this.事件类型 = 事件类型;
        }

        public String get上报人姓名() {
            return 上报人姓名;
        }

        public void set上报人姓名(String 上报人姓名) {
            this.上报人姓名 = 上报人姓名;
        }

        public String get河道名称() {
            return 河道名称;
        }

        public void set河道名称(String 河道名称) {
            this.河道名称 = 河道名称;
        }

        public String get事件状态() {
            return 事件状态;
        }

        public void set事件状态(String 事件状态) {
            this.事件状态 = 事件状态;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String get地址() {
            return 地址;
        }

        public void set地址(String 地址) {
            this.地址 = 地址;
        }

        public String get问题描述() {
            return 问题描述;
        }

        public void set问题描述(String 问题描述) {
            this.问题描述 = 问题描述;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getStates() {
            return states;
        }

        public void setStates(String states) {
            this.states = states;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getFuzeren() {
            return fuzeren;
        }

        public void setFuzeren(String fuzeren) {
            this.fuzeren = fuzeren;
        }

        public String get备注() {
            return 备注;
        }

        public void set备注(String 备注) {
            this.备注 = 备注;
        }

        public String get处理方式() {
            return 处理方式;
        }

        public void set处理方式(String 处理方式) {
            this.处理方式 = 处理方式;
        }

        public String getSb_id() {
            return sb_id;
        }

        public void setSb_id(String sb_id) {
            this.sb_id = sb_id;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getJb_id() {
            return jb_id;
        }

        public void setJb_id(String jb_id) {
            this.jb_id = jb_id;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getRiver_id() {
            return river_id;
        }

        public void setRiver_id(String river_id) {
            this.river_id = river_id;
        }

        public String getPatrol_record_id() {
            return patrol_record_id;
        }

        public void setPatrol_record_id(String patrol_record_id) {
            this.patrol_record_id = patrol_record_id;
        }

        public String get经度() {
            return 经度;
        }

        public void set经度(String 经度) {
            this.经度 = 经度;
        }

        public String get纬度() {
            return 纬度;
        }

        public void set纬度(String 纬度) {
            this.纬度 = 纬度;
        }

        public String getFz_name() {
            return fz_name;
        }

        public void setFz_name(String fz_name) {
            this.fz_name = fz_name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(上报理由);
            dest.writeString(事件类型);
            dest.writeString(上报人姓名);
            dest.writeString(河道名称);
            dest.writeString(事件状态);
            dest.writeString(phone);
            dest.writeString(地址);
            dest.writeString(问题描述);
            dest.writeString(picture);
            dest.writeString(states);
            dest.writeString(updatetime);
            dest.writeString(fuzeren);
            dest.writeString(备注);
            dest.writeString(处理方式);
            dest.writeString(sb_id);
            dest.writeString(video);
            dest.writeString(jb_id);
            dest.writeString(gid);
            dest.writeString(river_id);
            dest.writeString(patrol_record_id);
            dest.writeString(经度);
            dest.writeString(纬度);
            dest.writeString(fz_name);
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", 上报理由='" + 上报理由 + '\'' +
                    ", 事件类型='" + 事件类型 + '\'' +
                    ", 上报人姓名='" + 上报人姓名 + '\'' +
                    ", 河道名称='" + 河道名称 + '\'' +
                    ", 事件状态='" + 事件状态 + '\'' +
                    ", phone='" + phone + '\'' +
                    ", 地址='" + 地址 + '\'' +
                    ", 问题描述='" + 问题描述 + '\'' +
                    ", picture='" + picture + '\'' +
                    ", states='" + states + '\'' +
                    ", updatetime='" + updatetime + '\'' +
                    ", fuzeren='" + fuzeren + '\'' +
                    ", 备注='" + 备注 + '\'' +
                    ", 处理方式='" + 处理方式 + '\'' +
                    ", sb_id='" + sb_id + '\'' +
                    ", video='" + video + '\'' +
                    ", jb_id='" + jb_id + '\'' +
                    ", gid='" + gid + '\'' +
                    ", river_id='" + river_id + '\'' +
                    ", patrol_record_id='" + patrol_record_id + '\'' +
                    ", 经度='" + 经度 + '\'' +
                    ", 纬度='" + 纬度 + '\'' +
                    ", fz_name='" + fz_name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ProblemData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
