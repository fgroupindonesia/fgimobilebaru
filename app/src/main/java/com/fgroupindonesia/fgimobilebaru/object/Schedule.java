package com.fgroupindonesia.fgimobilebaru.object;

public class Schedule {
    private int id;
    private String time_schedule;
    private String day_schedule;
    private String class_registered;
    private String username;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the time_schedule
     */
    public String getTime_schedule() {
        return time_schedule;
    }

    /**
     * @param time_schedule the time_schedule to set
     */
    public void setTime_schedule(String time_schedule) {
        this.time_schedule = time_schedule;
    }

    /**
     * @return the day_schedule
     */
    public String getDay_schedule() {
        return day_schedule;
    }

    /**
     * @param day_schedule the day_schedule to set
     */
    public void setDay_schedule(String day_schedule) {
        this.day_schedule = day_schedule;
    }

    /**
     * @return the class_registered
     */
    public String getClass_registered() {
        return class_registered;
    }

    /**
     * @param class_registered the class_registered to set
     */
    public void setClass_registered(String class_registered) {
        this.class_registered = class_registered;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
}

