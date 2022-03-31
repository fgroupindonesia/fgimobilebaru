package com.fgroupindonesia.fgimobilebaru.helper;

import androidx.appcompat.app.AppCompatActivity;

import com.fgroupindonesia.fgimobilebaru.object.Schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleChecker {

    AppCompatActivity act;

    public ScheduleChecker(AppCompatActivity actIn) {
        act = actIn;
    }

    private String kelasSaatIni;

    public String getCurrentClass() {
        return kelasSaatIni;
    }

    private boolean isTimeEligilbe(String timeIn) {

        // timeIN is using this format HH:mm

        boolean eligible = false;

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date dNow = new Date();

        String timeNow = sdf.format(dNow);

        int hourNow = Integer.parseInt(timeNow.split(":")[0]);
        int hourSchedule = Integer.parseInt(timeIn.split(":")[0]);

        //ShowDialog.message(act, "jam sekarang " + hourNow + " sementara jam dia " + hourSchedule);

        // the eligible time is 1 hour only but not more
        if (hourNow < hourSchedule) {
            eligible = false;
        } else if (hourNow >= hourSchedule && hourNow <= hourSchedule + 1) {
            eligible = true;
        }

        return eligible;
    }

    private long timeDifference; // stored in milliseconds

    public long getTimeNeededFromNow() {
        return timeDifference;
    }

    private void setTimeNeededFromNow(long l) {
        timeDifference = l;
    }

    private String intoTime(int val) {

        String endRest = null;

        if (val < 10) {
            endRest = "0" + val + ":00";
        } else {
            endRest = val + ":00";
        }

        return endRest;
    }

    public boolean isTodaySchedules(Schedule[] dataIn) {

        // lets check is it today the schedule
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date dNow = new Date();

        SimpleDateFormat sdfDetail = new SimpleDateFormat("yyyy-MM-dd");
        String dateDigit = sdfDetail.format(dNow);

        SimpleDateFormat sdfCompared = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String dayFound, temp;
        String dayNow = sdf.format(dNow).toLowerCase();

        boolean todayNeedToSign = false;

        for (Schedule s : dataIn) {
            dayFound = s.getDay_schedule();
            if (dayFound.equalsIgnoreCase(dayNow) && isTimeEligilbe(s.getTime_schedule())) {

                // time is using HH:mm format
                // so we grab the hour and add by 2
                int hourFinished = Integer.parseInt(s.getTime_schedule().split(":")[0]) + 2;// added by 2 hour

                temp = dateDigit + " " + intoTime(hourFinished);
                Date dScheduleFinished = null;
                try {
                    dScheduleFinished = sdfCompared.parse(temp);
                    // store the milliseconds
                    long timeNa = dScheduleFinished.getTime() - dNow.getTime();
                    // the milliseconds is targeting until the last hour complete
                    setTimeNeededFromNow(timeNa);

                } catch (ParseException e) {
                    ShowDialog.message(act, "Some error on obtaining milliseconds diferences...");
                }


                // the class_reg will be used for later reference
                kelasSaatIni = s.getClass_registered();
                todayNeedToSign = !isMarked(s.getTime_schedule());

                break;
            }
        }

        return todayNeedToSign;

    }

    private boolean isMarked(String timeIn) {

        // format time in is
        // HH:mm

        // ceritanya karena tidak ditemukan pada preference
        // maka isMarked (false)
        return false;
    }

}
