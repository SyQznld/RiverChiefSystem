package com.appler.riverchiefsystem.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 水雨情
 * 水库水位
 * */
public class SYQ_SKSWData implements Parcelable{


    /**
     * code : 0
     * msg : success
     * data : [{"gid":"1","ogr_geometry":"POINT (112.611 34.217)","ShapeType":"POINT","站码":"50604890","站名":"涧山口","政区":"汝州市","库水位":"272.47","蓄水量":"5.64","可用水量":"5.38","死库容":"0.26","汛限水位":"272.5","超汛限水位":"-0.030","旱限水位":"269.6","低旱限水位":"-2.870","历史最高水位":"274.3","超历史最高水位":"-1.85","历史最低水位":"","低历史最低水位":"","入库流量":"","出库流量":"2","附近雨量站":"涧山口","水势":"0","时间":"2019/6/27 8:W00:W00","时间s":"2019/6/27 8:W00:W00"},{"gid":"2","ogr_geometry":"POINT (113.309359018214 33.3829814724822)","ShapeType":"POINT","站码":"50606100","站名":"燕山","政区":"叶县","库水位":"103.57","蓄水量":"144.08","可用水量":"124.08","死库容":"20.W00","汛限水位":"106","超汛限水位":"-2.430","旱限水位":"","低旱限水位":"","历史最高水位":"106.89","超历史最高水位":"-2.77","历史最低水位":"","低历史最低水位":"","入库流量":"","出库流量":"15.6","附近雨量站":"叶县","水势":"0","时间":"2019/6/27 8:W00:W00","时间s":"2019/6/27 8:W00:W00"},{"gid":"3","ogr_geometry":"POINT (112.746729666733 33.7486849077984)","ShapeType":"POINT","站码":"50603000","站名":"昭平台","政区":"鲁山县","库水位":"167.85","蓄水量":"128.58","可用水量":"92.58","死库容":"36.W00","汛限水位":"170","超汛限水位":"-2.150","旱限水位":"161","低旱限水位":"-6.850","历史最高水位":"177.3","超历史最高水位":"-10.48","历史最低水位":"","低历史最低水位":"","入库流量":"","出库流量":"16","附近雨量站":"昭平台水库","水势":"0","时间":"2019/6/27 8:W00:W00","时间s":"2019/6/27 8:W00:W00"},{"gid":"4","ogr_geometry":"POINT (113.557291074513 33.3039637163515)","ShapeType":"POINT","站码":"50300320","站名":"田岗","政区":"舞钢市","库水位":"85.83","蓄水量":"10.65","可用水量":"4.35","死库容":"6.30","汛限水位":"86","超汛限水位":"-0.170","旱限水位":"85.5","低旱限水位":"-0.330","历史最高水位":"87.46","超历史最高水位":"-1.61","历史最低水位":"","低历史最低水位":"","入库流量":"","出库流量":"10","附近雨量站":"田岗","水势":"0","时间":"2019/6/27 8:W00:W00","时间s":"2019/6/27 8:W00:W00"},{"gid":"5","ogr_geometry":"POINT (113.160681680853 33.7346732668731)","ShapeType":"POINT","站码":"50603200","站名":"白龟山","政区":"湛河区","库水位":"99.16","蓄水量":"112.33","可用水量":"46.33","死库容":"66.W00","汛限水位":"101.5","超汛限水位":"-2.340","旱限水位":"99.5","低旱限水位":"0.340","历史最高水位":"106.21","超历史最高水位":"-5.4","历史最低水位":"","低历史最低水位":"","入库流量":"","出库流量":"5.2","附近雨量站":"白龟山","水势":"0","时间":"2019/6/27 8:W00:W00","时间s":"2019/6/27 8:W00:W00"},{"gid":"6","ogr_geometry":"POINT (113.529377305961 33.2721458257317)","ShapeType":"POINT","站码":"50300100","站名":"石漫滩","政区":"舞钢市","库水位":"103.73","蓄水量":"44.4","可用水量":"38.80","死库容":"5.60","汛限水位":"106.5","超汛限水位":"-2.770","旱限水位":"96","低旱限水位":"-7.730","历史最高水位":"110.11","超历史最高水位":"-7.42","历史最低水位":"","低历史最低水位":"","入库流量":"","出库流量":"7.71","附近雨量站":"石漫滩水库","水势":"0","时间":"2019/6/27 8:W00:W00","时间s":"2019/6/27 8:W00:W00"},{"gid":"7","ogr_geometry":"POINT (113.067398607574 33.4770503745186)","ShapeType":"POINT","站码":"50605800","站名":"孤石滩","政区":"叶县","库水位":"149.55","蓄水量":"39.75","可用水量":"38.78","死库容":"0.97","汛限水位":"152","超汛限水位":"-2.450","旱限水位":"145.5","低旱限水位":"-4.050","历史最高水位":"158.72","超历史最高水位":"-8.5","历史最低水位":"","低历史最低水位":"","入库流量":"","出库流量":"8.29","附近雨量站":"孤石滩","水势":"0","时间":"2019/6/27 8:W00:W00","时间s":"2019/6/27 8:W00:W00"},{"gid":"8","ogr_geometry":"POINT (112.998673949471 33.626798568396)","ShapeType":"POINT","站码":"50604640","站名":"澎河","政区":"鲁山县","库水位":"144.72","蓄水量":"16.91","可用水量":"16.05","死库容":"0.86","汛限水位":"","超汛限水位":"","旱限水位":"142","低旱限水位":"-2.720","历史最高水位":"151.51","超历史最高水位":"-10.26","历史最低水位":"","低历史最低水位":"","入库流量":"","出库流量":"","附近雨量站":"澎河","水势":"0","时间":"2019/6/27 8:W00:W00","时间s":"2019/6/27 8:W00:W00"},{"gid":"9","ogr_geometry":"POINT (112.946714683195 33.8832290848959)","ShapeType":"POINT","站码":"50605560","站名":"河陈","政区":"宝丰县","库水位":"177.3","蓄水量":"3.47","可用水量":"3.25","死库容":"0.22","汛限水位":"179","超汛限水位":"-1.700","旱限水位":"","低旱限水位":"","历史最高水位":"180","超历史最高水位":"-2.85","历史最低水位":"","低历史最低水位":"","入库流量":"","出库流量":"","附近雨量站":"河陈","水势":"0","时间":"2019/6/27 8:W00:W00","时间s":"2019/6/27 8:W00:W00"},{"gid":"11","ogr_geometry":"POINT (112.823754990695 33.9929501197121)","ShapeType":"POINT","站码":"50605540","站名":"龙兴寺","政区":"宝丰县","库水位":"271.87","蓄水量":"8.49","可用水量":"7.59","死库容":"0.90","汛限水位":"282","超汛限水位":"-10.130","旱限水位":"264.8","低旱限水位":"-7.070","历史最高水位":"286.24","超历史最高水位":"-7.86","历史最低水位":"","低历史最低水位":"","入库流量":"","出库流量":"","附近雨量站":"龙兴寺","水势":"0","时间":"2019/6/27 8:W00:W00","时间s":"2019/6/27 8:W00:W00"},{"gid":"12","ogr_geometry":"POINT (112.611 34.217)","ShapeType":"POINT","站码":"50605330","站名":"藤口","政区":"汝州市","库水位":"343","蓄水量":"0.672","可用水量":"0.23","死库容":"0.44","汛限水位":"357","超汛限水位":"-14.000","旱限水位":"","低旱限水位":"","历史最高水位":"359.5","超历史最高水位":"-6.23","历史最低水位":"","低历史最低水位":"","入库流量":"","出库流量":"","附近雨量站":"藤口","水势":"0","时间":"2019/6/27 8:W00:W00","时间s":"2019/6/27 8:W00:W00"},{"gid":"13","ogr_geometry":"POINT (112.611 34.217)","ShapeType":"POINT","站码":"50605340","站名":"马庙","政区":"汝州市","库水位":"318.9","蓄水量":"3.45","可用水量":"2.62","死库容":"0.83","汛限水位":"328","超汛限水位":"-9.100","旱限水位":"318.6","低旱限水位":"-0.300","历史最高水位":"330","超历史最高水位":"-10.98","历史最低水位":"","低历史最低水位":"","入库流量":"","出库流量":"","附近雨量站":"","水势":"0","时间":"2019/6/27 8:W00:W00","时间s":"2019/6/27 8:W00:W00"},{"gid":"14","ogr_geometry":"POINT (113.136472793414 34.0870521441379)","ShapeType":"POINT","站码":"50605520","站名":"老虎洞","政区":"郏县","库水位":"235.73","蓄水量":"0.523","可用水量":"0.33","死库容":"0.19","汛限水位":"246","超汛限水位":"-10.270","旱限水位":"","低旱限水位":"","历史最高水位":"246.5","超历史最高水位":"-10.34","历史最低水位":"","低历史最低水位":"","入库流量":"","出库流量":"","附近雨量站":"老虎洞","水势":"0","时间":"2019/6/27 8:W00:W00","时间s":"2019/6/27 8:W00:W00"},{"gid":"15","ogr_geometry":"POINT (112.611 34.217)","ShapeType":"POINT","站码":"50605500","站名":"安沟","政区":"汝州市","库水位":"259.12","蓄水量":"0.827","可用水量":"0.75","死库容":"0.08","汛限水位":"270","超汛限水位":"-10.880","旱限水位":"260","低旱限水位":"0.880","历史最高水位":"270.63","超历史最高水位":"-10.71","历史最低水位":"","低历史最低水位":"","入库流量":"","出库流量":"","附近雨量站":"安沟","水势":"0","时间":"2019/6/27 8:W00:W00","时间s":"2019/6/27 8:W00:W00"},{"gid":"10","ogr_geometry":"POINT (112.771478139944 33.8346984535555)","ShapeType":"POINT","站码":"50604610","站名":"米湾","政区":"鲁山县","库水位":"197.95","蓄水量":"3.755","可用水量":"3.44","死库容":"0.32","汛限水位":"201","超汛限水位":"-3.050","旱限水位":"","低旱限水位":"","历史最高水位":"200.81","超历史最高水位":"-2.W01","历史最低水位":"","低历史最低水位":"","入库流量":"","出库流量":"","附近雨量站":"米湾","水势":"0","时间":"2019/6/26 8:W00:W00","时间s":"2019/6/26 8:W00:W00"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    protected SYQ_SKSWData(Parcel in) {
        code = in.readInt();
        msg = in.readString();
        data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Creator<SYQ_SKSWData> CREATOR = new Creator<SYQ_SKSWData>() {
        @Override
        public SYQ_SKSWData createFromParcel(Parcel in) {
            return new SYQ_SKSWData(in);
        }

        @Override
        public SYQ_SKSWData[] newArray(int size) {
            return new SYQ_SKSWData[size];
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

    public static class DataBean implements Parcelable {
        /**
         * gid : 1
         * ogr_geometry : POINT (112.611 34.217)
         * ShapeType : POINT
         * 站码 : 50604890
         * 站名 : 涧山口
         * 政区 : 汝州市
         * 库水位 : 272.47
         * 蓄水量 : 5.64
         * 可用水量 : 5.38
         * 死库容 : 0.26
         * 汛限水位 : 272.5
         * 超汛限水位 : -0.030
         * 旱限水位 : 269.6
         * 低旱限水位 : -2.870
         * 历史最高水位 : 274.3
         * 超历史最高水位 : -1.85
         * 历史最低水位 :
         * 低历史最低水位 :
         * 入库流量 :
         * 出库流量 : 2
         * 附近雨量站 : 涧山口
         * 水势 : 0
         * 时间 : 2019/6/27 8:W00:W00
         * 时间s : 2019/6/27 8:W00:W00
         */

