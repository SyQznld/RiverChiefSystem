package com.appler.riverchiefsystem.dao.daoBean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AdminData {

    @Id
    private Long Id;

    private int userId;
    private String username;
    private String name;
    private String password;
    private String department;
    private String telephone;
    private String role_id;
    private String rolename;
    @Generated(hash = 1483877271)
    public AdminData(Long Id, int userId, String username, String name,
            String password, String department, String telephone, String role_id,
            String rolename) {
        this.Id = Id;
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.password = password;
        this.department = department;
        this.telephone = telephone;
        this.role_id = role_id;
        this.rolename = rolename;
    }
    @Generated(hash = 222887179)
    public AdminData() {
    }
    public Long getId() {
        return this.Id;
    }
    public void setId(Long Id) {
        this.Id = Id;
    }
    public int getUserId() {
        return this.userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getDepartment() {
        return this.department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getTelephone() {
        return this.telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getRole_id() {
        return this.role_id;
    }
    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }
    public String getRolename() {
        return this.rolename;
    }
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

}
