package com.fgroupindonesia.fgimobilebaru.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;

import java.io.File;

public class WhatsappSender {
    Context context;
    private String numberAdmin = "+6285795569337";

    public WhatsappSender(Context x){
        context = x;
    }

    private Uri uriFromFile(File file)  {
        return Uri.fromFile(file);
    }

    public void sendFileToWhatsApp(File fileIn, String titleNa){
        String name = fileIn.getName().toLowerCase();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM,  uriFromFile(fileIn));
        shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if(name.contains("pdf")) {
            shareIntent.setType("application/pdf");
        }else if(name.contains("jpg") || name.contains("jpeg")){
            shareIntent.setType("image/jpeg");
        }else if(name.contains("ppt") || name.contains("pptx")){
            shareIntent.setType("application/vnd.ms-powerpoint");
        }

        context.startActivity(Intent.createChooser(shareIntent, "Share : " + titleNa));

    }

    public void sendMessageToWhatsAppContact(String message) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        try {
            String url = "https://wa.me/"+ numberAdmin + "?text=" + message;
           context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(
                            url
                    )));
        } catch (Exception e) {

            ErrorLogger.write(e);
            e.printStackTrace();
        }
    }

}
