package com.fgroupindonesia.fgimobilebaru.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;

public class WhatsappSender {
    AppCompatActivity context;
    private String numberAdmin = "+6285795569337";

    public WhatsappSender(AppCompatActivity x) {
        context = x;
    }

    private Uri uriFromFile(File file) {
        return Uri.fromFile(file);
    }

    private File exportFile(File src, File dst) throws Exception {

        //if folder does not exist
        if (!dst.exists()) {
            if (!dst.mkdirs()) {
                return null;
            }
        }

        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File expFile = new File(dst.getPath() + File.separator + src.getName());
        FileChannel inChannel = null;
        FileChannel outChannel = null;


        inChannel = new FileInputStream(src).getChannel();
        outChannel = new FileOutputStream(expFile).getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        if (inChannel != null)
            inChannel.close();
        if (outChannel != null)
            outChannel.close();

        return expFile;
    }

    public void sendFileToWhatsApp(File fileIn, String titleNa) {
        String name = fileIn.getName().toLowerCase();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        // make a temp location in sdcard
        String temp = Environment.getExternalStorageDirectory() + "/Android/data/" + context.getApplicationContext().getPackageName();
        File fileTemp = new File(temp);

        if (!fileTemp.exists()) {
            fileTemp.mkdirs();
        }

        // we create a new one
        try {

            fileTemp = new File(temp + File.separator + fileIn.getName());
            File fileFinal = exportFile(fileIn, fileTemp);

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uriFromFile(fileFinal));
            shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            if (name.contains("pdf")) {
                shareIntent.setType("application/pdf");
            } else if (name.contains("jpg") || name.contains("jpeg")) {
                shareIntent.setType("image/jpeg");
            } else if (name.contains("ppt") || name.contains("pptx")) {
                shareIntent.setType("application/vnd.ms-powerpoint");
            }

            context.startActivity(Intent.createChooser(shareIntent, "Share : " + titleNa));

        } catch (Exception ex) {
            ShowDialog.message(context, "Error while sharing document file.");
        }

    }

    public void sendMessageToWhatsAppContact(String message) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        try {
            String url = "https://wa.me/" + numberAdmin + "?text=" + message;
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(
                            url
                    )));
        } catch (Exception e) {
            ErrorLogger.write(context, e);
            e.printStackTrace();
        }
    }

}
