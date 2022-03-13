package com.fgroupindonesia.fgimobilebaru.object;

public class RemoteLoginClient {
    private int id;
    private String machine_unique;
    private String country;
    private String region;
    private String city;
    private String isp;
    private String isp_as;
    private String ip_address;
    private String status;
    private String date_created;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMachine_unique() {
        return machine_unique;
    }

    public void setMachine_unique(String machine_unique) {
        this.machine_unique = machine_unique;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getIsp_as() {
        return isp_as;
    }

    public void setIsp_as(String isp_as) {
        this.isp_as = isp_as;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }
}
