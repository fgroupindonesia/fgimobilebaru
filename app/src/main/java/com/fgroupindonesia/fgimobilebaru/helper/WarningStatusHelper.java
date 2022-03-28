package com.fgroupindonesia.fgimobilebaru.helper;

public class WarningStatusHelper {

    String status;
    int val;

    public WarningStatusHelper(int v){
        val = v;
    }

    public boolean isSafe(){
        if(val == 0){
            return true;
        }

        return false;
    }

    public String getStatus(){
        switch (val){
            case 1 :
                status = Keys.STATUS_WARN_ATTENDANCE;
                break;
            case 2:
                status = Keys.STATUS_WARN_PAYMENT;
                break;
            case 3:
                status = Keys.STATUS_WARN_EXERCISE;
                break;
            case 4:
                status = Keys.STATUS_WARN_MULTI_ATTENDANCE;
                break;
        }

        return status;
    }

}
