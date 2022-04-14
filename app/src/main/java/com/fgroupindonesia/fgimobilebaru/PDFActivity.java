package com.fgroupindonesia.fgimobilebaru;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import android.widget.ImageView;

import com.fgroupindonesia.fgimobilebaru.helper.ErrorLogger;
import com.fgroupindonesia.fgimobilebaru.helper.Keys;
import com.fgroupindonesia.fgimobilebaru.helper.ShowDialog;
import com.fgroupindonesia.fgimobilebaru.helper.WhatsappSender;

import java.io.File;

public class PDFActivity extends AppCompatActivity {

    SubsamplingScaleImageView pdfView;

    ImageView imageViewNext, imageViewBack;

    File file;

    int currentIndex = 0;
    int pageCount = -1;
    ParcelFileDescriptor fileDescriptor = null;
    PdfRenderer pdfRenderer = null;
    PdfRenderer.Page rendererPage = null;
   String titleNa;

    public void next(View v) {

        if (currentIndex != 0) {
            currentIndex--;
        renderPage();
        }


    }

    public void shareToWhatsapp(View v){

        titleNa = file.getName();
        new WhatsappSender(this).sendFileToWhatsApp(file, titleNa);

    }

    public void back(View v) {

        if (currentIndex < pageCount) {
            currentIndex++;
            renderPage();
        }


    }

    private void toggleTheBackNextButtons(){

        if(pageCount==1){
            imageViewNext.setVisibility(View.INVISIBLE);
            imageViewBack.setVisibility(View.INVISIBLE);
        }

    }

    private void renderPage()  {


        try{
            fileDescriptor = ParcelFileDescriptor.open(
                    file, ParcelFileDescriptor.MODE_READ_ONLY);


            pdfRenderer = new PdfRenderer(fileDescriptor);

            if(pageCount==-1) {
                pageCount = pdfRenderer.getPageCount();

                // showing the next and back button
                // based upon the page count total found
                toggleTheBackNextButtons();

            }

            rendererPage = pdfRenderer.openPage(currentIndex);

            int rendererPageWidth = rendererPage.getWidth();
            int rendererPageHeight = rendererPage.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(
                    rendererPageWidth,
                    rendererPageHeight,
                    Bitmap.Config.ARGB_8888);
            rendererPage.render(bitmap, null, null,
                    PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

            if (bitmap == null) {
                // Important: the destination bitmap must be ARGB (not RGB).
                int newWidth = (int) (getResources().getDisplayMetrics().densityDpi * rendererPage.getWidth() / 72);
                int newHeight = (int) (getResources().getDisplayMetrics().densityDpi * rendererPage.getHeight() / 72);
                bitmap = Bitmap.createBitmap(
                        newWidth,
                        newHeight,
                        Bitmap.Config.ARGB_8888);
            }

            bitmap.eraseColor(0xFFFFFFFF);
            rendererPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            // We are ready to show the Bitmap to user.
            pdfView.resetScaleAndCenter();
            pdfView.setImage(ImageSource.cachedBitmap(bitmap));

            rendererPage.close();
            pdfRenderer.close();
            fileDescriptor.close();

        } catch (Exception n){
            ShowDialog.message(this, "something " + n.getMessage());
            ErrorLogger.write(this, n);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        pdfView = (SubsamplingScaleImageView) findViewById(R.id.pdfview);

        imageViewNext = (ImageView ) findViewById(R.id.imageViewNext);
        imageViewBack = (ImageView) findViewById(R.id.imageViewBack);

        String namaDicari = getIntent().getStringExtra(Keys.FILE_PDF_TARGET);
        try {
            Uri uri = null;
            // this is an absolute full completed file path
            file = new File(namaDicari);
            if (Build.VERSION.SDK_INT < 24) {
                uri = Uri.fromFile(file);
            } else {
                uri = Uri.parse(file.getPath()); // My work-around for SDKs up to 29.
            }

           // ShowDialog.message(this, "sudah betul " + uri);


            //min. API Level 21
            renderPage();

        } catch (Exception e) {
            ShowDialog.message(this, "error inside PDF Activity!");
        }

    }
}