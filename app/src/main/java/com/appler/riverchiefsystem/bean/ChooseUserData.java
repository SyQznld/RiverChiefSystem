package com.appler.riverchiefsystem.bean;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ChooseUserData implements Parcelable {

    public static final Creator<ChooseUserData> CREATOR = new Creator<ChooseUserData>() {
        @Override
        public ChooseUserData createFromParcel(Parcel in) {
            return new ChooseUserData(in);
        }

        @Override
        public ChooseUserData[] newArray(int size) {
            return new ChooseUserData[size];
        }
    };
    /**
     * code : 0
     * msg :
     * data : [{"sys_staff_id":"1","staff_name":"管理员","staff_hierarchy":"分管路长","staff_photo":"img/index1/4.jpg","section_name":"一大队"},{"sys_staff_id":"2","staff_name":"吴永平","staff_hierarchy":"分管路长","staff_photo":"img/index1/4.jpg","section_name":"一大队"},{"sys_staff_id":"3","staff_name":"王勇","staff_hierarchy":"总路长","staff_photo":"img/index1/4.jpg","section_name":"一大队"},{"sys_staff_id":"4","staff_name":"乔宏雁","staff_hierarchy":"一级路长","staff_photo":"img/index1/4.jpg","section_name":"一大队"},{"sys_staff_id":"5","staff_name":"席磊","staff_hierarchy":"一级路长","staff_photo":"img/index1/4.jpg","section_name":"一大队"},{"sys_staff_id":"6","staff_name":"耿闯","staff_hierarchy":"一级路长","staff_photo":"img/index1/4.jpg","section_name":"一大队"},{"sys_staff_id":"7","staff_name":"张福志","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"一大队"},{"sys_staff_id":"8","staff_name":"程胜","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"一大队"},{"sys_staff_id":"9","staff_name":"王盈","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"一大队"},{"sys_staff_id":"10","staff_name":"康作强","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"一大队"},{"sys_staff_id":"17","staff_name":"张卫东","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"一大队"},{"sys_staff_id":"18","staff_name":"吕恒","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"一大队"},{"sys_staff_id":"50","staff_name":"王亚鹏","staff_hierarchy":"分管路长","staff_photo":"img/index1/4.jpg","section_name":"二大队"},{"sys_staff_id":"51","staff_name":"丁海燕","staff_hierarchy":"总路长","staff_photo":"img/index1/4.jpg","section_name":"二大队"},{"sys_staff_id":"52","staff_name":"陈双军","staff_hierarchy":"一级路长","staff_photo":"img/index1/4.jpg","section_name":"二大队"},{"sys_staff_id":"53","staff_name":"李震","staff_hierarchy":"一级路长","staff_photo":"img/index1/4.jpg","section_name":"二大队"},{"sys_staff_id":"54","staff_name":"郭荣显","staff_hierarchy":"一级路长","staff_photo":"img/index1/4.jpg","section_name":"二大队"},{"sys_staff_id":"55","staff_name":"张保健","staff_hierarchy":"一级路长","staff_photo":"img/index1/4.jpg","section_name":"二大队"},{"sys_staff_id":"56","staff_name":"潘亚文","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"二大队"},{"sys_staff_id":"57","staff_name":"李海峰","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"二大队"},{"sys_staff_id":"58","staff_name":"李铁军","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"二大队"},{"sys_staff_id":"59","staff_name":"詹迪","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"二大队"},{"sys_staff_id":"60","staff_name":"王刚","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"二大队"},{"sys_staff_id":"61","staff_name":"许建军","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"二大队"},{"sys_staff_id":"62","staff_name":"康朋辉","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"二大队"},{"sys_staff_id":"63","staff_name":"孔国东","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"二大队"},{"sys_staff_id":"64","staff_name":"邓进超","staff_hierarchy":"分管路长","staff_photo":"img/index1/4.jpg","section_name":"四大队"},{"sys_staff_id":"65","staff_name":"朱子民","staff_hierarchy":"总路长","staff_photo":"img/index1/4.jpg","section_name":"四大队"},{"sys_staff_id":"66","staff_name":"赵清生","staff_hierarchy":"一级路长","staff_photo":"img/index1/4.jpg","section_name":"四大队"},{"sys_staff_id":"67","staff_name":"肖博","staff_hierarchy":"一级路长","staff_photo":"img/index1/4.jpg","section_name":"四大队"},{"sys_staff_id":"68","staff_name":"王韩","staff_hierarchy":"一级路长","staff_photo":"img/index1/4.jpg","section_name":"四大队"},{"sys_staff_id":"69","staff_name":"蒋乐观","staff_hierarchy":"一级路长","staff_photo":"img/index1/4.jpg","section_name":"四大队"},{"sys_staff_id":"70","staff_name":"张政","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"四大队"},{"sys_staff_id":"71","staff_name":"刘然","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"四大队"},{"sys_staff_id":"72","staff_name":"蒋建伟","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"四大队"},{"sys_staff_id":"73","staff_name":"刘志峰","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"四大队"},{"sys_staff_id":"74","staff_name":"李予峰","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"四大队"},{"sys_staff_id":"75","staff_name":"孙春生","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"四大队"},{"sys_staff_id":"76","staff_name":"孙华东","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"四大队"},{"sys_staff_id":"77","staff_name":"史根宽","staff_hierarchy":"分管路长","staff_photo":"img/index1/4.jpg","section_name":"五大队"},{"sys_staff_id":"78","staff_name":"海洲","staff_hierarchy":"总路长","staff_photo":"img/index1/4.jpg","section_name":"五大队"},{"sys_staff_id":"79","staff_name":"董文","staff_hierarchy":"一级路长","staff_photo":"img/index1/4.jpg","section_name":"五大队"},{"sys_staff_id":"80","staff_name":"白桦","staff_hierarchy":"一级路长","staff_photo":"img/index1/4.jpg","section_name":"五大队"},{"sys_staff_id":"81","staff_name":"孙建华","staff_hierarchy":"一级路长","staff_photo":"img/index1/4.jpg","section_name":"五大队"},{"sys_staff_id":"82","staff_name":"李耀勇","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"五大队"},{"sys_staff_id":"83","staff_name":"刘长永","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"五大队"},{"sys_staff_id":"84","staff_name":"安红心","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"五大队"},{"sys_staff_id":"85","staff_name":"杨勇","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"五大队"},{"sys_staff_id":"86","staff_name":"于天水","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"五大队"},{"sys_staff_id":"87","staff_name":"史卫强","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"五大队"},{"sys_staff_id":"88","staff_name":"张爱华","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"五大队"},{"sys_staff_id":"89","staff_name":"陈志民","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"五大队"},{"sys_staff_id":"91","staff_name":"张松柏","staff_hierarchy":"分管路长","staff_photo":"img/index1/4.jpg","section_name":"六大队"},{"sys_staff_id":"92","staff_name":"牛万宝","staff_hierarchy":"分管路长","staff_photo":"img/index1/4.jpg","section_name":"六大队"},{"sys_staff_id":"93","staff_name":"赵勇","staff_hierarchy":"总路长","staff_photo":"img/index1/4.jpg","section_name":"六大队"},{"sys_staff_id":"94","staff_name":"王宏伟","staff_hierarchy":"一级路长","staff_photo":"img/index1/4.jpg","section_name":"六大队"},{"sys_staff_id":"95","staff_name":"贾铁军","staff_hierarchy":"一级路长","staff_photo":"img/index1/4.jpg","section_name":"六大队"},{"sys_staff_id":"96","staff_name":"王兵","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"六大队"},{"sys_staff_id":"97","staff_name":"李长斌","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"六大队"},{"sys_staff_id":"98","staff_name":"孙建新","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"六大队"},{"sys_staff_id":"99","staff_name":"汪勇","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"二大队"},{"sys_staff_id":"100","staff_name":"王永健","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"二大队"},{"sys_staff_id":"101","staff_name":"王发战","staff_hierarchy":"一级路长","staff_photo":"img/index1/4.jpg","section_name":"六大队"},{"sys_staff_id":"102","staff_name":"董红亮","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"六大队"},{"sys_staff_id":"103","staff_name":"田利强","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"六大队"},{"sys_staff_id":"105","staff_name":"李保伟","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"二大队"},{"sys_staff_id":"106","staff_name":"杨明昊","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"二大队"},{"sys_staff_id":"107","staff_name":"黄晓伟","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"二大队"},{"sys_staff_id":"108","staff_name":"刘跃辉","staff_hierarchy":"二级领导","staff_photo":"img/index1/4.jpg","section_name":"二大队"},{"sys_staff_id":"109","staff_name":"章冰","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"六大队"},{"sys_staff_id":"110","staff_name":"郝国平","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"六大队"},{"sys_staff_id":"111","staff_name":"马建峰","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"六大队"},{"sys_staff_id":"112","staff_name":"李幸坤","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"六大队"},{"sys_staff_id":"113","staff_name":"王一","staff_hierarchy":"分管路长","staff_photo":"img/index1/4.jpg","section_name":"三大队"},{"sys_staff_id":"114","staff_name":"王二","staff_hierarchy":"总路长","staff_photo":"img/index1/4.jpg","section_name":"三大队"},{"sys_staff_id":"115","staff_name":"王三","staff_hierarchy":"一级路长","staff_photo":"img/index1/4.jpg","section_name":"三大队"},{"sys_staff_id":"116","staff_name":"王四","staff_hierarchy":"二级路长","staff_photo":"img/index1/4.jpg","section_name":"三大队"},{"sys_staff_id":"118","staff_name":"11","staff_hierarchy":"11","staff_photo":"11","section_name":"十大队"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    protected ChooseUserData(Parcel in) {
        code = in.readInt();
        msg = in.readString();
        data = in.createTypedArrayList(DataBean.CREATOR);
    }

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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(code);
        parcel.writeString(msg);
        parcel.writeTypedList(data);
    }

    @Override
    public String toString() {
        return "ChooseUserData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean implements Parcelable {
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
        /**
         * sys_staff_id : 1
         * staff_name : 管理员
         * staff_hierarchy : 分管路长
         * staff_photo : img/index1/4.jpg
         * section_name : 一大队
         */

        private String sys_staff_id;
        private String staff_name;
        private String staff_hierarchy;
        private String staff_photo;
        private String section_name;

        protected DataBean(Parcel in) {
            sys_staff_id = in.readString();
            staff_name = in.readString();
            staff_hierarchy = in.readString();
            staff_photo = in.readString();
            section_name = in.readString();
        }

        public String getSys_staff_id() {
            return sys_staff_id;
        }

        public void setSys_staff_id(String sys_staff_id) {
            this.sys_staff_id = sys_staff_id;
        }

        public String getStaff_name() {
            return staff_name;
        }

        public void setStaff_name(String staff_name) {
            this.staff_name = staff_name;
        }

        public String getStaff_hierarchy() {
            return staff_hierarchy;
        }

        public void setStaff_hierarchy(String staff_hierarchy) {
            this.staff_hierarchy = staff_hierarchy;
        }

        public String getStaff_photo() {
            return staff_photo;
        }

        public void setStaff_photo(String staff_photo) {
            this.staff_photo = staff_photo;
        }

        public String getSection_name() {
            return section_name;
        }

        public void setSection_name(String section_name) {
            this.section_name = section_name;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "sys_staff_id='" + sys_staff_id + '\'' +
                    ", staff_name='" + staff_name + '\'' +
                    ", staff_hierarchy='" + staff_hierarchy + '\'' +
                    ", staff_photo='" + staff_photo + '\'' +
                    ", section_name='" + section_name + '\'' +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(sys_staff_id);
            parcel.writeString(staff_name);
            parcel.writeString(staff_hierarchy);
            parcel.writeString(staff_photo);
            parcel.writeString(section_name);
        }
    }
}
