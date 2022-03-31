package com.fgroupindonesia.fgimobilebaru;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.fgroupindonesia.fgimobilebaru.helper.ScheduleChecker;
import com.fgroupindonesia.fgimobilebaru.helper.ShowDialog;
import com.fgroupindonesia.fgimobilebaru.helper.UIHelper;
import com.fgroupindonesia.fgimobilebaru.helper.URLReference;
import com.fgroupindonesia.fgimobilebaru.helper.WarningStatusHelper;
import com.fgroupindonesia.fgimobilebaru.helper.WebRequest;
import com.fgroupindonesia.fgimobilebaru.helper.shared.UserData;
import com.fgroupindonesia.fgimobilebaru.object.Attendance;
import com.fgroupindonesia.fgimobilebaru.object.Schedule;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    String usName, token, statusKehadiran, kelasSaatIni,
    // used for storing image signature
    picturePath;

    // this is used for later if he wants to sign multidates
    ImageView imageViewDeleteDate1, imageViewDeleteDate2, imageViewDeleteDate3,
            imageViewDeleteDate4, imageViewDeleteDate5, imageViewDeleteDate6;
    TableRow tableRow1, tableRow2, tableRow3, tableRow4, tableRow5, tableRow6;
    EditText editTextDateMulti1, editTextDateMulti2, editTextDateMulti3,
            editTextDateMulti4, editTextDateMulti5, editTextDateMulti6;

    int totalDataSubmitted, warn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        // for shared preference usage
        UserData.setPreference(this);

        usName = UserData.getPreferenceString(Keys.USERNAME);
        token = UserData.getPreferenceString(Keys.TOKEN);
        warn = UserData.getPreferenceInt(Keys.WARNING_STATUS);

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


    public void callDataSchedule() {

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

    public void callDataAttendance() {

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

    private void showSignaturePad(boolean b) {
        if (b) {
            linearLayoutTandaTangan.setVisibility(View.VISIBLE);
            linearLayoutAbsensi.setVisibility(View.GONE);
        } else {
            linearLayoutTandaTangan.setVisibility(View.GONE);
            linearLayoutAbsensi.setVisibility(View.VISIBLE);
        }
    }

    private void showLoading(boolean b) {
        if (b) {
            progressBarAttendance.setVisibility(View.VISIBLE);
            buttonAttendanceRefresh.setVisibility(View.GONE);
        } else {
            progressBarAttendance.setVisibility(View.GONE);
            buttonAttendanceRefresh.setVisibility(View.VISIBLE);
        }
    }

    View layoutFilter;
    public void showFilterAttendance(View v) {
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Filter By");
        // set the custom layout
        layoutFilter = getLayoutInflater().inflate(R.layout.filter_attendance, null);
        builder.setView(layoutFilter);
        // add a button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Spinner spinnerMonth = layoutFilter.findViewById(R.id.spinnerMonthFilter);
                Spinner spinnerStatus = layoutFilter.findViewById(R.id.spinnerStatusFilter);

                showDataByFilter(spinnerMonth, spinnerStatus);
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void createOnClickDeleteDate(View rootView) {

        imageViewDeleteDate1 = (ImageView) rootView.findViewById(R.id.imageViewDateMulti1);
        imageViewDeleteDate2 = (ImageView) rootView.findViewById(R.id.imageViewDateMulti2);
        imageViewDeleteDate3 = (ImageView) rootView.findViewById(R.id.imageViewDateMulti3);
        imageViewDeleteDate4 = (ImageView) rootView.findViewById(R.id.imageViewDateMulti4);
        imageViewDeleteDate5 = (ImageView) rootView.findViewById(R.id.imageViewDateMulti5);
        imageViewDeleteDate6 = (ImageView) rootView.findViewById(R.id.imageViewDateMulti6);

        tableRow1 = (TableRow) rootView.findViewById(R.id.tableRowDateMulti1);
        tableRow2 = (TableRow) rootView.findViewById(R.id.tableRowDateMulti2);
        tableRow3 = (TableRow) rootView.findViewById(R.id.tableRowDateMulti3);
        tableRow4 = (TableRow) rootView.findViewById(R.id.tableRowDateMulti4);
        tableRow5 = (TableRow) rootView.findViewById(R.id.tableRowDateMulti5);
        tableRow6 = (TableRow) rootView.findViewById(R.id.tableRowDateMulti6);

        editTextDateMulti1 = (EditText) rootView.findViewById(R.id.editTextDateMulti1);
        editTextDateMulti2 = (EditText) rootView.findViewById(R.id.editTextDateMulti2);
        editTextDateMulti3 = (EditText) rootView.findViewById(R.id.editTextDateMulti3);
        editTextDateMulti4 = (EditText) rootView.findViewById(R.id.editTextDateMulti4);
        editTextDateMulti5 = (EditText) rootView.findViewById(R.id.editTextDateMulti5);
        editTextDateMulti6 = (EditText) rootView.findViewById(R.id.editTextDateMulti6);

        setOnClickDelete(imageViewDeleteDate1);
        setOnClickDelete(imageViewDeleteDate2);
        setOnClickDelete(imageViewDeleteDate3);
        setOnClickDelete(imageViewDeleteDate4);
        setOnClickDelete(imageViewDeleteDate5);
        setOnClickDelete(imageViewDeleteDate6);

        setOnClickDate(editTextDateMulti1);
        setOnClickDate(editTextDateMulti2);
        setOnClickDate(editTextDateMulti3);
        setOnClickDate(editTextDateMulti4);
        setOnClickDate(editTextDateMulti5);
        setOnClickDate(editTextDateMulti6);

    }

    private void showDatePicker(final EditText edt) {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AttendanceActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String dateIn = dayOfMonth + "-" + UIHelper.convertMonthToIndonesia(monthOfYear + 1) + "-" + year;
                        edt.setText(dateIn);

                        // set background color to none if this is a new date
                        edt.setBackgroundColor(0);
                        if (ensureDuplicateDates(dateIn)) {
                            edt.setBackgroundColor(Color.RED);
                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void setOnClickDate(final EditText edt) {

        edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    showDatePicker(edt);
                }
            }
        });

        edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker((EditText) v);
            }
        });


    }

    private boolean ensureDuplicateDates(String dateCompared) {

        boolean foundYes = false;

        for (String dateCheck : multiDateEntries) {
            if (dateCheck.equalsIgnoreCase(dateCompared)) {
                foundYes = true;
                break;
            }
        }

        return foundYes;
    }

    private void setOnClickDelete(ImageView img) {

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int post = Integer.parseInt(v.getTag().toString());
                hideMyDateField(post);
            }
        });

    }

    // clearning from arraylist
    private void clearMultiDateEntry(String dateIn) {
        int post = -1, itu = 0;

        for (String dataSatuan : multiDateEntries) {
            if (dateIn.equalsIgnoreCase(dataSatuan)) {
                post = itu;
                break;
            }

            itu++;
        }

        if (post != -1) {
            // remove the item from arraylist
            multiDateEntries.remove(post);
        }

    }

    private void hideMyDateField(int posisi) {
        if (posisi == 1) {
            tableRow2.setVisibility(View.GONE);
            clearMultiDateEntry(UIHelper.getText(editTextDateMulti2));
            editTextDateMulti2.setText("");
        } else if (posisi == 2) {
            tableRow3.setVisibility(View.GONE);
            clearMultiDateEntry(UIHelper.getText(editTextDateMulti3));
            editTextDateMulti3.setText("");
        } else if (posisi == 3) {
            tableRow4.setVisibility(View.GONE);
            clearMultiDateEntry(UIHelper.getText(editTextDateMulti4));
            editTextDateMulti4.setText("");
        } else if (posisi == 4) {
            tableRow5.setVisibility(View.GONE);
            clearMultiDateEntry(UIHelper.getText(editTextDateMulti5));
            editTextDateMulti5.setText("");
        } else if (posisi == 5) {
            tableRow6.setVisibility(View.GONE);
            clearMultiDateEntry(UIHelper.getText(editTextDateMulti6));
            editTextDateMulti6.setText("");
        }

        // when sombody click the removal
        // so the indexes need to be hide too backward
        numMultiDate--;
    }

    private void showNextDateField(int posisi) {

        if (posisi == 1) {
            tableRow2.setVisibility(View.VISIBLE);
            editTextDateMulti2.requestFocus();
        } else if (posisi == 2) {
            tableRow3.setVisibility(View.VISIBLE);
            editTextDateMulti3.requestFocus();
        } else if (posisi == 3) {
            tableRow4.setVisibility(View.VISIBLE);
            editTextDateMulti4.requestFocus();
        } else if (posisi == 4) {
            tableRow5.setVisibility(View.VISIBLE);
            editTextDateMulti5.requestFocus();
        } else if (posisi == 5) {
            tableRow6.setVisibility(View.VISIBLE);
            editTextDateMulti6.requestFocus();
        }

    }

    View layoutMultiAttendance;
    int numMultiDate = 0;
    // for storing info about the date of multi-signature
    ArrayList<String> multiDateEntries;

    private void grabAllDataMultiDateIntoList() {

        if (tableRow6.getVisibility() == View.VISIBLE) {
            multiDateEntries.add(UIHelper.getText(editTextDateMulti6));
         //   ShowDialog.message(this, "added " + UIHelper.getText(editTextDateMulti6));
        }

        if (tableRow5.getVisibility() == View.VISIBLE) {
            multiDateEntries.add(UIHelper.getText(editTextDateMulti5));
         //   ShowDialog.message(this, "added " + UIHelper.getText(editTextDateMulti5));
        }

        if (tableRow4.getVisibility() == View.VISIBLE) {
            multiDateEntries.add(UIHelper.getText(editTextDateMulti4));
         //   ShowDialog.message(this, "added " + UIHelper.getText(editTextDateMulti4));
        }

        if (tableRow3.getVisibility() == View.VISIBLE) {
            multiDateEntries.add(UIHelper.getText(editTextDateMulti3));
          //  ShowDialog.message(this, "added " + UIHelper.getText(editTextDateMulti3));

        }

        if (tableRow2.getVisibility() == View.VISIBLE) {
            multiDateEntries.add(UIHelper.getText(editTextDateMulti2));
          //  ShowDialog.message(this, "added " + UIHelper.getText(editTextDateMulti2));

        }

        if (tableRow1.getVisibility() == View.VISIBLE) {
            multiDateEntries.add(UIHelper.getText(editTextDateMulti1));
           // ShowDialog.message(this, "added " + UIHelper.getText(editTextDateMulti1));

        }

    }

    private void showMultiAttendance() {

        multiDateEntries = new ArrayList<String>();

        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Multi Attendance");
        // set the custom layout
        layoutMultiAttendance = getLayoutInflater().inflate(R.layout.multi_attendance, null);
        builder.setView(layoutMultiAttendance);

        createOnClickDeleteDate(layoutMultiAttendance);

        ImageView imageViewAddAttendanceMulti = (ImageView) layoutMultiAttendance.findViewById(R.id.imageViewAddAttendanceMulti);
        imageViewAddAttendanceMulti.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showNextDateField(numMultiDate);
                if (numMultiDate != 5) {
                    numMultiDate++;
                }
            }
        });

        // add a button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // grabbing all the dates to be posted later
                // put into arraylist
                grabAllDataMultiDateIntoList();

               // ShowDialog.message(AttendanceActivity.this, "mengupdate data absensi...");
                dialog.dismiss();

                saveDataAttendance();

            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();

                // clear the item as well
                multiDateEntries.clear();
               // ShowDialog.message(AttendanceActivity.this, "data cleared!!");
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void clearAllRows() {

        // all data Row are cleared except the last (top) header at index-0th position.
        int count = tableLayoutAttendance.getChildCount();
        for (int i = count; i != 0; i--) {
            View child = tableLayoutAttendance.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }

    }


    private void showDataByFilter(Spinner month, Spinner stat) {

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
            if (monthMode.contains("lalu")) {
                bulanDicari--;
            }

            //ShowDialog.message(this, "filtering bulan " + bulanDicari + " untuk " + statMode);

            clearAllRows();

            for (Attendance dataKehadiran : dataAttendance) {
                String tanggalNa = dataKehadiran.getDate_created();
                // yyyy-MM-dd HH:mm:ss

                String thnString = tanggalNa.split("-")[0];
                String blnString = tanggalNa.split("-")[1];
                int blnAngka = Integer.parseInt(blnString);
                int thnAngka = Integer.parseInt(thnString);

                boolean tglCocok = false;
                boolean statusCocok = false;
                // we show all data if 'semua' is selected
                // semua for bulan ini
                if (dataKehadiran.getStatus().equalsIgnoreCase(statMode) && blnAngka == bulanDicari && tahunIni == thnAngka) {
                    tglCocok = true;
                    statusCocok = true;
                } else if (statMode.equalsIgnoreCase("semua") && blnAngka == bulanDicari && tahunIni == thnAngka) {
                    tglCocok = true;
                    statusCocok = true;
                }

                if (tglCocok && statusCocok) {
                    addingDataRow(dataKehadiran);
                    manyDataFound++;
                }
            }

        } catch (Exception ex) {
            ShowDialog.message(this, "error while filtering data..." + ex.getMessage());
        }

        // show dummy empty not available data
        if (manyDataFound == 0) {
            Attendance dummy = new Attendance();
            dummy.setClass_registered("not available");
            dummy.setStatus("-");
            dummy.setDate_created(null);
            addingDataRow(dummy);
            ShowDialog.message(this, "Tidak ditemukan data yang cocok!");
            textViewKeseluruhanDataAbsensi.setText("Data absensi setelah Filtering : 0 data.");
        } else {
            textViewKeseluruhanDataAbsensi.setText("Data absensi setelah Filtering : " + manyDataFound + " data.");
        }

    }

    private void prepareSignaturePad() {

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

    public void clearPad(View v) {
        mSignaturePad.clear();
    }


   WarningStatusHelper wsh;

    public void savePad(View v) {
        Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
        if (ImageHelper.addJpgSignatureToGallery(signatureBitmap, this)) {
            //ShowDialog.message(this, "Tanda tangan absensi berhasil tersimpan!");
            showSignaturePad(false);

            picturePath = ImageHelper.getSignaturePath();

            statusKehadiran = "hadir";

            wsh = new WarningStatusHelper(warn);
            if(wsh.getStatus().equals(Keys.STATUS_WARN_MULTI_ATTENDANCE)){
                // if has problem so he need to filling the multi attendance
                showMultiAttendance();
            }else {
                // if no problem directly saving
                saveDataAttendance();
            }

        } else {
            ShowDialog.message(this, "Tanda tangan error, harap ulangi lagi! ");
            finish();
        }

    }

    public void attendanceParaf(View v) {

        prepareSignaturePad();
        showSignaturePad(true);

    }

    View layoutOpsiTakHadir;
    public void attendanceTakHadir(View v) {
        // show the dialog kenapa anda tidak hadir?
        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Kenapa anda tidak hadir?");
        // set the custom layout
        layoutOpsiTakHadir = getLayoutInflater().inflate(R.layout.opsi_tak_hadir, null);
        builder.setView(layoutOpsiTakHadir);
        // add a button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Spinner spinnerStatus = layoutOpsiTakHadir.findViewById(R.id.spinnerAlasan);

                statusKehadiran = spinnerStatus.getSelectedItem().toString();

                // save the attendance directly to webserver
               // ShowDialog.message(AttendanceActivity.this, "sending " + statusKehadiran);
                saveDataAttendance();


            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    private void saveDataAttendance(String tglMasuk) {
        WebRequest httpCall = new WebRequest(this, this);

        // used as areference for multisignature filling
        if (tglMasuk != null) {
            // early data is dd-MMM-yyyy
            tglMasuk = UIHelper.convertDateToEnglish(tglMasuk);
            httpCall.addData("previous_date", tglMasuk);

        } else {
            // when tglMasuk is null
            // so this is the latest or current signature
            // for uploading image
            if (picturePath != null) {
                httpCall.setMultipartform(true);
               // ShowDialog.message(this, "data is " + picturePath);
                httpCall.addFile("signature", new File(picturePath));
            }
        }


        httpCall.addData("username", usName);
        httpCall.addData("token", token);
        httpCall.addData("class_registered", kelasSaatIni);
        httpCall.addData("status", statusKehadiran);

       // ShowDialog.message(this, "us " + usName + " tok" + token);
       // ShowDialog.message(this, "class " + kelasSaatIni + " stat " + statusKehadiran);

        // we need to wait for the response
        httpCall.setWaitState(true);
        httpCall.setRequestMethod(WebRequest.POST_METHOD);
        httpCall.setTargetURL(URLReference.AttendanceAdd);
        httpCall.execute();

        totalDataSubmitted++;
        //ShowDialog.message(this, "mau kirim " + x + " " + tglMasuk);
    }

    private void saveDataAttendance() {

        // if there is a multisignature as he wants to sign previous days
        if (multiDateEntries != null ) {

            // go with the multisubmission
            // date format need to be converted later
            for (String tgl : multiDateEntries) {
                saveDataAttendance(tgl);
            }

        }

        // last submission is the current signature without the previous date
        // tglMasuk is actually : previous date

            saveDataAttendance(null);

    }


    public void refreshData(View v) {
        showLoading(true);
        clearAllRows();
        dataAttendance.clear();

        textViewKeseluruhanDataAbsensi.setText("Keseluruhan data absensi : 0 data.");
        // calling to Server API for this username
        callDataAttendance();
    }


    private void addRecordHistory(String status) {
        HistoryHelper hper = new HistoryHelper();
        hper.updateAttendance(usName, token, status, this);
    }

    @Override
    public void nextActivity() {
        ShowDialog.message(this, "Absensi hari ini sudah terupdate!");
        finish();
    }

    @Override
    public void onFailed(){
        // usually because no internet

    }

    @Override
    public void onSuccess(String urlTarget, String respond) {

        try {

            if (RespondHelper.isValidRespond(respond)) {

                JsonParser parser = new JsonParser();
                Gson gson = new Gson();

                if (urlTarget.contains(URLReference.AttendanceAll)) {

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

                } else if (urlTarget.contains(URLReference.ScheduleAll)) {

                    JSONArray jsons = RespondHelper.getArray(respond, "multi_data");

                    JsonElement mJson = parser.parse(jsons.toString());

                    Schedule objects[] = gson.fromJson(mJson, Schedule[].class);

                    ScheduleChecker engineChecker = new ScheduleChecker(this);

                    // check first is it eligible for today for signing in?
                    if (engineChecker.isTodaySchedules(objects)) {
                        // activate the button
                        // store also the data of class name
                        // take from the previously saved entry
                        kelasSaatIni = engineChecker.getCurrentClass();

                        UserData.savePreference(Keys.CLASS_REGISTERED, kelasSaatIni);

                        buttonAttendanceParaf.setVisibility(View.VISIBLE);
                        buttonAttendanceTakHadir.setVisibility(View.VISIBLE);
                    }

                } else if (urlTarget.contains(URLReference.AttendanceAdd)) {
                    // this person has updating the attendance
                    // now save the history into the server
                    // if the totaldata submitted is matched including the
                    // previous date along with the current signature then
                    // we shall proceed with the history
                    if(totalDataSubmitted == multiDateEntries.size()+1) {
                        addRecordHistory(statusKehadiran);

                        // update the warning status to be off
                        UserData.savePreference(Keys.WARNING_STATUS, Keys.STATUS_WARN_OFF);
                    }

                } else if (urlTarget.contains(URLReference.HistoryAdd)) {
                    // ended
                    nextActivity();

                }


            } else {
                // when invalid means no attendance yet
                showLoading(false);
            }

        } catch (Exception ex) {
            ShowDialog.message(this, "Error at " + ex.getMessage());
        }

    }


    private TextView createTextView(String mess) {


        TextView el = new TextView(this);
        el.setText(mess);
        el.setGravity(Gravity.CENTER);
        //el.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));;

        return el;

    }


    private void addingDataRow(Attendance dataIn) {

        TableRow tr = new TableRow(this);

        TableRow.LayoutParams trLayout = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
        String dayName, dateIndo;

        if (dataIn.getDate_created() != null) {
            dayName = UIHelper.convertDayName(dataIn.getDate_created());
            dateIndo = UIHelper.convertDateToIndonesia(dataIn.getDate_created());

        } else {

            dayName = "-";
            dateIndo = "-";

        }

        TextView dataText1 = createTextView(dataIn.getClass_registered());
        TextView dataText2 = createTextView(dataIn.getStatus());
        TextView dataText3 = createTextView(dayName);
        TextView dataText4 = createTextView(dateIndo);

        tr.addView(dataText1, trLayout);
        tr.addView(dataText2, trLayout);
        tr.addView(dataText3, trLayout);
        tr.addView(dataText4, trLayout);

        tableLayoutAttendance.addView(tr);
    }

}