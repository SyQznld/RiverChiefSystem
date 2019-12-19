package com.appler.riverchiefsystem.bean;

import java.util.List;

//添加问题 类型
public class ProblemTypeData {


    /**
     * code : 1
     * msg : success
     * data : [{"parents":"施工","child":[{"id":5,"probledetail":"施工点","repairerid":"40","type":"5"},{"id":5,"probledetail":"施工段","repairerid":"40","type":"5"}]},{"parents":"标线","child":[{"id":3,"probledetail":"无标牌","repairerid":"44","type":"3"},{"id":3,"probledetail":"无标线","repairerid":"44","type":"3"}]}]
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
         * parents : 施工
         * child : [{"id":5,"probledetail":"施工点","repairerid":"40","type":"5"},{"id":5,"probledetail":"施工段","repairerid":"40","type":"5"}]
         */

        private String parents;
        private List<ChildBean> child;

        public String getParents() {
            return parents;
        }

        public void setParents(String parents) {
            this.parents = parents;
        }

        public List<ChildBean> getChild() {
            return child;
        }

        public void setChild(List<ChildBean> child) {
            this.child = child;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "parents='" + parents + '\'' +
                    ", child=" + child +
                    '}';
        }

        public static class ChildBean {
            /**
             * id : 5
             * probledetail : 施工点
             * repairerid : 40
             * type : 5
             */

            private int id;
            private String probledetail;
            private String repairerid;
            private String type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getProbledetail() {
                return probledetail;
            }

            public void setProbledetail(String probledetail) {
                this.probledetail = probledetail;
            }

            public String getRepairerid() {
                return repairerid;
            }

            public void setRepairerid(String repairerid) {
                this.repairerid = repairerid;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
