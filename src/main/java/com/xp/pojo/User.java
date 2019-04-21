package com.xp.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

//要序列化
public class User implements Serializable {
    private Integer id;

    private String username;

    private String password;

    private String realname;

    private int balance;

    private Integer state;

    public User(Integer id, String username, String password, String realname, int balance, Integer state) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.realname = realname;
        this.balance = balance;
        this.state = state;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}