package com.fgroupindonesia.fgimobilebaru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fgroupindonesia.fgimobilebaru.helper.ArrayHelper;
import com.fgroupindonesia.fgimobilebaru.helper.Keys;
import com.fgroupindonesia.fgimobilebaru.helper.Navigator;
import com.fgroupindonesia.fgimobilebaru.helper.NavigatorFetch;
import com.fgroupindonesia.fgimobilebaru.helper.RespondHelper;
import com.fgroupindonesia.fgimobilebaru.helper.ShowDialog;
import com.fgroupindonesia.fgimobilebaru.helper.URLReference;
import com.fgroupindonesia.fgimobilebaru.helper.WebFetch;
import com.fgroupindonesia.fgimobilebaru.helper.WebRequest;
import com.fgroupindonesia.fgimobilebaru.helper.WhatsappSender;
import com.fgroupindonesia.fgimobilebaru.helper.adapter.SertifikatArrayAdapter;
import com.fgroupindonesia.fgimobilebaru.helper.shared.UserData;
import com.fgroupindonesia.fgimobilebaru.object.Sertifikat;
import com.google.gson.Gson;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;

import java.io.File;
import java.util.ArrayList;

public  class SertifikasiActivity extends AppCompatActivity implements Navigator, NavigatorFetch {

    private Fetch fetch;
    TextView textViewSertifikatTotal;
    ListView listViewDocument;
    SertifikatArrayAdapter arrayDocAdapter;
    ArrayList<Sertifikat> dataDocuments = new ArrayList<Sertifikat>();
    ArrayList<Sertifikat> dataTemp = new ArrayList<Sertifikat>();

    LinearLayout linearLayoutLoading, linearLayoutSertifikat, linearLayoutNoEntry;
    String usName, aToken, urlDownload, fileName;

