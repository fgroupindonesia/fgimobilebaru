package com.fgroupindonesia.fgimobilebaru;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fgroupindonesia.fgimobilebaru.helper.HistoryHelper;
import com.fgroupindonesia.fgimobilebaru.helper.Keys;
import com.fgroupindonesia.fgimobilebaru.helper.Navigator;
import com.fgroupindonesia.fgimobilebaru.helper.RespondHelper;
import com.fgroupindonesia.fgimobilebaru.helper.ShowDialog;
import com.fgroupindonesia.fgimobilebaru.helper.UIHelper;
import com.fgroupindonesia.fgimobilebaru.helper.URLReference;
import com.fgroupindonesia.fgimobilebaru.helper.WebRequest;
import com.fgroupindonesia.fgimobilebaru.helper.WhatsappSender;
import com.fgroupindonesia.fgimobilebaru.helper.shared.UserData;
import com.fgroupindonesia.fgimobilebaru.object.Token;
import com.fgroupindonesia.fgimobilebaru.object.User;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements Navigator {

    EditText editTextUsername, editTextPassword;
    TextView textViewErrorMessage;
    ProgressBar progressBar;
    TextView textViewregisterNewUser, textViewTidakBisaLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // for shared preference
        UserData.setPreference(this);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        progressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);
        textViewErrorMessage = (TextView) findViewById(R.id.textViewErrorMessage);
        textViewTidakBisaLogin = (TextView) findViewById(R.id.textViewTidakBisaLogin);
        textViewregisterNewUser = (TextView) findViewById(R.id.textViewregisterNewUser);

        textViewTidakBisaLogin.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        textViewregisterNewUser.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        UIHelper.toggleTitleApp(this);

        requestPermission();
    }

    public void whatsappAdmin(View v){
        WhatsappSender was = new WhatsappSender(this);
        was.sendMessageToWhatsAppContact("*Admin* \n\nTolong saya tidak bisa login *FGIMobile*!");
    }

    private void requestPermission(){
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.ACCESS_WIFI_STATE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.MODIFY_AUDIO_SETTINGS,
                android.Manifest.permission.SET_ALARM,
                android.Manifest.permission.WAKE_LOCK,
                android.Manifest.permission.ACCESS_NETWORK_STATE
        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }



    public void registerUser(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse(URLReference.RegistrationPage));
        startActivity(browserIntent);
    }

    public String getText(EditText txt){
        return txt.getText().toString();
    }


    public void verifyUser(View v){

        progressBar.setVisibility(View.VISIBLE);

        int WAITING_TIME_DELAY = 3;
        int milisTime = 1000 * WAITING_TIME_DELAY;

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //call and post to the server

                WebRequest httpCall = new WebRequest(LoginActivity.this, LoginActivity.this);

                httpCall.addData("username", getText(editTextUsername));
                httpCall.addData("password", getText(editTextPassword));

                // we need to wait for the response
                httpCall.setWaitState(true);
                httpCall.setRequestMethod(WebRequest.POST_METHOD);
                httpCall.setTargetURL(URLReference.UserLogin);
                httpCall.execute();

            }
        }, milisTime);


    }

    // for History purposes track record
    private void addRecordHistory(String data, String token){
        HistoryHelper hper = new HistoryHelper();
        hper.logging(data, token, this);
    }

    @Override
    public void nextActivity() {
        Intent  intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        
    }

    @Override
    public void onSuccess(String urlTarget, String respond) {
        try {
            // ShowDialog.message(this, "dapt " + respond);

            progressBar.setVisibility(View.INVISIBLE);

            Gson objectG = new Gson();

            if (RespondHelper.isValidRespond(respond)) {

                if (urlTarget.contains(URLReference.UserLogin)) {

                    JSONObject jsons = RespondHelper.getObject(respond, "multi_data");

                    JsonParser parser = new JsonParser();
                    JsonElement mJson =  parser.parse(jsons.toString());

                    Token objectToken  = objectG.fromJson(mJson, Token.class);

                    UserData.savePreference(Keys.USERNAME, objectToken.getUsername());
                    UserData.savePreference(Keys.TOKEN, objectToken.getToken());

                    // add the history on server side
                    addRecordHistory(objectToken.getUsername(), objectToken.getToken());

                }else if(urlTarget.contains(URLReference.HistoryAdd)){

                    textViewErrorMessage.setVisibility(View.INVISIBLE);

                    nextActivity();
                }

            } else if (!RespondHelper.isValidRespond(respond)) {

                textViewErrorMessage.setVisibility(View.VISIBLE);
                //finish();

            }
        } catch (Exception ex) {
            ShowDialog.message(this, "Error " + ex.getMessage());
            ex.printStackTrace();
        }

    }
}