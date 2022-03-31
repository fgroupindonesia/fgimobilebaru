package com.fgroupindonesia.fgimobilebaru;

import androidx.appcompat.app.AppCompatActivity;

import com.fgroupindonesia.fgimobilebaru.helper.AudioPlayer;
import com.fgroupindonesia.fgimobilebaru.helper.HistoryHelper;
import com.fgroupindonesia.fgimobilebaru.helper.Keys;
import com.fgroupindonesia.fgimobilebaru.helper.Navigator;
import com.fgroupindonesia.fgimobilebaru.helper.RespondHelper;
import com.fgroupindonesia.fgimobilebaru.helper.ScheduleSummary;
import com.fgroupindonesia.fgimobilebaru.helper.ShowDialog;
import com.fgroupindonesia.fgimobilebaru.helper.UIHelper;
import com.fgroupindonesia.fgimobilebaru.helper.URLReference;
import com.fgroupindonesia.fgimobilebaru.helper.WarningStatusHelper;
import com.fgroupindonesia.fgimobilebaru.helper.WebRequest;
import com.fgroupindonesia.fgimobilebaru.helper.shared.UserData;
import com.fgroupindonesia.fgimobilebaru.object.Schedule;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import static com.fgroupindonesia.fgimobilebaru.helper.Keys.*;

public class HomeActivity extends AppCompatActivity implements Navigator {

    WarningStatusHelper wsh;
    TextView textViewLogout, textviewUsername,
            textViewExpiredDate, textViewMessage;
    WebRequest httpCall;
    ImageView imageViewSertifikasi, imageViewKelas, imageViewDokumen;
    String filePropicName, usName, aToken;

    ImageView imageUserProfileHome;
    boolean mainMenuShown, hasInternetConnection = true;

    LinearLayout linearDocument, linearOption, linearHistory, linearKelas, linearDesktop, linearTagihan,
            linearAbsensi, linearSertifikasi, linearLayoutUserProfile;

    int warn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        addScreenUnlockFlag();
        textViewExpiredDate = (TextView) findViewById(R.id.textViewExpiredDate);

