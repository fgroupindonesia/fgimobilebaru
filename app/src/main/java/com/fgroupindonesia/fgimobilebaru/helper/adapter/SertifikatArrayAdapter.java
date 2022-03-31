package com.fgroupindonesia.fgimobilebaru.helper.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fgroupindonesia.fgimobilebaru.SertifikasiActivity;
import com.fgroupindonesia.fgimobilebaru.R;
import com.fgroupindonesia.fgimobilebaru.helper.FileOpener;
import com.fgroupindonesia.fgimobilebaru.object.Sertifikat;

import java.io.File;
import java.util.ArrayList;

public class SertifikatArrayAdapter extends ArrayAdapter<Sertifikat> {
    private Context context;
    private ArrayList<Sertifikat> values;
    //private Activity act;
    private SertifikasiActivity dokAct;

    private static class ViewHolder {
        TextView txtTitle;
        TextView txtStatus;
        TextView txtDate;
        ImageView imageAccess;
        ImageView imageDoc, imageViewWhatsapp;
        ProgressBar progressBarDokumen;
        ProgressBar progressBarPercentage;
    }

    public void setActivity(AppCompatActivity actIn) {
        dokAct = (SertifikasiActivity) actIn;
    }

    private AppCompatActivity getActivity() {
        return dokAct;
    }


    private String getPath() {
        String path = Environment.getExternalStorageDirectory()
                + "/Android/data/" + context.getPackageName() + "/files";

        return path;
    }

    private File getFilePath(String filename){
        String mypath = getPath() + "/" + filename;
        return new File(mypath);
    }

    private boolean existLocally(String aFileName) {
        boolean stat = false;

        if (aFileName != null) {

            //+ getApplicationContext().getPackageName();
            File objFile = new File(getPath());

            if (!objFile.exists()) {
                objFile.mkdirs();
            }

            String mypath = getPath() + "/" + aFileName;
            objFile = new File(mypath);
            if (objFile.exists()) {
                stat = true;
            }
        }

        return stat;
    }

    public SertifikatArrayAdapter(Context context, ArrayList<Sertifikat> values) {
        super(context, R.layout.list_sertifikat, values);
        this.context = context;
        this.values = values;
    }

    public Sertifikat getItem(int post) {
        return values.get(post);
    }

    private void openingFile(String filename) {
        String namaDicari = new File(getPath(), filename).getAbsolutePath();
       // ShowDialog.message(getActivity(), "lokasi dari " + namaDicari);

        if(filename.contains("pdf")){
            dokAct.setCurrentFileName(namaDicari);
            dokAct.nextActivity();

        }else{
            FileOpener.openFile(getActivity(), new File(getPath(), filename));

        }

    }

    private void downloadFile(ImageView img, ProgressBar prgBar, ProgressBar prgBar2, String fileNa, String urlNa) {
        ((SertifikasiActivity) getActivity()).downloadFile(img, prgBar, prgBar2 , fileNa, urlNa);
      //  ShowDialog.message(getActivity(),"downloading...");
    }

    public void openingFile(int post, View v) {
        Sertifikat d = getItem(post);

        ViewHolder vh = (ViewHolder) v.getTag();

        String jenisIcon = null;

        if(vh.imageAccess.getTag() != null){
            jenisIcon = String.valueOf(vh.imageAccess.getTag());

            if (jenisIcon.contains("download")) {
                // downloading...
                //ShowDialog.message(getActivity(), "trying to download " + d.getUrl());
                // with progressbar shown
                downloadFile(vh.imageAccess, vh.progressBarDokumen, vh.progressBarPercentage,  d.getFilename(), d.getUrl());
                showAnimatedDownload(vh,true);
            } else if (jenisIcon.contains("checklist")) {
                // opening
                openingFile(d.getFilename());
            } else {
                openBrowser(d.getUrl());
            }

        }


    }

    private void shareFile(String fileNa, String titleNa) {
        ((SertifikasiActivity) getActivity()).shareTo(getFilePath(fileNa), titleNa);
        // ShowDialog.message(getActivity(),"di share...");
    }

