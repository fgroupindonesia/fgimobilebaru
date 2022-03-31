package com.fgroupindonesia.fgimobilebaru;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.fgroupindonesia.fgimobilebaru.helper.Keys;
import com.fgroupindonesia.fgimobilebaru.helper.ShowDialog;
import com.fgroupindonesia.fgimobilebaru.helper.UIHelper;
import com.fgroupindonesia.fgimobilebaru.helper.shared.UserData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LoadingActivity extends AppCompatActivity {

    final int WAITING_TIME = 3000; // 3seconds


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // for future usage
        UserData.setPreference(this);
        //UserData.savePreference(Keys.USER_REGISTER_STATUS, false);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoAnotherActivity();
            }
        }, WAITING_TIME);

        //centerTitleApp();
        UIHelper.toggleTitleApp(this);

    }

    private void centerTitleApp() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
    }

    boolean signedIn;

    private boolean isExpiredNow(String dateIn) {

        boolean yeahExpired = false;

        // if there is no date so break it up
        if(dateIn==null){
            return yeahExpired;
        }
        // let split the data
        // yyyy-MM-dd HH:mm:ss

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateLocal = new Date();

        try {
            Date dateCome = sdf.parse(dateIn);
            if(dateLocal.compareTo(dateCome) > 0){
                yeahExpired = true;
            }
        } catch (ParseException e) {
            ShowDialog.message(this,"error while parsing date " + e.getMessage());
        }

        return yeahExpired;
    }

    private void gotoAnotherActivity() {
        Intent intent;

        signedIn = UserData.getPreferenceBoolean(Keys.SIGNED_IN);

        // need to know when this token expired?
        // is it met expired date?
        // if expired so still login
        // if it's still alive then directly to Home
        boolean expiredNow = false;

        String expDate = UserData.getPreferenceString(Keys.TOKEN_EXPIRED_DATE);
        // the format is using computer mysql format
        // yyyy-MM-dd  HH:mm:ss
        expiredNow = isExpiredNow(expDate);

        if (!signedIn || expiredNow) {
            intent = new Intent(this, LoginActivity.class);
        } else {
            intent = new Intent(this, HomeActivity.class);
        }

        startActivity(intent);

        finish();
    }

}