package com.fgroupindonesia.fgimobilebaru.object;

public class User {
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPropic() {
        return propic;
    }

    public void setPropic(String propic) {
        this.propic = propic;
    }

    int id;
    private int access_level;
    private int warning_status;
    String username;
    String pass;
    String email;
    private String address;
    private String mobile;
    private String tmv_id;
    private String tmv_pass;
    String propic;


    public int getAccess_level() {
        return access_level;
    }

    public void setAccess_level(int access_level) {
        this.access_level = access_level;
    }

    public int getWarning_status() {
        return warning_status;
    }

    public void setWarning_status(int warning_status) {
        this.warning_status = warning_status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTmv_id() {
        return tmv_id;
    }

    public void setTmv_id(String tmv_id) {
        this.tmv_id = tmv_id;
    }

    public String getTmv_pass() {
        return tmv_pass;
    }

    public void setTmv_pass(String tmv_pass) {
        this.tmv_pass = tmv_pass;
    }
}
