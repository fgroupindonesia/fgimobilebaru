package com.fgroupindonesia.fgimobilebaru.object;

public class Token {

    private String username;
    private String token;
    private String expired_date;
    private int warning_status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpired_date() {
        return expired_date;
    }

    public void setExpired_date(String expired_date) {
        this.expired_date = expired_date;
    }

    public int getWarning_status() {
        return warning_status;
    }

    public void setWarning_status(int warning_status) {
        this.warning_status = warning_status;
    }
}
