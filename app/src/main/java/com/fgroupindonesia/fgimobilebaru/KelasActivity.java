package com.fgroupindonesia.fgimobilebaru;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fgroupindonesia.fgimobilebaru.helper.ArrayHelper;
import com.fgroupindonesia.fgimobilebaru.helper.HistoryHelper;
import com.fgroupindonesia.fgimobilebaru.helper.Keys;
import com.fgroupindonesia.fgimobilebaru.helper.Navigator;
import com.fgroupindonesia.fgimobilebaru.helper.RespondHelper;
import com.fgroupindonesia.fgimobilebaru.helper.ScheduleChecker;
import com.fgroupindonesia.fgimobilebaru.helper.ShowDialog;
import com.fgroupindonesia.fgimobilebaru.helper.URLReference;
import com.fgroupindonesia.fgimobilebaru.helper.WebRequest;
import com.fgroupindonesia.fgimobilebaru.helper.shared.UserData;
import com.fgroupindonesia.fgimobilebaru.object.RemoteLoginClient;
import com.fgroupindonesia.fgimobilebaru.object.Schedule;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.fgroupindonesia.fgimobilebaru.helper.Keys.STATUS_RATE_CONFUSED;

public class KelasActivity extends AppCompatActivity implements Navigator {

    LinearLayout linearLayoutLoading, linearLayoutKelas, linearLayoutKelasAktif, linearLayoutKelasBelumAktif,
            linearLayoutKelasRating, linearRatingExcited, linearRatingNormal, linearRatingConfused;

    String usName, aToken;
    ScheduleChecker engineScheduleChecker = new ScheduleChecker(this);
    Schedule dataSchedulesIn [] =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelas);


        // for shared preference
        UserData.setPreference(this);

        usName = UserData.getPreferenceString(Keys.USERNAME);
        aToken = UserData.getPreferenceString(Keys.TOKEN);

        linearLayoutLoading = (LinearLayout) findViewById(R.id.linearLayoutLoading);
        linearLayoutKelas = (LinearLayout) findViewById(R.id.linearLayoutKelas);

        linearLayoutKelasAktif = (LinearLayout) findViewById(R.id.linearLayoutKelasAktif);
        linearLayoutKelasBelumAktif = (LinearLayout) findViewById(R.id.linearLayoutKelasBelumAktif);

        linearLayoutKelasRating = (LinearLayout) findViewById(R.id.linearLayoutKelasRating);

        showLayout(Keys.LAYOUT_LOADING);

        // call the schedule on server
        getScheduleData();
    }

    private void getScheduleData(){

        WebRequest httpCall = new WebRequest(KelasActivity.this, KelasActivity.this);

        httpCall.addData("username", usName);
        httpCall.addData("token", aToken);

        // we need to wait for the response
        httpCall.setWaitState(true);
        httpCall.setRequestMethod(WebRequest.POST_METHOD);
        httpCall.setTargetURL(URLReference.ScheduleAll);
        httpCall.execute();

    }

    private void showLayout(int keyLayout){

        if(keyLayout == Keys.LAYOUT_LOADING){
            linearLayoutLoading.setVisibility(View.VISIBLE);
            linearLayoutKelas.setVisibility(View.GONE);
            linearLayoutKelasRating.setVisibility(View.GONE);
        }else if(keyLayout == Keys.LAYOUT_KELAS_AKTIF){
            linearLayoutKelasRating.setVisibility(View.GONE);
            linearLayoutLoading.setVisibility(View.GONE);
            linearLayoutKelas.setVisibility(View.VISIBLE);
            linearLayoutKelasAktif.setVisibility(View.VISIBLE);
            linearLayoutKelasBelumAktif.setVisibility(View.GONE);
        }else if(keyLayout == Keys.LAYOUT_KELAS_BELUM_AKTIF){
            linearLayoutKelasRating.setVisibility(View.GONE);
            linearLayoutLoading.setVisibility(View.GONE);
            linearLayoutKelas.setVisibility(View.VISIBLE);
            linearLayoutKelasAktif.setVisibility(View.GONE);
            linearLayoutKelasBelumAktif.setVisibility(View.VISIBLE);
        }else if(keyLayout == Keys.LAYOUT_KELAS_RATING){
            linearLayoutKelasRating.setVisibility(View.VISIBLE);
            linearLayoutLoading.setVisibility(View.GONE);
            linearLayoutKelas.setVisibility(View.GONE);
        }

    }


    public void ratingNormal(View v){
        postRating(Keys.STATUS_RATE_NORMAL);
    }

    public void ratingConfused(View v){
        postRating(Keys.STATUS_RATE_CONFUSED);
    }

    public void ratingExcited(View v){
        postRating(Keys.STATUS_RATE_EXCITED);
    }

    private  void postRating(int stat){

        finish();
    }

    @Override
    public void onBackPressed(){
        if(cTimer!=null)
            cTimer.cancel();

        super.onBackPressed();
    }

    CountDownTimer cTimer = null;
    private void startTimerChecking(){

        // duration for checking is every 2 minutes
        int TIME_WAIT_SECOND = 120;
        int timeMilis = 1000 * TIME_WAIT_SECOND;
        int timeMilisExperiment = 1000 * 3;

        cTimer = new CountDownTimer(timeMilisExperiment, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
              // if(engineScheduleChecker.isTodaySchedules(dataSchedulesIn)){
              //     showLayout(Keys.LAYOUT_KELAS_AKTIF);
             //  }else{
                   showLayout(Keys.LAYOUT_KELAS_RATING);
             //  }
            }

        };
        cTimer.start();

    }

    // for History purposes track record
    private void addRecordHistory(String message){
        HistoryHelper hper = new HistoryHelper();
        hper.startStudyingClass(usName, aToken, message, this);
    }

    private String getHourNow(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date now = new Date();
        return sdf.format(now);
    }

    @Override
    public void nextActivity() {

    }

    @Override
    public void onSuccess(String urlTarget, String respond) {

        try{

            Gson objectG = new Gson();

            if (RespondHelper.isValidRespond(respond)) {

                // when it comes with Schedule data
                 if(urlTarget.contains(URLReference.ScheduleAll)){

                    // extracting the data
                    String innerData = RespondHelper.getValue(respond, "multi_data");
                      dataSchedulesIn = objectG.fromJson(innerData, Schedule[].class);

                    // we will check the schedule just as the process earlier from absensiactivity

                     // check first is it eligible for today for signing in?
                     if(engineScheduleChecker.isTodaySchedules(dataSchedulesIn)) {
                         // activate the button
                         showLayout(Keys.LAYOUT_KELAS_AKTIF);

                         // run the timer for checking whether the class has been ended
                         // if so, then show the rating layout
                         startTimerChecking();

                         // add the record on history at server
                         String jam = getHourNow();
                         addRecordHistory("starting class at " + jam);

                     }else {

                         showLayout(Keys.LAYOUT_KELAS_BELUM_AKTIF);
                     }

                }

            } else {

                if(urlTarget.contains(URLReference.ScheduleAll)){
                    // lets show that class isn't started yet
                    showLayout(Keys.LAYOUT_KELAS_BELUM_AKTIF);
                }


            }



        } catch(Exception err){

            ShowDialog.message(this, "Error verifying client. Please contact administrator!");
            ShowDialog.message(this, err.getMessage());
        }


    }
}