package com.fgroupindonesia.fgimobilebaru.helper;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.fgroupindonesia.fgimobilebaru.AttendanceActivity;
import com.fgroupindonesia.fgimobilebaru.BillActivity;
import com.fgroupindonesia.fgimobilebaru.DesktopActivity;
import com.fgroupindonesia.fgimobilebaru.HomeActivity;
import com.fgroupindonesia.fgimobilebaru.LoginActivity;
import com.fgroupindonesia.fgimobilebaru.SettingsActivity;
import com.fgroupindonesia.fgimobilebaru.object.Attendance;
import com.fgroupindonesia.fgimobilebaru.object.User;

public class HistoryHelper {

    public HistoryHelper(){
    }

    public void changingSettings(String username, String token, String message, SettingsActivity setAct){

        WebRequest httpCall = new WebRequest(setAct, setAct);

        httpCall.addData("description", message);
        httpCall.addData("username", username);
        httpCall.addData("token", token);

        // we need to wait for the response
        httpCall.setWaitState(true);
        httpCall.setRequestMethod(WebRequest.POST_METHOD);
        httpCall.setTargetURL(URLReference.HistoryAdd);
        httpCall.execute();
    }

    public void loggingOut(String username, String token, HomeActivity homeAct){

        WebRequest httpCall = new WebRequest(homeAct, homeAct);

        httpCall.addData("description", "logging out from mobile phone successfully");
        httpCall.addData("username", username);
        httpCall.addData("token", token);

        // we need to wait for the response
        httpCall.setWaitState(true);
        httpCall.setRequestMethod(WebRequest.POST_METHOD);
        httpCall.setTargetURL(URLReference.HistoryAdd);
        httpCall.execute();
    }

    public void verifyingRemoteClient(String username, String token, DesktopActivity deskAct){

        WebRequest httpCall = new WebRequest(deskAct, deskAct);

        httpCall.addData("description", "verifying remote client from mobile phone successfully");
        httpCall.addData("username", username);
        httpCall.addData("token", token);

        // we need to wait for the response
        httpCall.setWaitState(true);
        httpCall.setRequestMethod(WebRequest.POST_METHOD);
        httpCall.setTargetURL(URLReference.HistoryAdd);
        httpCall.execute();
    }

    public void logging(String username, String token, LoginActivity loginAct){

        WebRequest httpCall = new WebRequest(loginAct, loginAct);

        httpCall.addData("description", "logging in to mobile phone successfully");
        httpCall.addData("username", username);
        httpCall.addData("token", token);

        // we need to wait for the response
        httpCall.setWaitState(true);
        httpCall.setRequestMethod(WebRequest.POST_METHOD);
        httpCall.setTargetURL(URLReference.HistoryAdd);
        httpCall.execute();
    }


    public void updateAttendance(String username, String token, String status, AttendanceActivity attAct){

        WebRequest httpCall = new WebRequest(attAct, attAct);

        httpCall.addData("description", "attendance updated with "+ status + " status!");
        httpCall.addData("username", username);
        httpCall.addData("token", token);

        // we need to wait for the response
        httpCall.setWaitState(true);
        httpCall.setRequestMethod(WebRequest.POST_METHOD);
        httpCall.setTargetURL(URLReference.HistoryAdd);
        httpCall.execute();
    }

    public void uploadingPayment(String username, String token, String rpCash, BillActivity billAct){

        WebRequest httpCall = new WebRequest(billAct, billAct);

        httpCall.addData("description", "paid some cash "+rpCash+" to bank transfer");
        httpCall.addData("username", username);
        httpCall.addData("token", token);

        // we need to wait for the response
        httpCall.setWaitState(true);
        httpCall.setRequestMethod(WebRequest.POST_METHOD);
        httpCall.setTargetURL(URLReference.HistoryAdd);
        httpCall.execute();
    }

}
