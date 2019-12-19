package com.appler.riverchiefsystem.bean;


import java.util.List;

public class UserBean {


    /**
     * code : 1
     * result : success
     * data : [{"id":1,"username":"admin","name":"admin","password":"111","department":"办公室","telephone":"123","role_id":"1","rolename":"管理员"}]
     */

    private int code;
    private String result;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * username : admin
         * name : admin
         * password : 111
         * department : 办公室
         * telephone : 123
         * role_id : 1
         * rolename : 管理员
         */

        private int id;
        private String username;
        private String name;
        private String password;
        private String department;
        private String telephone;
        private String role_id;
        private String rolename;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getRole_id() {
            return role_id;
        }

        public void setRole_id(String role_id) {
            this.role_id = role_id;
        }

        public String getRolename() {
            return rolename;
        }

        public void setRolename(String rolename) {
            this.rolename = rolename;
        }
    }
}