    private void openBrowser(String aURL) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(aURL));
        getActivity().startActivity(browserIntent);
    }

    private String getFileNameWOExtension (String str) {
        // Handle null case specially.

        if (str == null) return null;

        // Get position of last '.'.

        int pos = str.lastIndexOf(".");

        // If there wasn't any '.' just return the string as is.

        if (pos == -1) return str;

        // Otherwise return the string, up to the dot.

        return str.substring(0, pos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Sertifikat dataModel = getItem(position);
        final ViewHolder viewHolder;
        View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_sertifikat, parent, false);

            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.textViewTitleDoc);
            viewHolder.txtStatus = (TextView) convertView.findViewById(R.id.textViewStatusDoc);
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.textViewDateDoc);
            viewHolder.imageDoc = (ImageView) convertView.findViewById(R.id.imageViewDocument);
            viewHolder.imageAccess = (ImageView) convertView.findViewById(R.id.imageViewAccessDocument);
            viewHolder.progressBarDokumen = (ProgressBar) convertView.findViewById(R.id.progressBarDokumen);
            viewHolder.progressBarPercentage = (ProgressBar) convertView.findViewById(R.id.progressBarPercentage);

            viewHolder.imageViewWhatsapp = (ImageView) convertView.findViewById(R.id.imageViewWhatsapp);


            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.imageViewWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Object refTag = viewHolder.imageAccess.getTag();
                String tag = null;

                if(refTag != null){
                    tag = String.valueOf(refTag);

                    if (tag.contains("checklist")) {
                        // we may directly share  the file
                        shareFile(dataModel.getFilename(), getFileNameWOExtension(dataModel.getFilename()));
                    }

                }

            }
        });

        viewHolder.imageAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView img = (ImageView) v;
                String tag = null;

                if(img.getTag() != null){
                    tag = String.valueOf(img.getTag());

                    if (tag.contains("download")) {
                        // we are required to download
                        downloadFile(viewHolder.imageAccess, viewHolder.progressBarDokumen, viewHolder.progressBarPercentage, dataModel.getFilename(), dataModel.getUrl());
                        showAnimatedDownload(viewHolder,true);
                    } else if (tag.contains("checklist")) {
                        // we may directly open the file locally
                        openingFile(dataModel.getFilename());
                    } else if (tag.contains("browser")) {
                        // we just open browser
                        openBrowser(dataModel.getUrl());
                    }

                }

            }
        });


        //imageViewAccessDoc.setImageResource(R.drawable.checklist);
        String statusNa = null;

        if(dataModel.getStatus()==1){
            statusNa = "Status : Available";
        }else {
            statusNa = "Status : Not Available";
        }

        if (existLocally(dataModel.getFilename())) {
            viewHolder.imageAccess.setImageResource(R.drawable.checklist);
            viewHolder.imageAccess.setTag("checklist");
            viewHolder.txtStatus.setText(statusNa);
            viewHolder.imageViewWhatsapp.setVisibility(View.VISIBLE);
            showAnimatedDownload(viewHolder,false);
        } else {
            viewHolder.imageAccess.setImageResource(R.drawable.download);
            viewHolder.imageAccess.setTag("download");
            viewHolder.imageViewWhatsapp.setVisibility(View.INVISIBLE);
            viewHolder.txtStatus.setText(statusNa);
        }


        viewHolder.txtTitle.setText(dataModel.getFilename());
        viewHolder.txtDate.setText(dataModel.getExam_date_created());

        if (dataModel.getFilename() != null) {
            if (dataModel.getFilename().contains(".pdf")) {
                viewHolder.imageDoc.setImageResource(R.drawable.pdf);
            } else if (dataModel.getFilename().contains(".jpg") || dataModel.getFilename().contains(".jpeg")) {
                viewHolder.imageDoc.setImageResource(R.drawable.jpg);
            } else if (dataModel.getFilename().contains(".png")) {
                viewHolder.imageDoc.setImageResource(R.drawable.png);
            } else {
                viewHolder.imageDoc.setImageResource(R.drawable.file);
            }

        }
        return convertView;
    }

    private void showAnimatedDownload(ViewHolder vhold, boolean b){
        if(b) {
            vhold.progressBarDokumen.setVisibility(View.VISIBLE);
            vhold.imageAccess.setVisibility(View.GONE);
        }else{
            vhold.progressBarDokumen.setVisibility(View.GONE);
            vhold.imageAccess.setVisibility(View.VISIBLE);
        }
    }


}
