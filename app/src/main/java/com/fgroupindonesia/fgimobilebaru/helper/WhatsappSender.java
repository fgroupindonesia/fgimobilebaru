package com.fgroupindonesia.fgimobilebaru.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class WhatsappSender {
    Context context;
    private String numberAdmin = "+6285795569337";

    public WhatsappSender(Context x){
        context = x;
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
