package com.fgroupindonesia.fgimobilebaru;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fgroupindonesia.fgimobilebaru.helper.ArrayHelper;
import com.fgroupindonesia.fgimobilebaru.helper.Keys;
import com.fgroupindonesia.fgimobilebaru.helper.Navigator;
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

import java.util.ArrayList;

public class DokumenActivity extends AppCompatActivity implements Navigator {

    EditText editTextSearchDocument;
    TextView textViewDocumentTotal;
    ListView listViewDocument;
    DocumentArrayAdapter arrayDocAdapter;
    ArrayList<Document> dataDocuments = new ArrayList<Document>();
    ArrayList<Document> dataTemp = new ArrayList<Document>();

    String usName, aToken, urlDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokumen);

        textViewDocumentTotal = (TextView) findViewById(R.id.textViewDocumentTotal);
        editTextSearchDocument = (EditText) findViewById(R.id.editTextSearchDocument);

        setEditTextEvent(editTextSearchDocument);

        listViewDocument = (ListView) findViewById(R.id.listViewDocument);

        arrayDocAdapter = new DocumentArrayAdapter(this, dataDocuments);
        // this activity is used for FileOpener later
        arrayDocAdapter.setActivity(this);

        setOnClickEvent(listViewDocument);

        listViewDocument.setAdapter(arrayDocAdapter);

        // for shared preference usage
        UserData.setPreference(this);

        usName = UserData.getPreferenceString(Keys.USERNAME);
        aToken = UserData.getPreferenceString(Keys.TOKEN);

        // calling to Server API for documents
        getDocumentsUser();
    }

    public void downloadFile(ProgressBar prgBar, String fileName, String alamatTujuan ){

        //ShowDialog.message(this, "testing download " + alamatTujuan);

        WebRequest httpCall = new WebRequest(this, this);
        httpCall.setTargetURL(alamatTujuan);
        httpCall.setDownloadState(true);

        prgBar.setVisibility(View.VISIBLE);

        httpCall.setProgressBar(prgBar);
        httpCall.setWaitState(true);
        // we should put the filename over here
        // to make android know what filename to be saved
        httpCall.addData("filename", fileName);
        httpCall.setRequestMethod(WebRequest.GET_METHOD);
        httpCall.execute();

        // store temporarily
        urlDownload = alamatTujuan;

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
                String dicari = UIHelper.getText(editTextSearchDocument);
                if (dicari.length() < 1) {

                    // when the search become not available due to empty text

                    textViewDocumentTotal.setText("Keseluruhan dokumen anda berjumlah : " + dataTemp.size()+ " file.");
                    dataDocuments = ArrayHelper.copyBackDocument(dataTemp, dataDocuments);
                    arrayDocAdapter = new DocumentArrayAdapter(DokumenActivity.this, dataDocuments);
                    listViewDocument.setAdapter(arrayDocAdapter);

                    arrayDocAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void searchDocument(View v) {

        String dicari = UIHelper.getText(editTextSearchDocument).toLowerCase();
        //ShowDialog.message(this, "berisi temp " + dataTemp.size());

        // before updated


        if (dicari != null) {
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

        }

        arrayDocAdapter.notifyDataSetChanged();

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


    @Override
    public void nextActivity() {

    }


    @Override
    public void onSuccess(String urlTarget, String respond) {

        try {
            Gson objectG = new Gson();

            if (RespondHelper.isValidRespond(respond)) {

                if (urlTarget.contains(URLReference.DocumentAll)) {

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
                if(urlTarget.contains(urlDownload)){
                    // refresh the layout
                    // calling to Server API for documents
                    getDocumentsUser();
                }
            }

        } catch (Exception ex) {
            ShowDialog.message(this, "error on " + ex.getMessage());
            ex.printStackTrace();
        }

    }
}