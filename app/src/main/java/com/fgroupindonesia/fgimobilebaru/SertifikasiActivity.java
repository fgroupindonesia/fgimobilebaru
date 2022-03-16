package com.fgroupindonesia.fgimobilebaru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fgroupindonesia.fgimobilebaru.helper.ArrayHelper;
import com.fgroupindonesia.fgimobilebaru.helper.Keys;
import com.fgroupindonesia.fgimobilebaru.helper.Navigator;
import com.fgroupindonesia.fgimobilebaru.helper.RespondHelper;
import com.fgroupindonesia.fgimobilebaru.helper.ShowDialog;
import com.fgroupindonesia.fgimobilebaru.helper.URLReference;
import com.fgroupindonesia.fgimobilebaru.helper.WebRequest;
import com.fgroupindonesia.fgimobilebaru.helper.adapter.SertifikatArrayAdapter;
import com.fgroupindonesia.fgimobilebaru.helper.shared.UserData;
import com.fgroupindonesia.fgimobilebaru.object.Sertifikat;
import com.google.gson.Gson;
import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Error;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2.FetchListener;
import com.tonyodev.fetch2.NetworkType;
import com.tonyodev.fetch2.Priority;
import com.tonyodev.fetch2.Request;
import com.tonyodev.fetch2core.DownloadBlock;
import com.tonyodev.fetch2core.Func;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SertifikasiActivity extends AppCompatActivity implements Navigator {

    private Fetch fetch;
    TextView textViewSertifikatTotal;
    ListView listViewDocument;
    SertifikatArrayAdapter arrayDocAdapter;
    ArrayList<Sertifikat> dataDocuments = new ArrayList<Sertifikat>();
    ArrayList<Sertifikat> dataTemp = new ArrayList<Sertifikat>();

    LinearLayout linearLayoutLoading, linearLayoutSertifikat, linearLayoutNoEntry;
    String usName, aToken, urlDownload, fileName;

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

    public void downloadFile(final ProgressBar prgBar, final ProgressBar prgBar2, final ImageView imageAccess, String fileName, String alamatTujuan ){

        // ShowDialog.message(this, "testing download " + alamatTujuan);
        if(prgBar!=null) {
            prgBar.setVisibility(View.VISIBLE);
            prgBar2.setVisibility(View.VISIBLE);
        }

        /*
        WebRequest httpCall = new WebRequest(this, this);
        httpCall.setDownloadState(true);
        httpCall.setProgressBar(prgBar);
        httpCall.setWaitState(true);
        // we should put the filename over here
        // to make android know what filename to be saved
        httpCall.addData("filename", fileName);
        httpCall.setTargetURL(alamatTujuan);
        httpCall.setRequestMethod(WebRequest.GET_METHOD);
        httpCall.execute();
        */

        // store temporarily
        urlDownload = alamatTujuan;

        String endPath = Environment.getExternalStorageDirectory()
                + "/Android/data/"+ this.getApplicationContext().getPackageName() + "/files/" + fileName;

        final Request request = new Request(alamatTujuan, endPath);
        request.setPriority(Priority.HIGH);
        request.setNetworkType(NetworkType.ALL);
        request.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG");

        FetchListener fetchListener = new FetchListener() {
            @Override
            public void onWaitingNetwork(@NotNull Download download) {

            }

            @Override
            public void onStarted(@NotNull Download download, @NotNull List<? extends DownloadBlock> list, int i) {

            }

            @Override
            public void onError(@NotNull Download download, @NotNull Error error, @Nullable Throwable throwable) {

            }

            @Override
            public void onDownloadBlockUpdated(@NotNull Download download, @NotNull DownloadBlock downloadBlock, int i) {

            }

            @Override
            public void onAdded(@NotNull Download download) {

            }

            @Override
            public void onQueued(@NotNull Download download, boolean waitingOnNetwork) {
                if (request.getId() == download.getId()) {
                    //showDownloadInList(download);
                }
            }

            @Override
            public void onCompleted(@NotNull Download download) {

                prgBar2.setVisibility(View.GONE);
                prgBar.setVisibility(View.GONE);

                imageAccess.setImageResource(R.drawable.checklist);
                imageAccess.setTag("checklist");
                imageAccess.setVisibility(View.VISIBLE);
            }

            @Override
            public void onProgress(@NotNull Download download, long etaInMilliSeconds, long downloadedBytesPerSecond) {
                if (request.getId() == download.getId()) {
                    //updateDownload(download, etaInMilliSeconds);
                }
                int progress = download.getProgress();
            }

            @Override
            public void onPaused(@NotNull Download download) {

            }

            @Override
            public void onResumed(@NotNull Download download) {

            }

            @Override
            public void onCancelled(@NotNull Download download) {

            }

            @Override
            public void onRemoved(@NotNull Download download) {

            }

            @Override
            public void onDeleted(@NotNull Download download) {

            }
        };

        fetch.addListener(fetchListener);

        fetch.enqueue(request, new Func<Request>() {
            @Override
            public void call(@NotNull Request updatedRequest) {
                //Request was successfully enqueued for download.
            }
        }, new Func<Error>() {
            @Override
            public void call(@NotNull Error error) {
                //An error occurred enqueuing the request.
            }
        });

        ShowDialog.message(this, "Downloading from " + urlDownload);

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
}