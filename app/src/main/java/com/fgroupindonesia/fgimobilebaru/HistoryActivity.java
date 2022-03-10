package com.fgroupindonesia.fgimobilebaru;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fgroupindonesia.fgimobilebaru.helper.Keys;
import com.fgroupindonesia.fgimobilebaru.helper.Navigator;
import com.fgroupindonesia.fgimobilebaru.helper.RespondHelper;
import com.fgroupindonesia.fgimobilebaru.helper.ShowDialog;
import com.fgroupindonesia.fgimobilebaru.helper.URLReference;
import com.fgroupindonesia.fgimobilebaru.helper.WebRequest;
import com.fgroupindonesia.fgimobilebaru.helper.shared.UserData;
import com.fgroupindonesia.fgimobilebaru.object.History;
import com.fgroupindonesia.fgimobilebaru.object.Token;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

public class HistoryActivity extends AppCompatActivity implements Navigator {

    History [] dataHistories;
    LinearLayout linearHistoryLoading, linearHistory;
    EditText editTextTextMultiLine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // for shared preference
        UserData.setPreference(this);

        linearHistoryLoading = (LinearLayout) findViewById(R.id.linearHistoryLoading);
        linearHistory = (LinearLayout) findViewById(R.id.linearHistory);
        editTextTextMultiLine = (EditText) findViewById(R.id.editTextTextMultiLine);

        // hide the keyboard
        editTextTextMultiLine.clearFocus();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        // first appearance
        showHistoryLayout(false);

        // get all data from server
        getAllHistoryRecords();
    }

    private void showHistoryLayout(boolean b){
        if(!b){
            linearHistoryLoading.setVisibility(View.VISIBLE);
            linearHistory.setVisibility(View.GONE);
        }else{
            linearHistoryLoading.setVisibility(View.GONE);
            linearHistory.setVisibility(View.VISIBLE);
        }
    }

    private void getAllHistoryRecords(){

        WebRequest httpCall = new WebRequest(HistoryActivity.this, HistoryActivity.this);

        httpCall.addData("username", UserData.getPreferenceString(Keys.USERNAME));
        httpCall.addData("token", UserData.getPreferenceString(Keys.TOKEN));

        // we need to wait for the response
        httpCall.setWaitState(true);
        httpCall.setRequestMethod(WebRequest.POST_METHOD);
        httpCall.setTargetURL(URLReference.HistoryAll);
        httpCall.execute();

    }

    public void closeHistory(View v){
        finish();
    }

    private String getText(EditText edt){
        return edt.getText().toString();
    }

    private void addintoView(History histObject){

    String textPrev = getText(editTextTextMultiLine);

    StringBuffer stb = new StringBuffer("\n");
    stb.append(histObject.getDate_created() + " for\n" + histObject.getDescription() +"\n\n");

    editTextTextMultiLine.setText(textPrev + stb.toString());

    }

    @Override
    public void nextActivity() {

    }

    @Override
    public void onSuccess(String urlTarget, String respond) {

        try {
            // ShowDialog.message(this, "dapt " + respond);


            Gson objectG = new Gson();

            if (RespondHelper.isValidRespond(respond)) {

                if (urlTarget.contains(URLReference.HistoryAll)) {

                    JSONArray jsons = RespondHelper.getArray(respond, "multi_data");

                    JsonParser parser = new JsonParser();
                    JsonElement mJson =  parser.parse(jsons.toString());

                     dataHistories  = objectG.fromJson(mJson, History[].class);

                     for(History hs: dataHistories){
                         addintoView(hs);
                     }

                     // rendering complete now show the UI
                    showHistoryLayout(true);

                }

            } else if (!RespondHelper.isValidRespond(respond)) {

                ShowDialog.message(this, "Belum ada data History!");
            }

        } catch (Exception ex) {
            ShowDialog.message(this, "Error " + ex.getMessage());
            ex.printStackTrace();
        }


    }


}