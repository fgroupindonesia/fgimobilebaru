package com.fgroupindonesia.fgimobilebaru;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.fgroupindonesia.fgimobilebaru.helper.Keys;
import com.fgroupindonesia.fgimobilebaru.helper.ShowDialog;

import java.io.File;
import java.net.URI;

public class PDFActivity extends AppCompatActivity {

    ImageView pdfView;

    float x1, y1, t1, x2 , y2, t2;
    int CLICK_DURATION = 200;

    File file;

    int currentIndex =0;
     int pageCount;
    ParcelFileDescriptor fileDescriptor = null;

    private void applyingSwipeEffect() {


        pdfView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {



                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        y1 = event.getY();
                        t1 = System.currentTimeMillis();
                        return true;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        y2 = event.getY();
                        t2 = System.currentTimeMillis();

                        try{
                            if (x1 == x2 && y1 == y2 && (t2 - t1) < CLICK_DURATION) {
                                ShowDialog.message(PDFActivity.this, "Click");
                            } else if ((t2 - t1) >= CLICK_DURATION) {
                                ShowDialog.message(PDFActivity.this, "Long Click");
                            } else if (x1 > x2) {
                                next();
                                renderPage();
                                ShowDialog.message(PDFActivity.this, "Left");
                            } else if (x2 > x1) {
                                back();
                                renderPage();
                                ShowDialog.message(PDFActivity.this, "Right");
                            }
                        } catch (Exception e){
                            ShowDialog.message(PDFActivity.this, "error while swiping...");
                            ShowDialog.message(PDFActivity.this, e.getMessage());
                        }



                        return true;
                }

                return false;
            }

        });

    }

    private void back(){

        if(currentIndex!=0){
            currentIndex--;
        }



    }

    private void next(){

        if(currentIndex<pageCount){
            currentIndex++;
        }


    }

    private void renderPage() throws Exception {

        fileDescriptor = ParcelFileDescriptor.open(
                file, ParcelFileDescriptor.MODE_READ_ONLY);

        PdfRenderer pdfRenderer  = new PdfRenderer(fileDescriptor);

        pageCount = pdfRenderer.getPageCount();

        //Display page 0
        PdfRenderer.Page rendererPage = pdfRenderer.openPage(currentIndex);
        int rendererPageWidth = rendererPage.getWidth();
        int rendererPageHeight = rendererPage.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(
                rendererPageWidth,
                rendererPageHeight,
                Bitmap.Config.ARGB_8888);
        rendererPage.render(bitmap, null, null,
                PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        pdfView.setImageBitmap(bitmap);
        rendererPage.close();

        pdfRenderer.close();
        fileDescriptor.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        pdfView = (ImageView)findViewById(R.id.pdfview);



        String namaDicari = getIntent().getStringExtra(Keys.FILE_PDF_TARGET);
try{
    Uri uri = null;
    // this is an absolute full completed file path
   file = new File(namaDicari);
    if (Build.VERSION.SDK_INT < 24) {
        uri = Uri.fromFile(file);
    } else {
        uri = Uri.parse(file.getPath()); // My work-around for SDKs up to 29.
    }

    ShowDialog.message(this, "sudah betul " + uri);



    //min. API Level 21
    renderPage();
    applyingSwipeEffect();

} catch (Exception e){
    ShowDialog.message(this, "error inside PDF Activity!");
}

    }
}