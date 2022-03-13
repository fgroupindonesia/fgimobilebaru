package com.fgroupindonesia.fgimobilebaru.helper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import java.io.File;

public class FileOpener {

    public static void openFile(Activity act, File fileIn){
        Uri uri = null;

        if (Build.VERSION.SDK_INT < 24) {
            uri = Uri.fromFile(fileIn);
        } else {
            uri = Uri.parse(fileIn.getPath());
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        if (fileIn.toString().contains(".doc") || fileIn.toString().contains(".docx")) {
            // Word document
            intent.setDataAndType(uri, "application/msword");
        } else if (fileIn.toString().contains(".pdf")) {
            // PDF file
            intent.setDataAndType(uri, "application/pdf");
        } else if (fileIn.toString().contains(".ppt") || fileIn.toString().contains(".pptx")) {
            // Powerpoint file
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        } else if (fileIn.toString().contains(".xls") || fileIn.toString().contains(".xlsx")) {
            // Excel file
            intent.setDataAndType(uri, "application/vnd.ms-excel");
        } else if (fileIn.toString().contains(".zip")) {
            // ZIP file
            intent.setDataAndType(uri, "application/zip");
        } else if (fileIn.toString().contains(".rar")){
            // RAR file
            intent.setDataAndType(uri, "application/x-rar-compressed");
        } else if (fileIn.toString().contains(".rtf")) {
            // RTF file
            intent.setDataAndType(uri, "application/rtf");
        } else if (fileIn.toString().contains(".wav") || fileIn.toString().contains(".mp3")) {
            // WAV audio file
            intent.setDataAndType(uri, "audio/x-wav");
        } else if (fileIn.toString().contains(".gif")) {
            // GIF file
            intent.setDataAndType(uri, "image/gif");
        } else if (fileIn.toString().contains(".jpg") || fileIn.toString().contains(".jpeg") || fileIn.toString().contains(".png")) {
            // JPG file
            intent.setDataAndType(uri, "image/jpeg");
        } else if (fileIn.toString().contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain");
        } else if (fileIn.toString().contains(".3gp") || fileIn.toString().contains(".mpg") ||
                fileIn.toString().contains(".mpeg") || fileIn.toString().contains(".mpe") || fileIn.toString().contains(".mp4") || fileIn.toString().contains(".avi")) {
            // Video files
            intent.setDataAndType(uri, "video/*");
        } else {
            intent.setDataAndType(uri, "*/*");
        }

        try {
            act.startActivity(intent);
        }
        catch (Exception e) {
            ShowDialog.message(act, "error while opening file " + fileIn.getName());
            ShowDialog.message(act, e.getMessage());
            e.printStackTrace();
        }
    }

}