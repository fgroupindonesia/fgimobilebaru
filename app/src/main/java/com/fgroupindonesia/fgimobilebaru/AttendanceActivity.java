package com.fgroupindonesia.fgimobilebaru;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.fgroupindonesia.fgimobilebaru.helper.HistoryHelper;
import com.fgroupindonesia.fgimobilebaru.helper.ImageHelper;
import com.fgroupindonesia.fgimobilebaru.helper.Keys;
import com.fgroupindonesia.fgimobilebaru.helper.Navigator;
import com.fgroupindonesia.fgimobilebaru.helper.RespondHelper;
import com.fgroupindonesia.fgimobilebaru.helper.ShowDialog;
import com.fgroupindonesia.fgimobilebaru.helper.UIHelper;
import com.fgroupindonesia.fgimobilebaru.helper.URLReference;
import com.fgroupindonesia.fgimobilebaru.helper.WebRequest;
import com.fgroupindonesia.fgimobilebaru.helper.shared.UserData;
import com.fgroupindonesia.fgimobilebaru.object.Attendance;
import com.fgroupindonesia.fgimobilebaru.object.Schedule;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AttendanceActivity extends AppCompatActivity implements Navigator {

    TableLayout tableLayoutAttendance;

    LinearLayout linearLayoutAbsensi, linearLayoutTandaTangan;
    ArrayList<Attendance> dataAttendance = new ArrayList<Attendance>();
    TextView textViewKeseluruhanDataAbsensi;
    ProgressBar progressBarAttendance;
    Button buttonAttendanceRefresh, buttonAttendanceParaf,
            buttonAttendanceTakHadir, buttonSaveTandaTangan, buttonClearTandaTangan;
    SignaturePad mSignaturePad;

    String usName, token, statusKehadiran, kelasSaatIni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        // for shared preference usage
        UserData.setPreference(this);

        usName = UserData.getPreferenceString(Keys.USERNAME);
        token = UserData.getPreferenceString(Keys.TOKEN);

        linearLayoutAbsensi = (LinearLayout) findViewById(R.id.linearLayoutAbsensi);
        linearLayoutTandaTangan = (LinearLayout) findViewById(R.id.linearLayoutTandaTangan);

        buttonAttendanceTakHadir = (Button) findViewById(R.id.buttonAttendanceTakHadir);
        buttonAttendanceParaf = (Button) findViewById(R.id.buttonAttendanceParaf);
        buttonSaveTandaTangan = (Button) findViewById(R.id.buttonSaveTtd);
        buttonClearTandaTangan = (Button) findViewById(R.id.buttonClearTtd);

        textViewKeseluruhanDataAbsensi = (TextView) findViewById(R.id.textViewKeseluruhanDataAbsensi);
        tableLayoutAttendance = (TableLayout) findViewById(R.id.tableLayoutAttendance);

        progressBarAttendance = (ProgressBar) findViewById(R.id.progressBarAttendance);
        buttonAttendanceRefresh = (Button) findViewById(R.id.buttonAttendanceRefresh);

        // for the first time because it's not the scheduled yet
        // then we hide the button temporarily
        buttonAttendanceParaf.setVisibility(View.GONE);
        buttonAttendanceTakHadir.setVisibility(View.GONE);

        // hide the signature pad yet
        // later it will be turned on once the button Signature clicked
        showSignaturePad(false);

        // preparing signaturepad
        mSignaturePad = (SignaturePad) findViewById(R.id.signature_pad);

        // calling to Server API for this username
        callDataAttendance();

        // check schedule is it for today?
        // and hasn't been signed?
        callDataSchedule();

    }


    public  void callDataSchedule(){

        // only if the activity and navigator are predefined earlier

        WebRequest httpCall = new WebRequest(AttendanceActivity.this, this);

        httpCall.addData("username", UserData.getPreferenceString(Keys.USERNAME));
        httpCall.addData("token", UserData.getPreferenceString(Keys.TOKEN));
        httpCall.setWaitState(true);
        httpCall.setRequestMethod(WebRequest.POST_METHOD);
        httpCall.setTargetURL(URLReference.ScheduleAll);
        httpCall.execute();

        //    ShowDialog.message(this, URLReference.AttendanceAll);
    }

    public  void callDataAttendance(){

        // only if the activity and navigator are predefined earlier

        WebRequest httpCall = new WebRequest(AttendanceActivity.this, this);

        httpCall.addData("username", UserData.getPreferenceString(Keys.USERNAME));
        httpCall.addData("token", UserData.getPreferenceString(Keys.TOKEN));
        httpCall.setWaitState(true);
        httpCall.setRequestMethod(WebRequest.POST_METHOD);
        httpCall.setTargetURL(URLReference.AttendanceAll);
        httpCall.execute();

        //    ShowDialog.message(this, URLReference.AttendanceAll);
    }

    private void showSignaturePad(boolean b){
        if(b){
            linearLayoutTandaTangan.setVisibility(View.VISIBLE);
            linearLayoutAbsensi.setVisibility(View.GONE);
        }else {
            linearLayoutTandaTangan.setVisibility(View.GONE);
            linearLayoutAbsensi.setVisibility(View.VISIBLE);
        }
    }

    private void showLoading(boolean b){
        if(b) {
            progressBarAttendance.setVisibility(View.VISIBLE);
            buttonAttendanceRefresh.setVisibility(View.GONE);
        }else{
            progressBarAttendance.setVisibility(View.GONE);
            buttonAttendanceRefresh.setVisibility(View.VISIBLE);
        }
    }

    public void showFilterAttendance(View v){
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Filter By");
        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.filter_attendance, null);
        builder.setView(customLayout);
        // add a button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Spinner spinnerMonth = customLayout.findViewById(R.id.spinnerMonthFilter);
                Spinner spinnerStatus = customLayout.findViewById(R.id.spinnerStatusFilter);

                showDataByFilter(spinnerMonth, spinnerStatus);
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void clearAllRows(){

        // all data Row are cleared except the last (top) header at index-0th position.
        int count = tableLayoutAttendance.getChildCount();
        for (int i = count; i != 0; i--) {
            View child = tableLayoutAttendance.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }

    }



    private void showDataByFilter(Spinner month, Spinner stat){

        String monthMode = month.getSelectedItem().toString().toLowerCase();
        String statMode = stat.getSelectedItem().toString().toLowerCase();
        int manyDataFound = 0;

        try {

        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

        String thnBlnIni = dateFormat.format(today);

        int tahunIni = Integer.parseInt(thnBlnIni.split("-")[0]);
        int bulanDicari = Integer.parseInt(thnBlnIni.split("-")[1]);

        // 1 bulan lalu
        if(monthMode.contains("lalu")){
            bulanDicari--;
        }

        //ShowDialog.message(this, "filtering bulan " + bulanDicari + " untuk " + statMode);

        clearAllRows();

            for (Attendance dataKehadiran : dataAttendance) {
                String tanggalNa = dataKehadiran.getDate_created();
                // yyyy-MM-dd HH:mm:ss

                String thnString = tanggalNa.split("-")[0];
                String blnString =  tanggalNa.split("-")[1];
                int blnAngka = Integer.parseInt(blnString);
                int thnAngka = Integer.parseInt(thnString);

                boolean tglCocok = false;
                boolean statusCocok = false;
                // we show all data if 'semua' is selected
                // semua for bulan ini
               if (dataKehadiran.getStatus().equalsIgnoreCase(statMode) && blnAngka == bulanDicari && tahunIni == thnAngka){
                    tglCocok = true;
                    statusCocok = true;
                } else if(statMode.equalsIgnoreCase("semua") && blnAngka == bulanDicari && tahunIni == thnAngka){
                   tglCocok = true;
                   statusCocok = true;
               }

                if(tglCocok && statusCocok){
                    addingDataRow(dataKehadiran);
                    manyDataFound++;
                }
            }

        } catch (Exception ex){
            ShowDialog.message(this, "error while filtering data..." + ex.getMessage());
        }

        // show dummy empty not available data
        if(manyDataFound == 0){
            Attendance dummy = new Attendance();
            dummy.setClass_registered("not available");
            dummy.setStatus("-");
            dummy.setDate_created(null);
            addingDataRow(dummy);
            ShowDialog.message(this, "Tidak ditemukan data yang cocok!" );
            textViewKeseluruhanDataAbsensi.setText("Data absensi setelah Filtering : 0 data.");
        }else {
            textViewKeseluruhanDataAbsensi.setText("Data absensi setelah Filtering : " + manyDataFound +" data.");
        }

    }

    private void prepareSignaturePad(){

        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
                //generating the date & time signed in
                buttonSaveTandaTangan.setEnabled(true);
                buttonClearTandaTangan.setEnabled(true);
            }

            @Override
            public void onSigned() {
                //Event triggered when the pad is signed

            }

            @Override
            public void onClear() {
                //Event triggered when the pad is cleared
                buttonSaveTandaTangan.setEnabled(false);
                buttonClearTandaTangan.setEnabled(false);
            }
        });
    }

    public void clearPad(View v){
        mSignaturePad.clear();
    }

    public void savePad(View v){
        Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
        if (ImageHelper.addJpgSignatureToGallery(signatureBitmap, this)) {
           ShowDialog.message(this, "Tanda tangan absensi berhasil tersimpan!");
           showSignaturePad(false);

            statusKehadiran = "hadir";
           saveDataAttendance();
        } else {
            ShowDialog.message(this, "Tanda tangan error, harap ulangi lagi!");
            finish();
        }

    }

    public void attendanceParaf(View v){

        prepareSignaturePad();
        showSignaturePad(true);

    }

    public void attendanceTakHadir(View v){
        // show the dialog kenapa anda tidak hadir?
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Kenapa anda tidak hadir?");
        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.opsi_tak_hadir, null);
        builder.setView(customLayout);
        // add a button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Spinner spinnerStatus = customLayout.findViewById(R.id.spinnerAlasan);

                statusKehadiran = spinnerStatus.getSelectedItem().toString();

                // save the attendance directly to webserver
                saveDataAttendance();


            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void saveDataAttendance(){

            WebRequest httpCall = new WebRequest(this, this);

            httpCall.addData("username", usName);
            httpCall.addData("token", token);
            httpCall.addData("class_registered", kelasSaatIni);
            httpCall.addData("status", statusKehadiran);

            // we need to wait for the response
            httpCall.setWaitState(true);
            httpCall.setRequestMethod(WebRequest.POST_METHOD);
            httpCall.setTargetURL(URLReference.AttendanceAdd);
            httpCall.execute();

    }


    public void refreshData(View v){
        showLoading(true);
        clearAllRows();
        dataAttendance.clear();

        textViewKeseluruhanDataAbsensi.setText("Keseluruhan data absensi : 0 data.");
        // calling to Server API for this username
        callDataAttendance();
    }

    private boolean isTimeEligilbe(String timeIn){

        // timeIN is using this format HH:mm

        boolean eligible = false;

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date dNow = new Date();

        String timeNow = sdf.format(dNow);

        int hourNow = Integer.parseInt(timeNow.split(":")[0]);
        int hourSchedule = Integer.parseInt(timeIn.split(":")[0]);

        ShowDialog.message(this, "jam sekarang " + hourNow + " sementara jam dia " + hourSchedule);

        // the eligible time is 1 hour only but not more
        if(hourNow < hourSchedule){
            eligible = false;
        }else if(hourNow >= hourSchedule && hourNow <= hourSchedule+1){
            eligible = true;
        }

        return eligible;
    }

    private boolean isTodaySchedules(Schedule [] dataIn){

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

    private void addRecordHistory(String status){
        HistoryHelper hper = new HistoryHelper();
        hper.updateAttendance(usName, token, status, this);
    }

    @Override
    public void nextActivity() {
        ShowDialog.message(this,"Absensi hari ini sudah terupdate!");
        finish();
    }

    @Override
    public void onSuccess(String urlTarget, String respond) {

        try {

            if (RespondHelper.isValidRespond(respond)) {

                JsonParser parser = new JsonParser();
                Gson gson = new Gson();

                if(urlTarget.contains(URLReference.AttendanceAll)) {

                    JSONArray jsons = RespondHelper.getArray(respond, "multi_data");

                    JsonElement mJson = parser.parse(jsons.toString());

                    Attendance object[] = gson.fromJson(mJson, Attendance[].class);

                    textViewKeseluruhanDataAbsensi.setText("Keseluruhan data absensi : " + object.length + " data.");

                    //ShowDialog.message(this, "we got " + jsons.toString());
                    showLoading(false);

                    for (Attendance single : object) {
                        addingDataRow(single);
                        dataAttendance.add(single);
                    }

                } else  if(urlTarget.contains(URLReference.ScheduleAll)) {

                    JSONArray jsons = RespondHelper.getArray(respond, "multi_data");

                    JsonElement mJson = parser.parse(jsons.toString());

                    Schedule objects[] = gson.fromJson(mJson, Schedule[].class);

                    // check first is it eligible for today for signing in?
                   if( isTodaySchedules(objects)) {
                       // activate the button
                       buttonAttendanceParaf.setVisibility(View.VISIBLE);
                       buttonAttendanceTakHadir.setVisibility(View.VISIBLE);
                   }

                } else if(urlTarget.contains(URLReference.AttendanceAdd)){
                    // this person has updating the attendance
                    // now save the history into the server
                    addRecordHistory(statusKehadiran);

                } else if(urlTarget.contains(URLReference.HistoryAdd)){
                    // ended
                  nextActivity();

                }


                }

        } catch (Exception ex) {
            ShowDialog.message(this, "error at "  + ex.getMessage());
        }

    }


    private TextView createTextView(String mess){


        TextView el = new TextView(this);
        el.setText(mess);
        el.setGravity(Gravity.CENTER);
        //el.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));;

        return el;

    }


    private void addingDataRow(Attendance dataIn){

        TableRow tr = new TableRow(this);

        TableRow.LayoutParams trLayout = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        String dayName, dateIndo;

        if(dataIn.getDate_created()!=null){
            dayName = UIHelper.convertDayName(dataIn.getDate_created());
            dateIndo = UIHelper.convertDateToIndonesia(dataIn.getDate_created());

        }else{

            dayName = "-";
            dateIndo = "-";

        }

        TextView dataText1 = createTextView(dataIn.getClass_registered());
        TextView dataText2 = createTextView(dataIn.getStatus());
        TextView dataText3 = createTextView(dayName);
        TextView dataText4 = createTextView(dateIndo);

        tr.addView(dataText1, trLayout );
        tr.addView(dataText2, trLayout);
        tr.addView(dataText3, trLayout);
        tr.addView(dataText4, trLayout);

        tableLayoutAttendance.addView(tr);
    }

}