    // for several download ProgressBars
    ProgressBar prgBar01, prgBar02;
    ImageView imageAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sertifikasi);

        // for shared preference usage
        UserData.setPreference(this);

        usName = UserData.getPreferenceString(Keys.USERNAME);
        aToken = UserData.getPreferenceString(Keys.TOKEN);

        linearLayoutLoading = (LinearLayout) findViewById(R.id.linearLayoutLoading);
        linearLayoutSertifikat = (LinearLayout) findViewById(R.id.linearLayoutSertifikat);
        linearLayoutNoEntry = (LinearLayout) findViewById(R.id.linearLayoutNoEntry);

        textViewSertifikatTotal = (TextView) findViewById(R.id.textViewSertifikatTotal);

        listViewDocument = (ListView) findViewById(R.id.listViewDocument);

        arrayDocAdapter = new SertifikatArrayAdapter(this, dataDocuments);
        // this activity is used for FileOpener later
        arrayDocAdapter.setActivity(this);
        listViewDocument.setAdapter(arrayDocAdapter);

        setOnClickEvent(listViewDocument);

        // first time layout shown
        showLoading(true);

        // calling to Server API for documents sertifikat
        getCertificateStudentAll();

        // example download file using 3rd party library

        FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(this)
                .setDownloadConcurrentLimit(3)
                .build();

        fetch = Fetch.Impl.getInstance(fetchConfiguration);

    }


    public void setCurrentFileName(String g){
        fileName = g;
    }

    public String getCurrentFileName(){
        return fileName;
    }

    private void showNoEntry(boolean b){

        if(b){
            linearLayoutNoEntry.setVisibility(View.VISIBLE);
            listViewDocument.setVisibility(View.GONE);
        }else {
            linearLayoutNoEntry.setVisibility(View.GONE);
            listViewDocument.setVisibility(View.VISIBLE);
        }

    }

    private void showLoading(boolean b){

        if(b){
            linearLayoutLoading.setVisibility(View.VISIBLE);
            linearLayoutSertifikat.setVisibility(View.GONE);
        }else {
            linearLayoutLoading.setVisibility(View.GONE);
            linearLayoutSertifikat.setVisibility(View.VISIBLE);
        }

    }


    public void downloadFile(ImageView img, ProgressBar prgBar1, ProgressBar prgBar2, String fileName, String alamatTujuan ){

        // ShowDialog.message(this, "testing download " + alamatTujuan);
        // self referencing for future usage after success callback returned
        prgBar01 = prgBar1;
        prgBar02 = prgBar2;

        imageAccess = img;

        WebFetch httpCall = new WebFetch(this, this);
        //no need to calculate size
        //httpCall.setCalculateSizeRequired(true);
        httpCall.setFileNameToBeSaved(fileName);
        httpCall.setTargetURL(alamatTujuan);
        httpCall.executeFetch();

        // store temporarily
        urlDownload = alamatTujuan;

        //ShowDialog.message(this, "Downloading from Fetcher");

    }


    public void shareTo(File fileIn, String titleNa){
        new WhatsappSender(this).sendFileToWhatsApp(fileIn, titleNa);
    }

    // additional onclick event for the listview instead of the icon clicked
    private void setOnClickEvent(ListView el){
        el.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                arrayDocAdapter.openingFile(position, view);

            }
        });
    }



    public void getCertificateStudentAll() {

        WebRequest httpCall = new WebRequest(this, this);
        httpCall.addData("username", usName);
        httpCall.addData("token", aToken);

        httpCall.setWaitState(true);
        httpCall.setRequestMethod(WebRequest.POST_METHOD);
        httpCall.setTargetURL(URLReference.CertificateStudentAll);
        httpCall.execute();

    }

    @Override
    public void nextActivity() {
        Intent n = new Intent(this, PDFActivity.class);
        n.putExtra(Keys.FILE_PDF_TARGET, getCurrentFileName());
        startActivity(n);
    }

    @Override
    public void onFailed(){
        // usually because no internet

    }

    @Override
    public void onSuccess(String urlTarget, String respond) {

        try {
            Gson objectG = new Gson();

            if (RespondHelper.isValidRespond(respond)) {

                if (urlTarget.contains(URLReference.CertificateStudentAll)) {

                    // remove the loading
                    // and automatically show the document layout
                    // showing the no-entry to be hidden as well
                    showLoading(false);
                    showNoEntry(false);

                    String innerData = RespondHelper.getValue(respond, "multi_data");
                    Sertifikat[] dataIn = objectG.fromJson(innerData, Sertifikat[].class);

                    // clearing up
                    dataDocuments.clear();
                    dataTemp.clear();

                    textViewSertifikatTotal.setText("Keseluruhan dokumen anda berjumlah : " + dataIn.length + " file.");
                    dataDocuments = ArrayHelper.fillArrayList(dataDocuments, dataIn);
                    // backup for search purposes
                    dataTemp = ArrayHelper.fillArrayList(dataTemp, dataIn);

                    //  ShowDialog.message(this, "Documents are " + dataDocuments.size());
                    arrayDocAdapter.notifyDataSetChanged();

                }

                //ShowDialog.message(this, "we got " + respond);

            }else{
                // this is for invalid response from the server

                if(urlDownload!=null){

                    if(urlTarget.contains(urlDownload)){
                        // refresh the layout
                        // calling to Server API for documents
                        getCertificateStudentAll();
                    }

                }else if(urlTarget.contains(URLReference.CertificateStudentAll)){
                    showLoading(false);
                    showNoEntry(true);
                }
            }

        } catch (Exception ex) {
            ShowDialog.message(this, "error on " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    private void refreshCurrentActivity(){

        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public void onSuccessByFetch(String urlTarget, String result) {
        // this is a call back of completed download by 3rd party fetch library

        if(urlTarget.contains(urlDownload)){
          /*
            prgBar01.setVisibility(View.GONE);
            prgBar02.setVisibility(View.GONE);
            imageAccess.setImageResource(R.drawable.checklist);
            imageAccess.setTag("checklist");
            imageAccess.setVisibility(View.VISIBLE);

            */

            refreshCurrentActivity();
        }

    }
}