        private String gid;
        private String ogr_geometry;
        private String ShapeType;
        private String 站码;
        private String 站名;
        private String 政区;
        private String 库水位;
        private String 蓄水量;
        private String 可用水量;
        private String 死库容;
        private String 汛限水位;
        private String 超汛限水位;
        private String 旱限水位;
        private String 低旱限水位;
        private String 历史最高水位;
        private String 超历史最高水位;
        private String 历史最低水位;
        private String 低历史最低水位;
        private String 入库流量;
        private String 出库流量;
        private String 附近雨量站;
        private String 水势;
        private String 时间;
        private String 时间s;

        protected DataBean(Parcel in) {
            gid = in.readString();
            ogr_geometry = in.readString();
            ShapeType = in.readString();
            站码 = in.readString();
            站名 = in.readString();
            政区 = in.readString();
            库水位 = in.readString();
            蓄水量 = in.readString();
            可用水量 = in.readString();
            死库容 = in.readString();
            汛限水位 = in.readString();
            超汛限水位 = in.readString();
            旱限水位 = in.readString();
            低旱限水位 = in.readString();
            历史最高水位 = in.readString();
            超历史最高水位 = in.readString();
            历史最低水位 = in.readString();
            低历史最低水位 = in.readString();
            入库流量 = in.readString();
            出库流量 = in.readString();
            附近雨量站 = in.readString();
            水势 = in.readString();
            时间 = in.readString();
            时间s = in.readString();
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

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getOgr_geometry() {
            return ogr_geometry;
        }

        public void setOgr_geometry(String ogr_geometry) {
            this.ogr_geometry = ogr_geometry;
        }

        public String getShapeType() {
            return ShapeType;
        }

        public void setShapeType(String ShapeType) {
            this.ShapeType = ShapeType;
        }

        public String get站码() {
            return 站码;
        }

        public void set站码(String 站码) {
            this.站码 = 站码;
        }

        public String get站名() {
            return 站名;
        }

        public void set站名(String 站名) {
            this.站名 = 站名;
        }

        public String get政区() {
            return 政区;
        }

        public void set政区(String 政区) {
            this.政区 = 政区;
        }

        public String get库水位() {
            return 库水位;
        }

        public void set库水位(String 库水位) {
            this.库水位 = 库水位;
        }

        public String get蓄水量() {
            return 蓄水量;
        }

        public void set蓄水量(String 蓄水量) {
            this.蓄水量 = 蓄水量;
        }

        public String get可用水量() {
            return 可用水量;
        }

        public void set可用水量(String 可用水量) {
            this.可用水量 = 可用水量;
        }

        public String get死库容() {
            return 死库容;
        }

        public void set死库容(String 死库容) {
            this.死库容 = 死库容;
        }

        public String get汛限水位() {
            return 汛限水位;
        }

        public void set汛限水位(String 汛限水位) {
            this.汛限水位 = 汛限水位;
        }

        public String get超汛限水位() {
            return 超汛限水位;
        }

        public void set超汛限水位(String 超汛限水位) {
            this.超汛限水位 = 超汛限水位;
        }

        public String get旱限水位() {
            return 旱限水位;
        }

        public void set旱限水位(String 旱限水位) {
            this.旱限水位 = 旱限水位;
        }

        public String get低旱限水位() {
            return 低旱限水位;
        }

        public void set低旱限水位(String 低旱限水位) {
            this.低旱限水位 = 低旱限水位;
        }

        public String get历史最高水位() {
            return 历史最高水位;
        }

        public void set历史最高水位(String 历史最高水位) {
            this.历史最高水位 = 历史最高水位;
        }

        public String get超历史最高水位() {
            return 超历史最高水位;
        }

        public void set超历史最高水位(String 超历史最高水位) {
            this.超历史最高水位 = 超历史最高水位;
        }

        public String get历史最低水位() {
            return 历史最低水位;
        }

        public void set历史最低水位(String 历史最低水位) {
            this.历史最低水位 = 历史最低水位;
        }

        public String get低历史最低水位() {
            return 低历史最低水位;
        }

        public void set低历史最低水位(String 低历史最低水位) {
            this.低历史最低水位 = 低历史最低水位;
        }

        public String get入库流量() {
            return 入库流量;
        }

        public void set入库流量(String 入库流量) {
            this.入库流量 = 入库流量;
        }

        public String get出库流量() {
            return 出库流量;
        }

        public void set出库流量(String 出库流量) {
            this.出库流量 = 出库流量;
        }

        public String get附近雨量站() {
            return 附近雨量站;
        }

        public void set附近雨量站(String 附近雨量站) {
            this.附近雨量站 = 附近雨量站;
        }

        public String get水势() {
            return 水势;
        }

        public void set水势(String 水势) {
            this.水势 = 水势;
        }

        public String get时间() {
            return 时间;
        }

        public void set时间(String 时间) {
            this.时间 = 时间;
        }

        public String get时间s() {
            return 时间s;
        }

        public void set时间s(String 时间s) {
            this.时间s = 时间s;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "gid='" + gid + '\'' +
                    ", ogr_geometry='" + ogr_geometry + '\'' +
                    ", ShapeType='" + ShapeType + '\'' +
                    ", 站码='" + 站码 + '\'' +
                    ", 站名='" + 站名 + '\'' +
                    ", 政区='" + 政区 + '\'' +
                    ", 库水位='" + 库水位 + '\'' +
                    ", 蓄水量='" + 蓄水量 + '\'' +
                    ", 可用水量='" + 可用水量 + '\'' +
                    ", 死库容='" + 死库容 + '\'' +
                    ", 汛限水位='" + 汛限水位 + '\'' +
                    ", 超汛限水位='" + 超汛限水位 + '\'' +
                    ", 旱限水位='" + 旱限水位 + '\'' +
                    ", 低旱限水位='" + 低旱限水位 + '\'' +
                    ", 历史最高水位='" + 历史最高水位 + '\'' +
                    ", 超历史最高水位='" + 超历史最高水位 + '\'' +
                    ", 历史最低水位='" + 历史最低水位 + '\'' +
                    ", 低历史最低水位='" + 低历史最低水位 + '\'' +
                    ", 入库流量='" + 入库流量 + '\'' +
                    ", 出库流量='" + 出库流量 + '\'' +
                    ", 附近雨量站='" + 附近雨量站 + '\'' +
                    ", 水势='" + 水势 + '\'' +
                    ", 时间='" + 时间 + '\'' +
                    ", 时间s='" + 时间s + '\'' +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(gid);
            dest.writeString(ogr_geometry);
            dest.writeString(ShapeType);
            dest.writeString(站码);
            dest.writeString(站名);
            dest.writeString(政区);
            dest.writeString(库水位);
            dest.writeString(蓄水量);
            dest.writeString(可用水量);
            dest.writeString(死库容);
            dest.writeString(汛限水位);
            dest.writeString(超汛限水位);
            dest.writeString(旱限水位);
            dest.writeString(低旱限水位);
            dest.writeString(历史最高水位);
            dest.writeString(超历史最高水位);
            dest.writeString(历史最低水位);
            dest.writeString(低历史最低水位);
            dest.writeString(入库流量);
            dest.writeString(出库流量);
            dest.writeString(附近雨量站);
            dest.writeString(水势);
            dest.writeString(时间);
            dest.writeString(时间s);
        }
    }

    @Override
    public String toString() {
        return "SYQ_SKSWData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
