package com.fgroupindonesia.fgimobilebaru;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fgroupindonesia.fgimobilebaru.helper.HistoryHelper;
import com.fgroupindonesia.fgimobilebaru.helper.ImageHelper;
import com.fgroupindonesia.fgimobilebaru.helper.Keys;
import com.fgroupindonesia.fgimobilebaru.helper.Navigator;
import com.fgroupindonesia.fgimobilebaru.helper.RespondHelper;
import com.fgroupindonesia.fgimobilebaru.helper.ShowDialog;
import com.fgroupindonesia.fgimobilebaru.helper.UIHelper;
import com.fgroupindonesia.fgimobilebaru.helper.URLReference;
import com.fgroupindonesia.fgimobilebaru.helper.WebRequest;
import com.fgroupindonesia.fgimobilebaru.helper.shared.UserData;
import com.fgroupindonesia.fgimobilebaru.object.Bill;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.io.File;

public class BillActivity extends AppCompatActivity implements Navigator {

    TextView textViewTagihanRupiah,textViewTagihanTanggalRilis,
            textViewTagihanDescription, textViewTagihanStatus;

    Button buttonTagihanNantiDulu, buttonTagihanBayarSekarang, buttonTagihanUnggahBuktiPembayaran;

    ImageView imageViewBill, imageViewPrevBill , imageViewNextBill;

    LinearLayout loadingLayout, billLayout;
    String filePath;
    int totalBills, billIndex;
    Bill [] dataBillsIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        // for shared preference
        UserData.setPreference(this);

        loadingLayout = (LinearLayout) findViewById(R.id.linearBillLoading);
        billLayout = (LinearLayout) findViewById(R.id.linearBillDetail);

        imageViewBill = (ImageView) findViewById(R.id.imageViewBill);
        imageViewPrevBill = (ImageView) findViewById(R.id.imageViewPrevBill);
        imageViewNextBill = (ImageView) findViewById(R.id.imageViewNextBill);

        textViewTagihanStatus = (TextView) findViewById(R.id.textViewTagihanStatus);
        textViewTagihanDescription = (TextView) findViewById(R.id.textViewTagihanDescription);
        textViewTagihanTanggalRilis = (TextView) findViewById(R.id.textViewTagihanTanggalRilis);
        textViewTagihanRupiah = (TextView) findViewById(R.id.textViewTagihanRupiah);

        buttonTagihanBayarSekarang = (Button) findViewById(R.id.buttonTagihanBayarSekarang);
        buttonTagihanNantiDulu = (Button) findViewById(R.id.buttonTagihanNantiDulu);
        buttonTagihanUnggahBuktiPembayaran = (Button) findViewById(R.id.buttonTagihanUnggahBuktiPembayaran);

        // show the loading first before the real bill layout
        showBillLayout(false);

