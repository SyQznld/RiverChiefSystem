package com.appler.riverchiefsystem.bean;

import java.util.List;

/**
 * 通知公告 实体类
 */

public class NoticeData {


    /**
     * code : 0
     * msg : success
     * data : [{"id":"6011c937-f9f8-469f-adfb-cb8ad97f3e95","name":"清明节假期放假通知","brief":"清明节放假通知","content":"%3Cp%20style%3D%22text-align%3A%20center%3B%22%3E%3Cstrong%3E%u6E05%u660E%u8282%u653E%u5047%u901A%u77E5%3C/strong%3E%3C/p%3E%3Cp%20style%3D%22text-align%3Acenter%22%3E%3Cstrong%3E%3Cimg%20src%3D%22/ueditor/asp/upload/image/20190926/15694623381662305.jpg%22%20title%3D%2215575466489182036.jpg%22%20alt%3D%2215575466489182036.jpg%22/%3E%3C/strong%3E%3C/p%3E%3Cp%20style%3D%22text-align%3A%20left%3B%22%3E%26nbsp%3B%26nbsp%3B%3C/p%3E%3Cp%3E%26nbsp%3B%20%26nbsp%3B%20%26nbsp%3B%20%u6839%u636E%u300A%u56FD%u52A1%u9662%u529E%u516C%u5385%u516C%u5E032019%u5E74%u653E%u5047%u5B89%u6392%u300B%uFF0C%u7ED3%u5408%u516C%u53F8%u7684%u5B9E%u9645%u60C5%u51B5%uFF0C%20%u73B0%u5C062019%u5E74%u4E94-%20-%u653E%u5047%u8C03%u4F11%u65E5%u671F%u5B89%u6392%u901A%u77E5%u5982%u4E0B%3A%3C/p%3E%3Cp%3E%3Cbr/%3E%3C/p%3E%3Cp%3E%26nbsp%3B%20%26nbsp%3B%20%26nbsp%3B%201%u30014%u67081%u65E5%u81F33%u65E5%u653E%u5047%u8C03%u4F11%uFF0C%u51713%u5929%u30024%u67084%u65E5%28%u661F%u671F%u65E5%29%u4E0A%u73ED%u3002%3C/p%3E%3Cp%3E%3Cbr/%3E%3C/p%3E%3Cp%3E%26nbsp%3B%20%26nbsp%3B%20%26nbsp%3B%202%u3001%u653E%u5047%u671F%u95F4%u505A%u597D%u9632%u706B%u3001%u9632%u76D7%u5DE5%u4F5C%uFF0C%u8BF7%u6CE8%u610F%u4E92%u76F8%u7763%u4FC3%u505A%u597D%u529E%u516C%u533A%u57DF%u5185%u7684%u7535%u6E90%u3001%u536B%u751F%u53CA%u843D%u9501%u5DE5%u4F5C%u3002%3C/p%3E%3Cp%3E%3Cbr/%3E%3C/p%3E%3Cp%3E%26nbsp%3B%20%26nbsp%3B%20%26nbsp%3B%203%u3001%u56DE%u5BB6%u6216%u5916%u51FA%u6E38%u73A9%u7684%u5458%u5DE5%u8BF7%u643A%u5E26%u597D%u81EA%u5DF1%u7684%u884C%u674E%u7269%u54C1%uFF0C%u6CE8%u610F%u4EBA%u8EAB%u3001%u8D22%u4EA7%u5B89%u5168%uFF0C%u9075%u5B88%u6709%u5173%u6CD5%u5F8B%u6CD5%u89C4%uFF0C%u63D0%u524D%u505A%u597D%u8FD4%u7A0B%u5B89%u6392%uFF0C%u786E%u4FDD%u5982%u671F%u4E0A%u73ED%u3002%3C/p%3E","addtime":"2019-05-11 11:53:07","updatetime":"2019-09-26 09:45:39","flag":0,"isdeleted":0},{"id":"942a779e-9975-4e82-b042-3522f7fea280","name":"测试项目停止公告","brief":"测试项目因启动资金不足告停测试项目因启动资金不足告停测试项目因启动资金不足告停测试项目因启动资金不足告停测试项目因启动资金不足告停测试项目因启动资金不足告停测试项目因启动资金不足告停测试项目因启动资金不足告停测试项目因启动资金不足告停测试项目因启动资金不足告停测试项目因启动资金不足告停测试项目因启动资金不足告停测试项目因启动资金不足告停测试项目因启动资金不足告停测试项目因启动资金不足告停资金不足告停","content":"%3Cp%20style%3D%22text-align%3Acenter%22%3E%u6D4B%u8BD5%u9879%u76EE%u56E0%u542F%u52A8%u8D44%u91D1%u4E0D%u8DB3%u544A%u505C%3C/p%3E%3Cp%20style%3D%22text-align%3Acenter%22%3E%3Cimg%20src%3D%22/Libs/GCGL/ueditor/asp/upload/image/20190523/15585835588433317.jpg%22%20title%3D%22052305.jpg%22%20alt%3D%22052305.jpg%22%20width%3D%22444%22%20height%3D%22248%22/%3E%3C/p%3E%3Cp%3E%3Cbr/%3E%3C/p%3E","addtime":"2019-05-10 05:32:25","updatetime":"2019-05-23 11:53:38","flag":1,"isdeleted":0}]
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
         * id : 6011c937-f9f8-469f-adfb-cb8ad97f3e95
         * name : 清明节假期放假通知
         * brief : 清明节放假通知
         * content : %3Cp%20style%3D%22text-align%3A%20center%3B%22%3E%3Cstrong%3E%u6E05%u660E%u8282%u653E%u5047%u901A%u77E5%3C/strong%3E%3C/p%3E%3Cp%20style%3D%22text-align%3Acenter%22%3E%3Cstrong%3E%3Cimg%20src%3D%22/ueditor/asp/upload/image/20190926/15694623381662305.jpg%22%20title%3D%2215575466489182036.jpg%22%20alt%3D%2215575466489182036.jpg%22/%3E%3C/strong%3E%3C/p%3E%3Cp%20style%3D%22text-align%3A%20left%3B%22%3E%26nbsp%3B%26nbsp%3B%3C/p%3E%3Cp%3E%26nbsp%3B%20%26nbsp%3B%20%26nbsp%3B%20%u6839%u636E%u300A%u56FD%u52A1%u9662%u529E%u516C%u5385%u516C%u5E032019%u5E74%u653E%u5047%u5B89%u6392%u300B%uFF0C%u7ED3%u5408%u516C%u53F8%u7684%u5B9E%u9645%u60C5%u51B5%uFF0C%20%u73B0%u5C062019%u5E74%u4E94-%20-%u653E%u5047%u8C03%u4F11%u65E5%u671F%u5B89%u6392%u901A%u77E5%u5982%u4E0B%3A%3C/p%3E%3Cp%3E%3Cbr/%3E%3C/p%3E%3Cp%3E%26nbsp%3B%20%26nbsp%3B%20%26nbsp%3B%201%u30014%u67081%u65E5%u81F33%u65E5%u653E%u5047%u8C03%u4F11%uFF0C%u51713%u5929%u30024%u67084%u65E5%28%u661F%u671F%u65E5%29%u4E0A%u73ED%u3002%3C/p%3E%3Cp%3E%3Cbr/%3E%3C/p%3E%3Cp%3E%26nbsp%3B%20%26nbsp%3B%20%26nbsp%3B%202%u3001%u653E%u5047%u671F%u95F4%u505A%u597D%u9632%u706B%u3001%u9632%u76D7%u5DE5%u4F5C%uFF0C%u8BF7%u6CE8%u610F%u4E92%u76F8%u7763%u4FC3%u505A%u597D%u529E%u516C%u533A%u57DF%u5185%u7684%u7535%u6E90%u3001%u536B%u751F%u53CA%u843D%u9501%u5DE5%u4F5C%u3002%3C/p%3E%3Cp%3E%3Cbr/%3E%3C/p%3E%3Cp%3E%26nbsp%3B%20%26nbsp%3B%20%26nbsp%3B%203%u3001%u56DE%u5BB6%u6216%u5916%u51FA%u6E38%u73A9%u7684%u5458%u5DE5%u8BF7%u643A%u5E26%u597D%u81EA%u5DF1%u7684%u884C%u674E%u7269%u54C1%uFF0C%u6CE8%u610F%u4EBA%u8EAB%u3001%u8D22%u4EA7%u5B89%u5168%uFF0C%u9075%u5B88%u6709%u5173%u6CD5%u5F8B%u6CD5%u89C4%uFF0C%u63D0%u524D%u505A%u597D%u8FD4%u7A0B%u5B89%u6392%uFF0C%u786E%u4FDD%u5982%u671F%u4E0A%u73ED%u3002%3C/p%3E
         * addtime : 2019-05-11 11:53:07
         * updatetime : 2019-09-26 09:45:39
         * flag : 0
         * isdeleted : 0
         */

        private String id;
        private String name;
        private String brief;
        private String content;
        private String addtime;
        private String updatetime;
        private int flag;
        private int isdeleted;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getIsdeleted() {
            return isdeleted;
        }

        public void setIsdeleted(int isdeleted) {
            this.isdeleted = isdeleted;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", brief='" + brief + '\'' +
                    ", content='" + content + '\'' +
                    ", addtime='" + addtime + '\'' +
                    ", updatetime='" + updatetime + '\'' +
                    ", flag=" + flag +
                    ", isdeleted=" + isdeleted +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NoticeData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
