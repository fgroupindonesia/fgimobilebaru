package com.fgroupindonesia.fgimobilebaru.helper;

import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class ErrorLogger {

    public static String getAlbumStorageDir(AppCompatActivity actIn){
        // excluded
        // String path = Environment.getExternalStorageDirectory()
        //         + "/Android/data/"+ actIn.getApplicationContext().getPackageName();

        // now we used the below internal storage access only
        File f = new File(actIn.getFilesDir(), actIn.getApplicationContext().getPackageName());

        if(!f.exists())
            f.mkdirs();

        String path = f.getAbsolutePath();
        return path;
    }

    public static void write(AppCompatActivity actIn, Exception ex){

        File file = null;
        try{
            file = new File(getAlbumStorageDir(actIn), "error.log");

            if(!file.exists()) {
                file.createNewFile();
            }

            PrintStream ps = new PrintStream(file);

            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDate = df.format(c);

            ps.println(formattedDate);
            ex.printStackTrace(ps);
            //ps.println(ex.initCause(ex).toString());
            ps.close();

            Log.e(TAG,Log.getStackTraceString(ex));

        } catch(Exception e){
            System.err.println("Error tah " + e.getMessage());
        }

    }

}
