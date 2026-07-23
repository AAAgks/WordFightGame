package com.xiaogan.bean;

public class Account {
//    属性：id、用户名、密码、状态
//
//**各属性描述：**
//
//            1. id：
//
//            * 用户无法设置，是自动生成的，格式为：heima+5位数字的随机数
//
//2. 用户名username：
//
//            * 用户名唯一
//* 长度必须在3 ~ 16位
//* 只能由字母、数字组成，不能是纯数字
//
//3，密码要求：
//
//            * 长度3 ~ 8位
//* 只能是字母加数字的组合，不能有其他字母
//
//4，状态state
//
//* 默认是可使用状态
//* 如果密码连续输错3次，修改为不可使用状态
    private String id;
    private String username;
    private String password;
    private String status = "normal";

    public Account() {
    }

    public Account(String id, String username, String password, String status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