        // applying animation
        textViewExpiredDate.setBackground(this.getDrawable(R.drawable.round_textview_gray));
        textViewExpiredDate.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in_out));

        textviewUsername = (TextView) findViewById(R.id.textviewUsername);
        textViewLogout = (TextView) findViewById(R.id.textViewLogout);
        textViewMessage = (TextView) findViewById(R.id.textViewMessage);

        imageViewSertifikasi = (ImageView) findViewById(R.id.imageViewHomeSertifikasi);
        imageViewKelas = (ImageView) findViewById(R.id.imageViewHomeKelas) ;
        imageViewDokumen = (ImageView) findViewById(R.id.imageViewHomeDokumen);

        // textViewMessage.setBackgroundResource(R.color.yellow);
        textViewMessage.setBackground(this.getDrawable(R.drawable.round_textview_yellow));

         imageUserProfileHome = (ImageView) findViewById(R.id.imageUserProfileHome);

        // for shared preference usage
        UserData.setPreference(this);

        usName = UserData.getPreferenceString(Keys.USERNAME);
        aToken = UserData.getPreferenceString(Keys.TOKEN);
        warn = UserData.getPreferenceInt(WARNING_STATUS);

      //  ShowDialog.message(this, "didapat warn " + warn);

        if (usName != null) {
            textviewUsername.setText(usName);
        }

        // making things underlined
        textviewUsername.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        textViewLogout.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        linearSertifikasi = (LinearLayout) findViewById(R.id.linearSertifikasi);
        linearAbsensi = (LinearLayout) findViewById(R.id.linearAbsensi);
        linearDocument = (LinearLayout) findViewById(R.id.linearDocument);

        linearOption = (LinearLayout) findViewById(R.id.linearOption);
        linearHistory = (LinearLayout) findViewById(R.id.linearHistory);
        linearKelas = (LinearLayout) findViewById(R.id.linearKelas);
        linearDesktop = (LinearLayout) findViewById(R.id.linearDesktop);
        linearTagihan = (LinearLayout) findViewById(R.id.linearTagihan);

        if(checkInternet()){
            // first time calling the webserver
            // to get user profile propic name
            // we call this after 4 seconds
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    getUserDataAPI();
                }
            }, PERIOD_OF_TIME_WAIT_GENERAL);

            // to get the schedule of this person registered
            getScheduleDataAPI();
        }

        // this will autologoff only if the person toggle ON from the settings
        autoLogoff();

    }


    @Override
    public void onBackPressed() {

        if (!mainMenuShown) {
            mainMenuShown = !mainMenuShown;
            showMainMenu(mainMenuShown);

        } else {

            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Exit")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //super.onBackPressed();
                            //Or used finish();
                            //ActivityCompat.finishAffinity(HomeActivity.this);
                            logout(null);
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();

        }


    }

    private void disableByWarningStatus(){

        if(!wsh.isSafe()){
            // cannot open dokumen, kelas, and sertifikat
            imageViewSertifikasi.setImageResource(0);
            imageViewSertifikasi.setImageResource(R.drawable.certificate_off);

            imageViewDokumen.setImageResource(0);
            imageViewDokumen.setImageResource(R.drawable.document_off);

            imageViewKelas.setImageResource(0);
            imageViewKelas.setImageResource(R.drawable.board_off);
        }else {
            imageViewSertifikasi.setImageResource(0);
            imageViewSertifikasi.setImageResource(R.drawable.certificate);

            imageViewDokumen.setImageResource(0);
            imageViewDokumen.setImageResource(R.drawable.document);

            imageViewKelas.setImageResource(0);
            imageViewKelas.setImageResource(R.drawable.board);
        }

    }

    @Override
    public void nextActivity() {

    }

    ScheduleSummary schSummary;
    private void applyWarningStatus(){
        warn = UserData.getPreferenceInt(WARNING_STATUS);
        wsh = new WarningStatusHelper(warn);

        if(wsh.isSafe()){
            // if no warning found
            textViewMessage.setText(UIHelper.replaceAllEnglishToIndonesian(schSummary.getText()));
            textViewMessage.setTextColor(Color.BLACK);
            //textViewMessage.setBackgroundResource(R.color.yellow);
            textViewMessage.setBackground(this.getDrawable(R.drawable.round_textview_yellow));

        }else{
            textViewMessage.setTextColor(Color.WHITE);
            textViewMessage.setText(wsh.getStatus() + " : Please contact admin!");
            // textViewMessage.setBackgroundResource(R.color.red);
            textViewMessage.setBackground(this.getDrawable(R.drawable.round_textview_red));

        }

        // only applied if the warning status isn't safe
        // otherwise all lock will be opened again
        disableByWarningStatus();
    }

    // for experimental purposes
    // such as expired after study or amount of class attended later
    String expDate;

    @Override
    public void onSuccess(String urlTarget, String respond) {

        try {
            Gson objectG = new Gson();

            if (RespondHelper.isValidRespond(respond)) {

                if (urlTarget.contains(URLReference.UserProfile)) {

                    JSONObject jo = RespondHelper.getObject(respond, "multi_data");
                    filePropicName = jo.getString("propic");

                    ShowDialog.message(this, "propic got " + filePropicName);

                    expDate = jo.getString("expired_date");

                    ShowDialog.message(this, "exp got " + expDate);

                    if(expDate==null) {
                        textViewExpiredDate.setText("Exp. Date : unlimited.");
                    }else{
                        UserData.savePreference(USER_EXPIRED_DATE, expDate);
                        textViewExpiredDate.setText("Exp. Date : " + UIHelper.convertDateToIndonesia(expDate));
                    }

                    //ShowDialog.message(this, "propic got " + filePropicName);

                    // calling another process to show the images
                    downloadPictureAPI();

                } else  if (urlTarget.contains(URLReference.ScheduleAll)) {

                    // this is obtaining the schedule he was registered as student
                    JSONArray jsons = RespondHelper.getArray(respond, "multi_data");

                    JsonParser parser = new JsonParser();
                    JsonElement mJson =  parser.parse(jsons.toString());

                    Schedule objectSchedule []  = objectG.fromJson(mJson, Schedule[].class);

                    schSummary = new ScheduleSummary(objectSchedule);

                    textViewMessage.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    textViewMessage.setSelected(true);
                    textViewMessage.setSingleLine(true);

                    if(schSummary!=null) {
                        applyWarningStatus();
                    }

                }
            } else if (!RespondHelper.isValidRespond(respond)) {

                // getting the picture is actually non-valid returned value
                if (urlTarget.contains(URLReference.UserPicture)) {
                    // refreshing the imageview
                    //ShowDialog.message(this, "downloading got " + respond);
                    // memory saver
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 5;

                    Bitmap b = BitmapFactory.decodeFile(respond);
                    imageUserProfileHome.setImageBitmap(b);
                }

            }
        } catch (Exception ex) {
            ShowDialog.message(this, "Error 292 " + ex.getMessage());
            ex.printStackTrace();
        }


    }

    @Override
    public void onResume(){

        super.onResume();

        if(schSummary!=null) {
            applyWarningStatus();
        }
    }

    private void showMainMenu(boolean b) {

        if (b) {
            linearOption.setVisibility(View.VISIBLE);
            linearKelas.setVisibility(View.VISIBLE);
            linearHistory.setVisibility(View.VISIBLE);
            linearDesktop.setVisibility(View.VISIBLE);
            linearTagihan.setVisibility(View.VISIBLE);
            linearDocument.setVisibility(View.VISIBLE);
        } else {
            linearOption.setVisibility(View.GONE);
            linearKelas.setVisibility(View.GONE);
            linearHistory.setVisibility(View.GONE);
            linearDesktop.setVisibility(View.GONE);
            linearTagihan.setVisibility(View.GONE);
            linearDocument.setVisibility(View.GONE);
        }
    }

    public void addScreenUnlockFlag() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    // for History purposes track record
    private void addRecordHistory(){
        HistoryHelper hper = new HistoryHelper();
        hper.loggingOut(usName, aToken, this);
    }

    public void logout(View v) {

        // call the Web that this person has been logged out from mobile
        addRecordHistory();


        // clearing several data
        UserData.savePreference(Keys.USERNAME, null);
        UserData.savePreference(Keys.PASSWORD, null);
        UserData.savePreference(Keys.TOKEN, null);
        UserData.savePreference(Keys.SIGNED_IN, false);

        finish();

    }

    public void getUserDataAPI() {

        //UIAction.ACT_API_CURRENT_CALL = OPSAction.ACT_API_USERPROFILE_LOAD_DATA;
        // the web request executed by httcall
        // preparing the httpcall
        WebRequest httpCall = new WebRequest(this, this);
        httpCall.addData("token", UserData.getPreferenceString(Keys.TOKEN));
        httpCall.addData("username", UserData.getPreferenceString(Keys.USERNAME));
        httpCall.setWaitState(true);
        httpCall.setRequestMethod(WebRequest.POST_METHOD);
        httpCall.setTargetURL(URLReference.UserProfile);
        httpCall.execute();

    }


    public void getScheduleDataAPI() {

        //UIAction.ACT_API_CURRENT_CALL = OPSAction.ACT_API_USERPROFILE_LOAD_DATA;
        // the web request executed by httcall
        // preparing the httpcall
        WebRequest httpCall = new WebRequest(this, this);
        httpCall.addData("token", UserData.getPreferenceString(Keys.TOKEN));
        httpCall.addData("username", UserData.getPreferenceString(Keys.USERNAME));
        httpCall.setWaitState(true);
        httpCall.setRequestMethod(WebRequest.POST_METHOD);
        httpCall.setTargetURL(URLReference.ScheduleAll);
        httpCall.execute();

    }

    public void downloadPictureAPI() {

        //UIAction.ACT_API_CURRENT_CALL = OPSAction.ACT_API_USERPROFILE_DOWNLOAD_PICTURE;

        WebRequest httpCall = new WebRequest(this, this);
        //httpCall.addData("token", UserData.getPreferenceString(Keys.TOKEN));
        httpCall.addData("propic", filePropicName);
        httpCall.setWaitState(true);
        httpCall.setDownloadState(true);

        httpCall.setRequestMethod(WebRequest.GET_METHOD);
        httpCall.setTargetURL(URLReference.UserPicture + filePropicName);
        httpCall.execute();

    }

    private boolean checkInternet(){

        boolean active = true;
        // if any internet available we do our work...
        // otherwise show the error
        if(!WebRequest.checkConnection(HomeActivity.this)) {
            // when he doesn't have the internet we show the warning
            warningNoInternet();
            active = false;
            hasInternetConnection = false;
        }

        return active;
    }

    private void warningNoInternet(){
        textViewMessage.setText("No Internet, harap relogin kembali!!!");
        textViewMessage.setTextColor(Color.WHITE);
        textViewMessage.setBackgroundResource(R.color.red);
    }

    private void autoLogoff(){
        int min = 5;
        int realMilliseconds = min * 60 * 1000;

        int simulationSeconds = 3000;

        // if let say by 5 min nothing happened
        if(UserData.getPreferenceBoolean(AUTO_LOGOUT)){
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    logout(null);
                }
            }, realMilliseconds);
        }

    }

    public void openMaps(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLReference.Gmaps));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        try {
            startActivity(intent);
        } catch (Exception ex) {
            // Chrome browser presumably not installed so allow user to choose instead
            intent.setPackage(null);
            startActivity(intent);
        }
    }

    @Override
    public void onFailed(){
        // usually because no internet

    }

    private void nextActivity(int jenisActivity) {


        Intent intent = null;
        boolean needRefreshLater = false;
        boolean canOpen = false;

      if (jenisActivity == ACT_OPTIONS) {
            intent = new Intent(this, SettingsActivity.class);
            needRefreshLater = true;
            canOpen = true;
        } else if (jenisActivity == ACT_USER_PROFILE) {
            intent = new Intent(this, UserProfileActivity.class);
            needRefreshLater = true;
            canOpen = true;
        } else if (jenisActivity == ACT_HISTORY) {
            intent = new Intent(this, HistoryActivity.class);
            canOpen = true;
        } else if (jenisActivity == ACT_SERTIFIKASI) {
            intent = new Intent(this, SertifikasiActivity.class);
            canOpen = wsh.isSafe();
        } else if (jenisActivity == ACT_DOCUMENT) {
            intent = new Intent(this, DokumenActivity.class);
          canOpen = wsh.isSafe();
        } else if (jenisActivity == ACT_DESKTOP) {
          canOpen = true;
            intent = new Intent(this, DesktopActivity.class);
        } else if (jenisActivity == ACT_TAGIHAN) {
          canOpen = true;
            intent = new Intent(this, BillActivity.class);
        } else if (jenisActivity == ACT_ABSENSI) {
          canOpen = true;
            intent = new Intent(this, AttendanceActivity.class);
        } else if (jenisActivity == ACT_KELAS) {
          canOpen = wsh.isSafe();
          intent = new Intent(this, KelasActivity.class);
      }

        if (intent != null && hasInternetConnection && canOpen) {

            // only if the settings was toggled ON
            if(UserData.getPreferenceBoolean(CLICK_SOUNDS)) {
                AudioPlayer.play(this, AudioPlayer.CLICK_SOUND);
            }

                startActivity(intent);

                // HomeActivity ended here and will be re-created from UserProfile
                // or from Settings activities
                // meanwhile other activities just no need to recreate
                if(needRefreshLater) {
                    finish();
                }

        }else{
            // if no internet
            // thus, we dont go anywhere
            if(hasInternetConnection==false) {
                warningNoInternet();
            }

            if(!canOpen){

                // only if the settings was toggled ON
                if(UserData.getPreferenceBoolean(CLICK_SOUNDS)) {
                    AudioPlayer.play(this, AudioPlayer.FAIL);
                }

                ShowDialog.message(this, "No access!");
            }
        }

    }

    public void openDocument(View v) {
        nextActivity(ACT_DOCUMENT);
    }

    public void openTagihan(View v) {
        nextActivity(ACT_TAGIHAN);
    }

    public void openAbsensi(View v) {
        nextActivity(ACT_ABSENSI);
    }

    public void openUserProfile(View v) {
        nextActivity(ACT_USER_PROFILE);
    }

    public void openOptions(View v) {
        nextActivity(ACT_OPTIONS);
    }

    public void openKelas(View v) {
        nextActivity(ACT_KELAS);
    }

    public void openHistory(View v) {
        nextActivity(ACT_HISTORY);
    }

    public void openDesktop(View v) {
        nextActivity(ACT_DESKTOP);
    }

    public void openSertifikasi(View v) {
            nextActivity(ACT_SERTIFIKASI);

    }


}