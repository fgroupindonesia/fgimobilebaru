package com.fgroupindonesia.fgimobilebaru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
import com.fgroupindonesia.fgimobilebaru.helper.NavigatorFetch;
import com.fgroupindonesia.fgimobilebaru.helper.RespondHelper;
import com.fgroupindonesia.fgimobilebaru.helper.ShowDialog;
import com.fgroupindonesia.fgimobilebaru.helper.TextChangedListener;
import com.fgroupindonesia.fgimobilebaru.helper.UIHelper;
import com.fgroupindonesia.fgimobilebaru.helper.URLReference;
import com.fgroupindonesia.fgimobilebaru.helper.WebFetch;
import com.fgroupindonesia.fgimobilebaru.helper.WebRequest;
import com.fgroupindonesia.fgimobilebaru.helper.WhatsappSender;
import com.fgroupindonesia.fgimobilebaru.helper.adapter.DocumentArrayAdapter;
import com.fgroupindonesia.fgimobilebaru.helper.shared.UserData;
import com.fgroupindonesia.fgimobilebaru.object.Document;
import com.google.gson.Gson;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;

import java.io.File;
import java.util.ArrayList;

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
    ImageView imageAccess;
    TextView txtSize;

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


    public void shareTo(File fileIn, String titleNa) {
        //ShowDialog.message(this, "mo dishare " + fileIn.getAbsolutePath());
        new WhatsappSender(this).sendFileToWhatsApp(fileIn, titleNa);
    }

    private void resetItemChecked() {
        listViewDocument.clearChoices();
    }


    private void showNoEntry(boolean b) {

        if (b) {
            linearLayoutNoEntry.setVisibility(View.VISIBLE);
            listViewDocument.setVisibility(View.GONE);
        } else {
            linearLayoutNoEntry.setVisibility(View.GONE);
            listViewDocument.setVisibility(View.VISIBLE);
        }

    }

    private void showLoading(boolean b) {

        if (b) {
            linearLayoutLoading.setVisibility(View.VISIBLE);
            linearLayoutDokumen.setVisibility(View.GONE);
        } else {
            linearLayoutLoading.setVisibility(View.GONE);
            linearLayoutDokumen.setVisibility(View.VISIBLE);
        }

    }


    public void downloadFile(TextView txt, ImageView img, ProgressBar prgBar1, ProgressBar prgBar2, String fileName, String alamatTujuan) {

        // ShowDialog.message(this, "testing download " + alamatTujuan);
        // self referencing for future usage after success callback returned
        prgBar01 = prgBar1;
        prgBar02 = prgBar2;
        imageAccess = img;
        txtSize = txt;

        WebFetch httpCall = new WebFetch(this, this);

        httpCall.setFileNameToBeSaved(fileName);
        httpCall.setTargetURL(alamatTujuan);
        httpCall.executeFetch();

        // store temporarily
        urlDownload = alamatTujuan;

        // ShowDialog.message(this, "Downloading from Fetcher");

    }

    // additional onclick event for the listview instead of the icon clicked
    private void setOnClickEvent(ListView el) {
        el.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        el.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                searchDocument(null);
            }
        });
    }

    public void searchDocument(View v) {

        String dicari = UIHelper.getText(editTextSearchDocument);

        //ShowDialog.message(this, "berisi temp " + dataTemp.size());


        if (dicari != null) {
            if (dicari.length() > 0) {

                dicari = dicari.toLowerCase();

                dataDocuments.clear();

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

                textViewDocumentTotal.setText("Keseluruhan dokumen anda berjumlah : " +dataDocuments.size() +" file.");

                arrayDocAdapter.notifyDataSetChanged();

                if(dataDocuments.size()==0){
                    showLoading(false);
                    showNoEntry(true);
                }else{
                    showLoading(false);
                    showNoEntry(false);
                }


            } else {
                // when empty
                // we store back the data
                // when the search become not available due to empty text
                textViewDocumentTotal.setText("Keseluruhan dokumen anda berjumlah : " + dataTemp.size() + " file.");
                dataDocuments = ArrayHelper.copyBackDocument(dataTemp, dataDocuments);

                arrayDocAdapter = new DocumentArrayAdapter(DokumenActivity.this, dataDocuments);
                arrayDocAdapter.setActivity(DokumenActivity.this);
                listViewDocument.setAdapter(arrayDocAdapter);

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

    public String getCurrentFileName() {
        return fileName;
    }

    public void setCurrentFileName(String n) {
        fileName = n;
    }

    @Override
    public void onFailed() {
        // usually because no internet

    }

    @Override
    public void nextActivity() {
        Intent n = new Intent(this, PDFActivity.class);
        n.putExtra(Keys.FILE_PDF_TARGET, getCurrentFileName());
        startActivity(n);
        finish();
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

            } else {
                // this is for invalid response from the server

                if (urlDownload != null) {

                    if (urlTarget.contains(urlDownload)) {
                        // refresh the layout
                        // calling to Server API for documents
                        getDocumentsUser();
                    }

                } else if (urlTarget.contains(URLReference.DocumentAll)) {
                    showLoading(false);
                    showNoEntry(true);
                }
            }

        } catch (Exception ex) {
            ShowDialog.message(this, "error on " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    private void refreshCurrentActivity() {

        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
        finish();
    }


    @Override
    public void onSuccessByFetch(String urlTarget, String result) {
        if (urlTarget.contains(urlDownload)) {
        /*    prgBar01.setVisibility(View.GONE);
            prgBar02.setVisibility(View.GONE);

            txtSize.setText(fileSize);
            imageAccess.setImageResource(R.drawable.checklist);
            imageAccess.setTag("checklist");
            imageAccess.setVisibility(View.VISIBLE);

         */
            refreshCurrentActivity();
        }
    }
}