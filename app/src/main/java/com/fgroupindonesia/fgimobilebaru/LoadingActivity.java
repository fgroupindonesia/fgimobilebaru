package com.fgroupindonesia.fgimobilebaru;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.fgroupindonesia.fgimobilebaru.helper.Keys;
import com.fgroupindonesia.fgimobilebaru.helper.UIHelper;
import com.fgroupindonesia.fgimobilebaru.helper.shared.UserData;


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

    private void centerTitleApp(){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
    }

    boolean signedIn;
    private void gotoAnotherActivity(){
        Intent intent;

        signedIn = UserData.getPreferenceBoolean(Keys.SIGNED_IN);

        if(!signedIn) {
            intent  = new Intent(this, LoginActivity.class);
        } else {
            intent  = new Intent(this, HomeActivity.class);
        }

        startActivity(intent);

        finish();
    }

}