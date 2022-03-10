package com.fgroupindonesia.fgimobilebaru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.fgroupindonesia.fgimobilebaru.helper.HistoryHelper;
import com.fgroupindonesia.fgimobilebaru.helper.Keys;
import com.fgroupindonesia.fgimobilebaru.helper.Navigator;
import com.fgroupindonesia.fgimobilebaru.helper.shared.UserData;

public class SettingsActivity extends AppCompatActivity implements Navigator {

    Switch swAutoLogout, swClickSounds, swNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // this is for shared preference
        UserData.setPreference(this);

        swAutoLogout = (Switch) findViewById(R.id.switchAutoLogout);
        swNotification = (Switch) findViewById(R.id.switchNotificationUpdates);
        swClickSounds = (Switch) findViewById(R.id.switchClickSounds);

        swAutoLogout.setChecked(UserData.getPreferenceBoolean(Keys.AUTO_LOGOUT));
        swClickSounds.setChecked(UserData.getPreferenceBoolean(Keys.CLICK_SOUNDS));
        swNotification.setChecked(UserData.getPreferenceBoolean(Keys.NOTIF_UPDATES));

    }

    @Override
    public void onBackPressed() {
        Intent intentCaller = new Intent(this, HomeActivity.class);
        startActivity(intentCaller);

        finish();

    }

    // for History purposes track record
    private void addRecordHistory(String message){
        String username = UserData.getPreferenceString(Keys.USERNAME),
                token = UserData.getPreferenceString(Keys.TOKEN);

        HistoryHelper hper = new HistoryHelper();
        hper.changingSettings(username, token, message, this);
    }

    public void toggleClickSounds(View v){


        if(swClickSounds.isChecked()){
            UserData.savePreference(Keys.CLICK_SOUNDS, true);
            addRecordHistory("activating click sounds in settings!");

        }else{
            UserData.savePreference(Keys.CLICK_SOUNDS, false);
            addRecordHistory("deactivating click sounds in settings!");

        }
    }

    public void toggleNotification(View v){
        // this functionality hasn't been made yet
        // just for dummy toggle at the moment

        if(swNotification.isChecked()){
            UserData.savePreference(Keys.NOTIF_UPDATES, true);
            addRecordHistory("activating notification in mobile update settings!");

        }else{
            UserData.savePreference(Keys.NOTIF_UPDATES, false);
            addRecordHistory("deactivating notification in settings!");

        }
    }

    public void toggleAutoLogout(View v){
        if(swAutoLogout.isChecked()){
            UserData.savePreference(Keys.AUTO_LOGOUT, true);
            addRecordHistory("activating autologout in 5 minutes settings!");

        }else{
            UserData.savePreference(Keys.AUTO_LOGOUT, false);
            addRecordHistory("deactivating autologout in settings!");

        }
    }

    @Override
    public void nextActivity() {

    }

    @Override
    public void onSuccess(String urlTarget, String result) {

    }
}