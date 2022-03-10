package com.fgroupindonesia.fgimobilebaru.helper;

import android.app.Activity;
import android.graphics.Color;
import android.widget.TextView;

import com.fgroupindonesia.fgimobilebaru.HomeActivity;
import com.fgroupindonesia.fgimobilebaru.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

public class TimerAnimate implements Runnable {
    private Date nowDate, scheduleDate;
    private TextView textView;
    private Timer timerRef;
    private HomeActivity homeAct;
    private boolean working= false;

    public void setWorking(boolean b){
        working = b;
    }

    public boolean isWorking(){
        return working;
    }

    public void setActivity(Activity act){
        homeAct = (HomeActivity) act;
    }

    public void setTimer(Timer aref){
        timerRef = aref;
    }

    public void setTextView(TextView textObj){
        textView = textObj;
    }

    public void setScheduleDate(Date dataIn){
        scheduleDate = dataIn;
    }

    public String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(scheduleDate);
    }

    @Override
    public void run() {

        nowDate = new Date();

        //in milliseconds
        if(scheduleDate!=null){

        long diff = scheduleDate.getTime() - nowDate.getTime();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        if (diffDays != -1) {

            if (textView != null) {
                if (diffDays > 0) {

                    //labelInterval.setText("Next Class : " + diffDays + " hari, " + diffHours + " jam, " + diffMinutes + " menit, " + diffSeconds + " detik.");
                    textView.setText("next : " + diffDays + " Hari, "
                            + diffHours + " Jam, "
                            + diffMinutes + " Menit, "
                            + diffSeconds + " Detik.");

                    textView.setTextColor(Color.BLACK);
                    textView.setBackgroundResource(R.color.yellow);

                } else if (diffHours > 0) {

                    textView.setText("next : Hari ini, "
                            + diffHours + " Jam, "
                            + diffMinutes + " Menit, "
                            + diffSeconds + " Detik.");

                    textView.setTextColor(Color.BLACK);
                    textView.setBackgroundResource(R.color.yellow);

                } else if (diffHours == 0 && diffMinutes>-1 && diffSeconds>-1) {

                    textView.setText("next : Hari ini, "
                            + diffMinutes + " Menit, "
                            + diffSeconds + " Detik.");

                    textView.setTextColor(Color.BLACK);
                    textView.setBackgroundResource(R.color.yellow);

                } else if (diffDays == 0 && diffHours <= 2 && diffHours >= 0) {
                    // here when the class is already passed

                    textView.setText("Kelas hari ini sedang berlangsung.");

                    textView.setTextColor(Color.BLACK);
                    textView.setBackgroundResource(R.color.white);
                    stopTimer();

                }else if(diffDays == 0 && diffHours < 0){
                    textView.setText("Kelas jam " + getTime() +" sudah selesai.");

                    textView.setTextColor(Color.WHITE);
                    textView.setBackgroundResource(R.color.green);
                    stopTimer();
                }


            }


        } else if (diffDays < 0) {

            textView.setText("");
            stopTimer();

        }

        }

    }



    public void stopTimer(){
        if(timerRef!=null) {
            timerRef.cancel();
        }
    }
}
