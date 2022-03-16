package com.fgroupindonesia.fgimobilebaru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fgroupindonesia.fgimobilebaru.helper.ArrayHelper;
import com.fgroupindonesia.fgimobilebaru.helper.FileOpener;
import com.fgroupindonesia.fgimobilebaru.helper.Keys;
import com.fgroupindonesia.fgimobilebaru.helper.Navigator;
import com.fgroupindonesia.fgimobilebaru.helper.NavigatorFetch;
import com.fgroupindonesia.fgimobilebaru.helper.RespondHelper;
import com.fgroupindonesia.fgimobilebaru.helper.ShowDialog;
import com.fgroupindonesia.fgimobilebaru.helper.TextChangedListener;
import com.fgroupindonesia.fgimobilebaru.helper.UIHelper;
import com.fgroupindonesia.fgimobilebaru.helper.URLReference;
import com.fgroupindonesia.fgimobilebaru.helper.WebRequest;
import com.fgroupindonesia.fgimobilebaru.helper.adapter.DocumentArrayAdapter;
import com.fgroupindonesia.fgimobilebaru.helper.shared.UserData;
import com.fgroupindonesia.fgimobilebaru.object.Document;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DokumenActivity extends AppCompatActivity implements Navigator, NavigatorFetch {

    private Fetch fetch;
    EditText editTextSearchDocument;
    TextView textViewDocumentTotal;
    ListView listViewDocument;
    DocumentArrayAdapter arrayDocAdapter;
    ArrayList<Document> dataDocuments = new ArrayList<Document>();
    ArrayList<Document> dataTemp = new ArrayList<Document>();

    LinearLayout linearLayoutLoading, linearLayoutDokumen, linearLayoutNoEntry;
    String usName, aToken, urlDownload, fileName;

    // for several download ProgressBars
    ProgressBar prgBar01, prgBar02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokumen);

        // for shared preference usage
        UserData.setPreference(this);

        usName = UserData.getPreferenceString(Keys.USERNAME);
        aToken = UserData.getPreferenceString(Keys.TOKEN);

        linearLayoutLoading = (LinearLayout) findViewById(R.id.linearLayoutLoading);
        linearLayoutDokumen = (LinearLayout) findViewById(R.id.linearLayoutDokumen);
        linearLayoutNoEntry = (LinearLayout) findViewById(R.id.linearLayoutNoEntry);

        textViewDocumentTotal = (TextView) findViewById(R.id.textViewDocumentTotal);
        editTextSearchDocument = (EditText) findViewById(R.id.editTextSearchDocument);

        listViewDocument = (ListView) findViewById(R.id.listViewDocument);

        arrayDocAdapter = new DocumentArrayAdapter(this, dataDocuments);
        // this activity is used for FileOpener later
        arrayDocAdapter.setActivity(this);
        listViewDocument.setAdapter(arrayDocAdapter);

        setOnClickEvent(listViewDocument);

        setEditTextEvent(editTextSearchDocument);

        // first time layout shown
        showLoading(true);

        // calling to Server API for documents
        getDocumentsUser();

        // example download file using 3rd party library

        FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(this)
                .setDownloadConcurrentLimit(3)
                .build();

        fetch = Fetch.Impl.getInstance(fetchConfiguration);

    }

    private Uri uriFromFile(AppCompatActivity cont, File file)  {
            return Uri.fromFile(file);
    }

    public void shareTo(File fileIn, String titleNa){

        String name = fileIn.getName().toLowerCase();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM,  uriFromFile(this,fileIn));
        shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if(name.contains("pdf")) {
            shareIntent.setType("application/pdf");
        }else if(name.contains("jpg") || name.contains("jpeg")){
            shareIntent.setType("image/jpeg");
        }else if(name.contains("ppt") || name.contains("pptx")){
            shareIntent.setType("application/vnd.ms-powerpoint");
        }

        startActivity(Intent.createChooser(shareIntent, "Share.." + titleNa));

    }

    private void resetItemChecked(){
        listViewDocument.clearChoices();
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
            linearLayoutDokumen.setVisibility(View.GONE);
        }else {
            linearLayoutLoading.setVisibility(View.GONE);
            linearLayoutDokumen.setVisibility(View.VISIBLE);
        }

    }


    public void downloadFile(ProgressBar prgBar1, ProgressBar prgBar2, String fileName, String alamatTujuan ){

        // ShowDialog.message(this, "testing download " + alamatTujuan);
        // self referencing for future usage after success callback returned
        prgBar01 = prgBar1;
        prgBar02 = prgBar2;

        WebFetch httpCall = new WebFetch(alamatTujuan, fileName);
        httpCall.setActivity(this);
        httpCall.setNavigatorFetch(this);
        httpCall.prepareFetchLibrary();
        httpCall.executeFetch();

        // store temporarily
        urlDownload = alamatTujuan;

        ShowDialog.message(this, "Downloading from Fetcher");

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

    private void setEditTextEvent(EditText el) {

        el.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    searchDocument(null);
                    return true;
                }
                return false;
            }
        });

        el.addTextChangedListener(new TextChangedListener<EditText>(el) {
            @Override
            public void onTextChanged(EditText target, Editable s) {
                if(editTextSearchDocument!=null){

                    String dicari = UIHelper.getText(editTextSearchDocument);
                    if (dicari != null) {

                        // when the search become not available due to empty text

                        textViewDocumentTotal.setText("Keseluruhan dokumen anda berjumlah : " + dataTemp.size()+ " file.");
                        dataDocuments = ArrayHelper.copyBackDocument(dataTemp, dataDocuments);
                        arrayDocAdapter = new DocumentArrayAdapter(DokumenActivity.this, dataDocuments);
                        listViewDocument.setAdapter(arrayDocAdapter);

                        arrayDocAdapter.notifyDataSetChanged();
                    }

                }

            }
        });
    }

    public void searchDocument(View v) {

        String dicari = UIHelper.getText(editTextSearchDocument);

        //ShowDialog.message(this, "berisi temp " + dataTemp.size());

        // before updated

        if (dicari != null) {
            if(dicari.length()>1){

                dicari = dicari.toLowerCase();

                dataDocuments.clear();
                textViewDocumentTotal.setText("Keseluruhan dokumen anda berjumlah : 0 file.");

                // search through the arraylist
                for (Document d : dataTemp) {
                    if (d.getDescription().toLowerCase().contains(dicari) || d.getTitle().toLowerCase().contains(dicari)) {
                        dataDocuments.add(d);
                    } else if (d.getFilename() != null) {
                        if (d.getFilename().toLowerCase().contains(dicari)) {
                            dataDocuments.add(d);
                        }
                    }
                }

                arrayDocAdapter.notifyDataSetChanged();


            }
        }


    }

    public void getDocumentsUser() {

        WebRequest httpCall = new WebRequest(this, this);
        httpCall.addData("username", usName);
        httpCall.addData("token", aToken);

        httpCall.setWaitState(true);
        httpCall.setRequestMethod(WebRequest.POST_METHOD);
        httpCall.setTargetURL(URLReference.DocumentAll);
        httpCall.execute();

    }

    public String getCurrentFileName(){
        return fileName;
    }

    public void setCurrentFileName(String n){
        fileName = n;
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

                if (urlTarget.contains(URLReference.DocumentAll)) {

                    // remove the loading
                    // and automatically show the document layout
                    // showing the no-entry to be hidden as well
                    showLoading(false);
                    showNoEntry(false);

                    String innerData = RespondHelper.getValue(respond, "multi_data");
                    Document[] dataIn = objectG.fromJson(innerData, Document[].class);

                    // clearing up
                    dataDocuments.clear();
                    dataTemp.clear();

                    textViewDocumentTotal.setText("Keseluruhan dokumen anda berjumlah : " + dataIn.length + " file.");
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
                        getDocumentsUser();
                    }

                }else if(urlTarget.contains(URLReference.DocumentAll)){
                    showLoading(false);
                    showNoEntry(true);
                }
            }

        } catch (Exception ex) {
            ShowDialog.message(this, "error on " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    @Override
    public void onSuccessByFetch(String urlTarget, String result) {
        // this is a call back of completed download by 3rd party fetch library

        if(urlTarget.contains(urlDownload)){
            prgBar01.setVisibility(View.GONE);
            prgBar02.setVisibility(View.GONE);
        }

    }
}