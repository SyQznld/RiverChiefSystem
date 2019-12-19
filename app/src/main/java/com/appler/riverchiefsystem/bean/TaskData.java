package com.appler.riverchiefsystem.bean;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class TaskData implements Parcelable {


    /**
     * code : 0
     * msg : success
     * data : [{"id":"49","question":"垃圾分类","questionxq":"","rivername":"沙河支流","address":"3434","state":"待解决","states":"0","updatetime":"2019/9/30 9:41:39","picture":"uploadfiles/Picture/2019年09月30日 09时41分35秒/15575466489182036.jpg","fuzeren":"83","gid":"34","leixing":"派发","fasongren":"76","video":"uploadfiles/Video/2019年09月30日 09时41分37秒/1.mp4","fs_name":"卢向升"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    protected TaskData(Parcel in) {
        code = in.readInt();
        msg = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(msg);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TaskData> CREATOR = new Creator<TaskData>() {
        @Override
        public TaskData createFromParcel(Parcel in) {
            return new TaskData(in);
        }

        @Override
        public TaskData[] newArray(int size) {
            return new TaskData[size];
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

    public static class DataBean implements Parcelable{
        /**
         * id : 49
         * question : 垃圾分类
         * questionxq :
         * rivername : 沙河支流
         * address : 3434
         * state : 待解决
         * states : 0
         * updatetime : 2019/9/30 9:41:39
         * picture : uploadfiles/Picture/2019年09月30日 09时41分35秒/15575466489182036.jpg
         * fuzeren : 83
         * gid : 34
         * leixing : 派发
         * fasongren : 76
         * video : uploadfiles/Video/2019年09月30日 09时41分37秒/1.mp4
         * fs_name : 卢向升
         */

        private String id;
        private String question;
        private String questionxq;
        private String rivername;
        private String address;
        private String state;
        private String states;
        private String updatetime;
        private String picture;
        private String fuzeren;
        private String gid;
        private String leixing;
        private String fasongren;
        private String video;
        private String fs_name;

        protected DataBean(Parcel in) {
            id = in.readString();
            question = in.readString();
            questionxq = in.readString();
            rivername = in.readString();
            address = in.readString();
            state = in.readString();
            states = in.readString();
            updatetime = in.readString();
            picture = in.readString();
            fuzeren = in.readString();
            gid = in.readString();
            leixing = in.readString();
            fasongren = in.readString();
            video = in.readString();
            fs_name = in.readString();
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

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getQuestionxq() {
            return questionxq;
        }

        public void setQuestionxq(String questionxq) {
            this.questionxq = questionxq;
        }

        public String getRivername() {
            return rivername;
        }

        public void setRivername(String rivername) {
            this.rivername = rivername;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
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

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getFuzeren() {
            return fuzeren;
        }

        public void setFuzeren(String fuzeren) {
            this.fuzeren = fuzeren;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getLeixing() {
            return leixing;
        }

        public void setLeixing(String leixing) {
            this.leixing = leixing;
        }

        public String getFasongren() {
            return fasongren;
        }

        public void setFasongren(String fasongren) {
            this.fasongren = fasongren;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getFs_name() {
            return fs_name;
        }

        public void setFs_name(String fs_name) {
            this.fs_name = fs_name;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(question);
            dest.writeString(questionxq);
            dest.writeString(rivername);
            dest.writeString(address);
            dest.writeString(state);
            dest.writeString(states);
            dest.writeString(updatetime);
            dest.writeString(picture);
            dest.writeString(fuzeren);
            dest.writeString(gid);
            dest.writeString(leixing);
            dest.writeString(fasongren);
            dest.writeString(video);
            dest.writeString(fs_name);
        }
    }
}
