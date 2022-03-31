package com.fgroupindonesia.fgimobilebaru;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.fgroupindonesia.fgimobilebaru.helper.ErrorLogger;
import com.fgroupindonesia.fgimobilebaru.helper.ImageHelper;
import com.fgroupindonesia.fgimobilebaru.helper.Keys;
import com.fgroupindonesia.fgimobilebaru.helper.Navigator;
import com.fgroupindonesia.fgimobilebaru.helper.RespondHelper;
import com.fgroupindonesia.fgimobilebaru.helper.ShowDialog;
import com.fgroupindonesia.fgimobilebaru.helper.UIHelper;
import com.fgroupindonesia.fgimobilebaru.helper.URLReference;
import com.fgroupindonesia.fgimobilebaru.helper.WebRequest;
import com.fgroupindonesia.fgimobilebaru.helper.shared.UserData;
import com.fgroupindonesia.fgimobilebaru.object.User;

import org.json.JSONObject;

import java.io.File;

public class UserProfileActivity extends AppCompatActivity implements Navigator {

    EditText editTextUsername, editTextPassword, editTextEmail,
            editTextAddress, editTextMobile, editTextTmvID, editTextTmvPass;

    LinearLayout linearUserProfileLoading;
    ScrollView scrollViewUserProfile;

    ImageView imageUserProfile;
    String picturePath, idText, filePropicName;

    User dataUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // for shared pref usage
        UserData.setPreference(this);

        editTextUsername = (EditText) findViewById(R.id.editTextUserProfileUsername);
        // lock the editing
        editTextUsername.setEnabled(false);

        editTextPassword = (EditText) findViewById(R.id.editTextUserProfilePassword);
        editTextEmail = (EditText) findViewById(R.id.editTextUserProfileEmail);
        editTextAddress = (EditText) findViewById(R.id.editTextUserProfileAddress);
        editTextMobile = (EditText) findViewById(R.id.editTextUserProfileMobilePhone);
        editTextTmvID = (EditText) findViewById(R.id.editTextUserProfileTmvID);
        editTextTmvPass = (EditText) findViewById(R.id.editTextUserProfileTmvPass);

        linearUserProfileLoading = (LinearLayout) findViewById(R.id.linearUserProfileLoading);
        scrollViewUserProfile = (ScrollView) findViewById(R.id.scrollViewUserProfile);

        imageUserProfile = (ImageView) findViewById(R.id.imageUserProfile);