        // calling to Server API wait after certain seconds
        // is there any bill for this user?
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getAllBills();
            }
        }, Keys.PERIOD_OF_TIME_WAIT_GENERAL);

        // for the title removal
        //UIHelper.toggleTitleApp(this);


    }

    public void nextBill(View v){
        if(billIndex!=totalBills) {
            billIndex++;
        }

        renderBillData();
    }

    public void prevBill(View v){
        if(billIndex!=0){
            billIndex--;
        }

        renderBillData();
    }

    // for History purposes track record
    private void addRecordHistory(String data, String token, String rpCash){
        HistoryHelper hper = new HistoryHelper();
        hper.uploadingPayment(data, token, rpCash, this);
    }

    private void renderBillData(){
        if(totalBills<=1){
            imageViewPrevBill.setVisibility(View.INVISIBLE);
            imageViewNextBill.setVisibility(View.INVISIBLE);
        }else if(totalBills>=2) {

            if(billIndex==totalBills-1){
                imageViewPrevBill.setVisibility(View.VISIBLE);
                imageViewNextBill.setVisibility(View.INVISIBLE);
            }else if(billIndex>0){
                imageViewPrevBill.setVisibility(View.VISIBLE);
                imageViewNextBill.setVisibility(View.VISIBLE);
            }else if(billIndex==0){
                imageViewPrevBill.setVisibility(View.INVISIBLE);
                imageViewNextBill.setVisibility(View.VISIBLE);
            }

        }



        textViewTagihanRupiah.setText(UIHelper.formatRupiah(dataBillsIn[billIndex].getAmount()));
        textViewTagihanStatus.setText("Status : " + UIHelper.convertStatusToIndonesia(dataBillsIn[billIndex].getStatus()));
        textViewTagihanDescription.setText(dataBillsIn[billIndex].getDescription());
        textViewTagihanTanggalRilis.setText("Tanggal rilis : " + UIHelper.convertDateToIndonesia(dataBillsIn[billIndex].getDate_created()));

        // in case he already upload the approval
        // we will hide all buttons
        if(dataBillsIn[billIndex].getStatus().equalsIgnoreCase("unpaid") ){
            hideAllButtons(false);
            // when not paid yet
            imageViewBill.setImageResource(R.drawable.cash);
        }else if(dataBillsIn[billIndex].getStatus().equalsIgnoreCase("paid")){
            hideAllButtons(true);
            // when paid and completed
            imageViewBill.setImageResource(R.drawable.cash_lunas);
        }else if(dataBillsIn[billIndex].getStatus().equalsIgnoreCase("pending")){
            hideAllButtons(true);
            // when pending waiting confirmation
            imageViewBill.setImageResource(R.drawable.cash_pending);
        }
    }


    public void updateBill(){

        WebRequest httpCall = new WebRequest(BillActivity.this, BillActivity.this);
        httpCall.addData("username", UserData.getPreferenceString(Keys.USERNAME));
        httpCall.addData("token", UserData.getPreferenceString(Keys.TOKEN));
        httpCall.addData("amount", String.valueOf(dataBillsIn[billIndex].getAmount()));
        httpCall.addData("description", dataBillsIn[billIndex].getDescription());
        httpCall.addData("id", String.valueOf(dataBillsIn[billIndex].getId()));

        if(filePath!=null) {
            httpCall.addFile("screenshot", new File(filePath));
        }

        httpCall.setWaitState(true);
        // for uploading image
        httpCall.setMultipartform(true);
        httpCall.setRequestMethod(WebRequest.POST_METHOD);
        httpCall.setTargetURL(URLReference.BillPaid);
        httpCall.execute();

    }

    public void getAllBills(){

        WebRequest httpCall = new WebRequest(BillActivity.this, BillActivity.this);
        httpCall.addData("username", UserData.getPreferenceString(Keys.USERNAME));
        httpCall.addData("token", UserData.getPreferenceString(Keys.TOKEN));

        httpCall.setWaitState(true);
        httpCall.setRequestMethod(WebRequest.POST_METHOD);
        httpCall.setTargetURL(URLReference.BillAll);
        httpCall.execute();

    }

    public void unggahBukti(View v){
        selectImage();
    }


    public void nantiDulu(View v){
        finish();
    }


    public void bayarSekarang(View v){
        buttonTagihanBayarSekarang.setVisibility(View.GONE);
        buttonTagihanNantiDulu.setVisibility(View.GONE);
        buttonTagihanUnggahBuktiPembayaran.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case Keys.ACT_CAMERA_BILL:
                    if (resultCode == RESULT_OK && data != null) {
                        //Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        //imageView.setImageBitmap(selectedImage);

                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        //imageView.setImageBitmap(photo);
                        //knop.setVisibility(Button.VISIBLE);


                        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                        Uri tempUri = ImageHelper.getImageUri(getApplicationContext(), photo);

                        // CALL THIS METHOD TO GET THE ACTUAL PATH
                        filePath = ImageHelper.getRealPathFromURI(this, tempUri);
                        filePath = ImageHelper.convertToSmallJPG(this, filePath, "payment");

                        textViewTagihanStatus.setText("didapatlah " + filePath);
                        ShowDialog.message(this, "didapatlah " + filePath);

                        updateBill();

                    }

                    break;
                case Keys.ACT_GALLERY_BILL:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        filePath = ImageHelper.getPath(this.getApplicationContext(), selectedImage);
                        //ShowDialog.message(UserProfileActivity.this, picturePath);

                        // lets convert it to png to make it save for any server
                        filePath = ImageHelper.convertToSmallJPG(this, filePath, "payment");

                        // showing the loading layout
                        //showBillLayout(false);
                        //updateBill();


                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                filePath = cursor.getString(columnIndex);

                                //imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

                                // lets convert it to png to make it save for any server
                                filePath = ImageHelper.convertToSmallJPG(this, filePath, "payment");

                                cursor.close();

                                // showing the loading layout
                                showBillLayout(false);
                                updateBill();
                            }
                        }

                        break;
                    }

            }
        }
    }


    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Unggah Bukti Pembayaran");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, Keys.ACT_CAMERA_BILL);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , Keys.ACT_GALLERY_BILL);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void showBillLayout(boolean b){
        if(b) {
            billLayout.setVisibility(View.VISIBLE);
            loadingLayout.setVisibility(View.GONE);
        } else{
            billLayout.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFailed(){
        // usually because no internet

    }

    @Override
    public void nextActivity() {

    }

    @Override
    public void onSuccess(String urlTarget, String respond) {

        try {
            Gson objectG = new Gson();

            if (RespondHelper.isValidRespond(respond)) {

                if (urlTarget.contains(URLReference.BillAll)) {

                    showBillLayout(true);

                    JSONArray jsons = RespondHelper.getArray(respond, "multi_data");

                    JsonParser parser = new JsonParser();
                    JsonElement mJson =  parser.parse(jsons.toString());

                    dataBillsIn  = objectG.fromJson(mJson, Bill[].class);

                    billIndex = 0;
                    totalBills = dataBillsIn.length;

                   renderBillData();

                }else if (urlTarget.contains(URLReference.BillPaid)) {

                    // this is when updating the payment bill
                    dataBillsIn[billIndex].setStatus("pending");
                    textViewTagihanStatus.setText("Status : " + UIHelper.convertStatusToIndonesia(dataBillsIn[billIndex].getStatus()));

                    showBillLayout(true);
                    hideAllButtons(true);

                    // store the record on server
                    String username = UserData.getPreferenceString(Keys.USERNAME);
                    String token = UserData.getPreferenceString(Keys.TOKEN);
                    String cash = "Rp." + dataBillsIn[billIndex].getAmount();
                    addRecordHistory(username, token, cash);
                } else if(urlTarget.contains(URLReference.HistoryAdd)){
                    ShowDialog.message(this, "Bukti pembayaran telah terupload! Harap menunggu konfirmasi admin.");
                    finish();
                }

            } else if (!RespondHelper.isValidRespond(respond)) {

                ShowDialog.message(this, "tidak ada tagihan terkini");
                finish();

            }
        } catch (Exception ex) {
            ShowDialog.message(this, "Error " + ex.getMessage());
            ex.printStackTrace();
        }


    }

    private void hideAllButtons(boolean b){
        if (b){
        buttonTagihanNantiDulu.setVisibility(View.GONE);
        buttonTagihanBayarSekarang.setVisibility(View.GONE);
        buttonTagihanUnggahBuktiPembayaran.setVisibility(View.GONE);
        }else{

            buttonTagihanNantiDulu.setVisibility(View.VISIBLE);
            buttonTagihanBayarSekarang.setVisibility(View.VISIBLE);
            buttonTagihanUnggahBuktiPembayaran.setVisibility(View.GONE);

        }

    }

}