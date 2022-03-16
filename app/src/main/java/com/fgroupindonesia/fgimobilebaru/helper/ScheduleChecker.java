package com.fgroupindonesia.fgimobilebaru.helper;

import androidx.appcompat.app.AppCompatActivity;

import com.fgroupindonesia.fgimobilebaru.object.Schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleChecker {

    AppCompatActivity act;

    public ScheduleChecker(AppCompatActivity actIn){
        act = actIn;
    }

    private String kelasSaatIni;

    public String getCurrentClass(){
        return kelasSaatIni;
    }

    private boolean isTimeEligilbe(String timeIn){

        // timeIN is using this format HH:mm

        boolean eligible = false;

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date dNow = new Date();

        String timeNow = sdf.format(dNow);

        int hourNow = Integer.parseInt(timeNow.split(":")[0]);
        int hourSchedule = Integer.parseInt(timeIn.split(":")[0]);

        ShowDialog.message(act, "jam sekarang " + hourNow + " sementara jam dia " + hourSchedule);

        // the eligible time is 1 hour only but not more
        if(hourNow < hourSchedule){
            eligible = false;
        }else if(hourNow >= hourSchedule && hourNow <= hourSchedule+1){
            eligible = true;
        }

        return eligible;
    }

    public boolean isTodaySchedules(Schedule[] dataIn){

        // lets check is it today the schedule
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date dNow = new Date();

        String dayFound;
        String dayNow = sdf.format(dNow).toLowerCase();

        boolean todayNeedToSign = false;

        for(Schedule s: dataIn){
            dayFound = s.getDay_schedule();
            if(dayFound.equalsIgnoreCase(dayNow) && isTimeEligilbe(s.getTime_schedule())){

                // the class_reg will be used for later reference
                kelasSaatIni = s.getClass_registered();
                todayNeedToSign = !isMarked(s.getTime_schedule());

                break;
            }
        }

        return todayNeedToSign;

    }

    private boolean isMarked(String timeIn){

        // format time in is
        // HH:mm

        // ceritanya karena tidak ditemukan pada preference
        // maka isMarked (false)
        return false;
    }

}