        UIHelper.toggleTitleApp(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                getDataAPI();

            }
        }, Keys.PERIOD_OF_TIME_WAIT_GENERAL);


    }

    public void saveUserProfile(View v) {
        saveDataAPI();
    }

    public void downloadPictureAPI() {

        // UIAction.ACT_API_CURRENT_CALL = OPSAction.ACT_API_USERPROFILE_DOWNLOAD_PICTURE;

        WebRequest httpCall = new WebRequest(this, this);
        //httpCall.addData("token", UserData.getPreferenceString(KeyPref.TOKEN));
        httpCall.addData("propic", filePropicName);
        httpCall.setWaitState(true);
        httpCall.setDownloadState(true);

        httpCall.setRequestMethod(WebRequest.GET_METHOD);
        httpCall.setTargetURL(URLReference.UserPicture + filePropicName);
        httpCall.execute();

    }

    public void saveDataAPI() {
        //UIAction.ACT_API_CURRENT_CALL = OPSAction.ACT_API_USERPROFILE_UPDATE_DATA;

        // the web request executed by httcall
        // preparing the httpcall
        WebRequest httpCall = new WebRequest(this, this);
        httpCall.addData("token", UserData.getPreferenceString(Keys.TOKEN));
        httpCall.addData("id", idText);
        httpCall.addData("username", UIHelper.getText(editTextUsername));
        httpCall.addData("password", UIHelper.getText(editTextPassword));
        httpCall.addData("mobile", UIHelper.getText(editTextMobile));
        httpCall.addData("email", UIHelper.getText(editTextEmail));
        httpCall.addData("address", UIHelper.getText(editTextAddress));
        httpCall.addData("tmv_id", UIHelper.getText(editTextTmvID));
        httpCall.addData("tmv_pass", UIHelper.getText(editTextTmvPass));
        // this is the warning status obtained from the server
        httpCall.addData("warning_status", String.valueOf(dataUser.getWarning_status()));


        if (picturePath != null) {
            httpCall.addFile("propic", new File(picturePath));
        }

        httpCall.setWaitState(true);
        // for uploading image
        httpCall.setMultipartform(true);
        httpCall.setRequestMethod(WebRequest.POST_METHOD);
        httpCall.setTargetURL(URLReference.UserUpdate);
        httpCall.execute();


    }

    public void pickPicture(View view) {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, Keys.ACT_PICK_PICTURE);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Keys.ACT_PICK_PICTURE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                // stop if there's nothing
                return;
            }

            try {
                Uri imageURI = data.getData();
                imageUserProfile.setImageURI(imageURI);

                // this will be sent to server later
                picturePath = ImageHelper.getPath(this.getApplicationContext(), imageURI);
                //ShowDialog.message(UserProfileActivity.this, picturePath);

                // lets convert it to png to make it save for any server
                picturePath = ImageHelper.convertToSmallJPG(this, picturePath, "propic");


            } catch (Exception ex) {
                ShowDialog.message(this, "Error on choosing picture");
            }

        }
    }


    public void getDataAPI() {

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

    @Override
    public void onFailed(){
        // usually because no internet

    }

    @Override
    public void nextActivity() {

        Intent intenCaller = new Intent(this, HomeActivity.class);
        startActivity(intenCaller);

    }

    @Override
    public void onBackPressed() {
        nextActivity();
        finish();
    }

    private User extractFromJSON(JSONObject jo) {
        User user = new User();

        try {
            user.setId(jo.getInt("id"));
            user.setPropic(jo.getString("propic"));

            user.setUsername(jo.getString("username"));
            user.setPass(jo.getString("pass"));
            user.setEmail(jo.getString("email"));
            user.setMobile(jo.getString("mobile"));
            user.setAddress(jo.getString("address"));
            user.setTmv_id(jo.getString("tmv_id"));
            user.setTmv_pass(jo.getString("tmv_pass"));
            user.setWarning_status(jo.getInt("warning_status"));

        } catch (Exception ex) {
            user = null;
        }

        return user;
    }

    @Override
    public void onSuccess(String urlTarget, String respond) {

        try {

            // ShowDialog.message(UserProfileActivity.this, "respond is " + respond);

            if (RespondHelper.isValidRespond(respond)) {

                if (urlTarget.equalsIgnoreCase(URLReference.UserProfile)) {

                    JSONObject jo = RespondHelper.getObject(respond, "multi_data");

                    linearUserProfileLoading.setVisibility(View.GONE);
                    scrollViewUserProfile.setVisibility(View.VISIBLE);

                    dataUser = extractFromJSON(jo);

                    idText = String.valueOf(dataUser.getId());
                    filePropicName = dataUser.getPropic();

                    editTextUsername.setText(dataUser.getUsername());
                    editTextPassword.setText(dataUser.getPass());
                    editTextEmail.setText(dataUser.getEmail());
                    editTextMobile.setText(dataUser.getMobile());
                    editTextAddress.setText(dataUser.getAddress());
                    editTextTmvID.setText(dataUser.getTmv_id());
                    editTextTmvPass.setText(dataUser.getTmv_pass());

                    // calling the image download
                    downloadPictureAPI();

                } else if (urlTarget.equalsIgnoreCase(URLReference.UserUpdate)) {
                    // back to the dashboard (home)
                    nextActivity();
                    finish();
                }

                // the invalid output is sometimes for non-POST method
            } else if (!RespondHelper.isValidRespond(respond)) {
                // refreshing the imageview
                //ShowDialog.message(this, "downloading got " + respond);

                Bitmap b = BitmapFactory.decodeFile(respond);
                imageUserProfile.setImageBitmap(b);
            }
        } catch (Exception err) {
            ErrorLogger.write(err);
            ShowDialog.message(this, "Error obtaining userprofile data! Please contact administrator!");
            ShowDialog.message(this, err.getMessage());

        }

    }


}