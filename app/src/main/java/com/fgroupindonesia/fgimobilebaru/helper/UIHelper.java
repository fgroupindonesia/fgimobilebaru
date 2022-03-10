package com.fgroupindonesia.fgimobilebaru.helper;

import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.fgroupindonesia.fgimobilebaru.R;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UIHelper {

    public static String getText(EditText element) {
        if(element.getText()!=null) {
            return element.getText().toString();
        } else {
            return null;
        }
    }

    public static String getText(TextView element) {
        if(element.getText()!=null) {
            return element.getText().toString();
        } else {
            return null;
        }
    }

    private static String dayIndonesia[] = {"Ahad", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
    private static String dayEnglish[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private static String monthIndonesia[] = {"Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt", "Nov", "Des"};

    public static final int LANG_CODE_ID = 0, LANG_CODE_EN = 1;

    public static String replaceAllEnglishToIndonesian(String inputData){

        String endRest = null;
        for(String dayEn : dayEnglish ){
           inputData = inputData.replace(dayEn.toLowerCase(), toIndonesian(dayEn));
        }

        endRest = inputData;
        return endRest;
    }

    public static String convertMonthToIndonesia(int numMOnth){
        if(numMOnth<=12) {
            return monthIndonesia[numMOnth - 1];
        }

        return null;
    }

    public static String toIndonesian(String dayName) {
        int indexFound = 0;
        String newName = null;
        for (String dName : dayEnglish) {

            if (dayName.toLowerCase().contains(dName.toLowerCase())) {
                newName = dayName.toLowerCase().replace(dName.toLowerCase(), dayIndonesia[indexFound].toLowerCase());
                break;
            }
            indexFound++;
        }


        //return dayIndonesia[indexFound];
        return newName;
    }

    public static String toEnglish(String dayName) {
        int indexFound = 0;
        String newName = null;
        for (String dName : dayIndonesia) {

            if (dayName.toLowerCase().contains(dName.toLowerCase())) {
                newName = dayName.toLowerCase().replace(dName.toLowerCase(), dayEnglish[indexFound].toLowerCase());
                break;
            }

            indexFound++;
        }

        //return dayEnglish[indexFound];
        return newName;
    }

    public static String convertDateToIndonesia(String dateSet) {
        // yyyy-MM-dd HH:mm:ss
        // converted become dd-MMMM-yyyy
        String dataMentah[] = dateSet.split(" ");
        String tanggalOnly[] = dataMentah[0].split("-");
        String tanggalIndo = tanggalOnly[2] + "-" + convertMonthToIndonesia(Integer.parseInt(tanggalOnly[1])) + "-" + tanggalOnly[0];

        return tanggalIndo;
    }

    public static String convertStatusToIndonesia(String stat) {
        String val = null;
        switch (stat) {
            case "unpaid":
                val = "belum dibayar";
                break;
            case "pending":
                val = "menunggu konfirmasi";
                break;
            case "paid":
                val = "lunas";
                break;
        }

        return val;
    }

    public static String formatRupiah(int number) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

    public static boolean isEnglish(String dayName){
        for(String day:dayEnglish){
            if(day.toLowerCase().equalsIgnoreCase(dayName.toLowerCase())){
                return true;
            }
        }

        return false;
    }

    public static String convertDayName(String computerDate) {

        String res = null;
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dt1 = format1.parse(computerDate);
            DateFormat format2 = new SimpleDateFormat("EEEE");
            String finalDay = format2.format(dt1);
            res = finalDay;

            if(isEnglish(finalDay)){
                res = toIndonesian(finalDay);
            } else {
                res = toEnglish(finalDay);
            }


        } catch (Exception ex) {
            res = "";
        }

        return res;

    }

    // this will hide the titlebar if supported
    // otherwise it will centering it to the middle side
    public static void toggleTitleApp(AppCompatActivity act){
        try {
            hideTitleApp(act);
        } catch (Exception ex){
            centerTitleApp(act);
        }
    }

    private static void hideTitleApp(AppCompatActivity act)throws Exception{
       act.getSupportActionBar().hide();
    }

    private static void centerTitleApp(AppCompatActivity act){
        act.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        act.getSupportActionBar().setCustomView(R.layout.actionbar);
    }

    public static boolean empty(EditText element) {

        boolean stat = false;

        if (getText(element).trim().length() < 1) {
            stat = true;
        }

        return stat;

    }

}
