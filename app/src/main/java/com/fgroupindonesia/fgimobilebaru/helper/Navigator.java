package com.fgroupindonesia.fgimobilebaru.helper;

// just an interface for WebRequest httpcall
public  interface Navigator {

     void nextActivity();
     void onSuccess(String urlTarget, String result);
     void onFailed(); // because internet connection

}
