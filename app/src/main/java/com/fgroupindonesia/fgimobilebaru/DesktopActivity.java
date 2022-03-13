package com.fgroupindonesia.fgimobilebaru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.fgroupindonesia.fgimobilebaru.helper.ArrayHelper;
import com.fgroupindonesia.fgimobilebaru.helper.HistoryHelper;
import com.fgroupindonesia.fgimobilebaru.helper.Keys;
import com.fgroupindonesia.fgimobilebaru.helper.Navigator;
import com.fgroupindonesia.fgimobilebaru.helper.RespondHelper;
import com.fgroupindonesia.fgimobilebaru.helper.ShowDialog;
import com.fgroupindonesia.fgimobilebaru.helper.URLReference;
import com.fgroupindonesia.fgimobilebaru.helper.WebRequest;
import com.fgroupindonesia.fgimobilebaru.helper.adapter.RemoteLoginClientAdapter;
import com.fgroupindonesia.fgimobilebaru.helper.shared.UserData;
import com.fgroupindonesia.fgimobilebaru.object.RemoteLoginClient;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class DesktopActivity extends AppCompatActivity implements Navigator {

    private IntentIntegrator intentIntegrator;

    LinearLayout linearDesktopLoading, linearDesktopOpenedClient;
    String aToken, usName;

    RemoteLoginClientAdapter arrayClientAdapter;
    ArrayList<RemoteLoginClient> dataClients = new ArrayList<RemoteLoginClient>();
    ArrayList<RemoteLoginClient> dataTemp = new ArrayList<RemoteLoginClient>();
    ListView listViewClientDesktop;
    String machineID;

    private void showLoading(boolean b){

        if(b){
            linearDesktopLoading.setVisibility(View.VISIBLE);
            linearDesktopOpenedClient.setVisibility(View.GONE);
        }else{
            linearDesktopLoading.setVisibility(View.GONE);
            linearDesktopOpenedClient.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desktop);

        // for shared preference usage
        UserData.setPreference(this);

        usName = UserData.getPreferenceString(Keys.USERNAME);
        aToken = UserData.getPreferenceString(Keys.TOKEN);

        linearDesktopLoading = (LinearLayout) findViewById(R.id.linearDesktopLoading);
        linearDesktopOpenedClient = (LinearLayout) findViewById(R.id.linearDesktopOpenedClient);

        listViewClientDesktop = (ListView) findViewById(R.id.listViewClientDesktop);

        setEventListView(listViewClientDesktop);

        arrayClientAdapter = new RemoteLoginClientAdapter(this, dataClients);
        // this activity is used for FileOpener later
        arrayClientAdapter.setActivity(this);

        listViewClientDesktop.setAdapter(arrayClientAdapter);

        // calling to server whether this client has several ip address logged in
        requestShowAllData();

    }

    private void setEventListView(ListView list){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // obtaining the object from the adapter
                RemoteLoginClient rclient = arrayClientAdapter.getItem(position);

                machineID = rclient.getMachine_unique();

                //ShowDialog.message(DesktopActivity.this, "Coba in " + machineID);
                disconnectClient();

            }
        });
    }

    private void requestShowAllData(){
        WebRequest httpCall = new WebRequest(this, this);

        httpCall.addData("username", usName);
        httpCall.addData("token",aToken);
        httpCall.setWaitState(true);
        httpCall.setRequestMethod(WebRequest.POST_METHOD);
        httpCall.setTargetURL(URLReference.RemoteLoginShow);
        httpCall.execute();

    }

    public void disconnectClient(){

        WebRequest httpCall = new WebRequest(this, this);

        httpCall.addData("machine_unique", machineID);
        httpCall.addData("username", usName);
        httpCall.addData("token",aToken);
        httpCall.setWaitState(true);
        httpCall.setRequestMethod(WebRequest.POST_METHOD);
        httpCall.setTargetURL(URLReference.RemoteLoginDisconnect);
        httpCall.execute();

        ShowDialog.message(this, "Desktop Client berhasil logout");

    }


    private void verifyClient(){

        // the web request executed by httcall
        // preparing the httpcall
        WebRequest httpCall = new WebRequest(this, this);

        httpCall.addData("machine_unique", machineID);
        httpCall.addData("username", usName);
        httpCall.addData("token",aToken);
        httpCall.setWaitState(true);
        httpCall.setRequestMethod(WebRequest.POST_METHOD);
        httpCall.setTargetURL(URLReference.RemoteLoginVerify);
        httpCall.execute();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() == null){
                //ShowDialog.message(this, "Hasil tidak ditemukan");
                // let's back to Home Dashboard
                finish();
            }else{
                // jika qrcode berisi data

                machineID = result.getContents();

                // post to the server to unlock the client
                ShowDialog.message(this, machineID);

                // make a FLAG
                // UIAction.ACT_API_CURRENT_CALL = OPSAction.ACT_API_REMOTELOGIN_VERIFY;

                verifyClient();

            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void readQRCode(){

        intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setPrompt("Scan a barcode");
        intentIntegrator.setCameraId(0);  // Use a specific camera of the device
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setBarcodeImageEnabled(false);

        intentIntegrator.setCaptureActivity(PortraitCaptureActivity.class);

        intentIntegrator.initiateScan();

    }



    @Override
    public void nextActivity() {

    }

    @Override
    public void onSuccess(String urlTarget, String respond) {

        try{

            Gson objectG = new Gson();

            if (RespondHelper.isValidRespond(respond)) {

                // when it comes from Verifying Client
                //if(UIAction.ACT_API_CURRENT_CALL == OPSAction.ACT_API_REMOTELOGIN_VERIFY){
                if(urlTarget.contains(URLReference.RemoteLoginVerify)){

                    // we end this activity, simply to say "OK, done!"
                    // and post a history API call
                    addRecordHistory();

                    finish();

                } else if(urlTarget.contains(URLReference.RemoteLoginShow)){

                    // extracting the data
                    String innerData = RespondHelper.getValue(respond, "multi_data");
                    RemoteLoginClient[] dataIn = objectG.fromJson(innerData, RemoteLoginClient[].class);

                    // clearing up
                    dataClients.clear();
                    dataTemp.clear();

                    dataClients = ArrayHelper.fillArrayList(dataClients, dataIn);
                    // backup for search purposes
                    dataTemp = ArrayHelper.fillArrayList(dataTemp, dataIn);

                    //  ShowDialog.message(this, "Documents are " + dataDocuments.size());
                    arrayClientAdapter.notifyDataSetChanged();

                    // hide the loading ui
                    showLoading(false);

                } else if (urlTarget.contains(URLReference.RemoteLoginDisconnect)){

                    // refresh back the ui
                    requestShowAllData();
                }

            } else {

                if(urlTarget.contains(URLReference.RemoteLoginShow)){
                    // lets open the qrcode reader
                    readQRCode();
                }


            }



        } catch(Exception err){

            ShowDialog.message(this, "Error verifying client. Please contact administrator!");
            ShowDialog.message(this, err.getMessage());
        }



    }

    private void addRecordHistory(){
        HistoryHelper hsper = new HistoryHelper();
        hsper.verifyingRemoteClient(usName, aToken, this);
    }

}