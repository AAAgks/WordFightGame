package com.xiaogan.domain;

import java.util.Random;

public class User {
    private String id;
    private String username;
    private String password;
    private String phoneNumber;
    private boolean status;

    public User() {
        this.id=createId();
        this.status = true;
    }


    public User(String name, String password, String phoneNumber) {
        this.id=createId();
        this.username = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.status = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public  String getUsername() {
        return username;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    public String createId(){
        StringBuilder sb = new StringBuilder("xiaogan");
        Random random = new Random();;// 0-9
        for (int i = 0; i < 5; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
