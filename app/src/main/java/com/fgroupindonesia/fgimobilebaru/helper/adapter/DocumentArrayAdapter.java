package com.fgroupindonesia.fgimobilebaru.helper.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fgroupindonesia.fgimobilebaru.object.Document;
import com.fgroupindonesia.fgimobilebaru.DokumenActivity;
import com.fgroupindonesia.fgimobilebaru.R;
import com.fgroupindonesia.fgimobilebaru.helper.FileOpener;

import java.io.File;
import java.util.ArrayList;

public class DocumentArrayAdapter extends ArrayAdapter<Document> {
    private Context context;
    private ArrayList<Document> values;
    //private Activity act;
    private DokumenActivity dokAct;

    private static class ViewHolder {
        TextView txtTitle;
        TextView txtSize;
        TextView txtDate;
        ImageView imageAccess;
        ImageView imageDoc;
        ProgressBar progressBarDokumen;
        ProgressBar progressBarPercentage;
        ImageView imageViewWhatsapp;
    }

    public void setActivity(AppCompatActivity actIn) {
        dokAct = (DokumenActivity) actIn;
    }

    private AppCompatActivity getActivity() {
        return dokAct;
    }


    private String getPath() {
        // we exclude this one
        // String path = Environment.getExternalStorageDirectory()
        //         + "/Android/data/" + context.getPackageName() + "/files";


        // with the new one for internal access usage
        String path = FileOpener.getSystemFilePath(getActivity());
        return path;
    }

    private File getFilePath(String filename){
        String mypath = getPath() + filename;
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

            objFile = getFilePath(aFileName);
            if (objFile.exists()) {
                stat = true;
            }
        }

        return stat;
    }

    public DocumentArrayAdapter(Context context, ArrayList<Document> values) {
        super(context, R.layout.list_document, values);
        this.context = context;
        this.values = values;
    }

    public Document getItem(int post) {
        return values.get(post);
    }

    private void openingFile(String filename) {
        String namaDicari = getFilePath(filename).getAbsolutePath();
        //ShowDialog.message(getActivity(), "lokasi ke " + namaDicari);

        if(filename.contains("pdf")){
            dokAct.setCurrentFileName(namaDicari);
            dokAct.nextActivity();
        }else{
            FileOpener.openFile(getActivity(), new File(getPath(), filename));

        }
    }

    private void downloadFile(TextView txt, ImageView img, ProgressBar prgBar1, ProgressBar prgBar2, String fileNa, String urlNa) {
        ((DokumenActivity) getActivity()).downloadFile(txt, img, prgBar1, prgBar2, fileNa, urlNa);
        //ShowDialog.message(getActivity(),"downloading...");
    }


    private void shareFile(String fileNa, String titleNa) {
        ((DokumenActivity) getActivity()).shareTo(getFilePath(fileNa), titleNa);
       // ShowDialog.message(getActivity(),"di share...");
    }

    public void openingFile(int post, View v) {
        Document d = getItem(post);

        ViewHolder vh = (ViewHolder) v.getTag();

        String jenisIcon = null;

        if(vh.imageAccess.getTag() != null){
           jenisIcon = String.valueOf(vh.imageAccess.getTag());

            if (jenisIcon.contains("download")) {
                // downloading...
                //ShowDialog.message(getActivity(), "trying to download " + d.getUrl());
                // with progressbar shown
                downloadFile(vh.txtSize, vh.imageAccess, vh.progressBarPercentage, vh.progressBarDokumen, d.getFilename(), d.getUrl());
                showAnimatedDownload(vh,true);
            } else if (jenisIcon.contains("checklist")) {
                // opening
                openingFile(d.getFilename());
            } else {
                openBrowser(d.getUrl());
            }

        }



    }

    private void openBrowser(String aURL) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(aURL));
        getActivity().startActivity(browserIntent);
    }

    private String getFileSize(String aFileName) {
        if (aFileName != null) {

            return "size : " + Formatter.formatShortFileSize(context, getFilePath(aFileName).length());
        }

        return "size : 0";
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // convertView.setBackgroundColor(0);

        final Document dataModel = getItem(position);
        final ViewHolder viewHolder;
        View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_document, parent, false);

            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.textViewTitleDoc);
            viewHolder.txtSize = (TextView) convertView.findViewById(R.id.textViewSizeDoc);
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
                        shareFile(dataModel.getFilename(), dataModel.getTitle());
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
                        downloadFile(viewHolder.txtSize, viewHolder.imageAccess, viewHolder.progressBarDokumen, viewHolder.progressBarPercentage, dataModel.getFilename(), dataModel.getUrl());
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
        if (existLocally(dataModel.getFilename())) {
            viewHolder.imageAccess.setImageResource(R.drawable.checklist);
            viewHolder.imageAccess.setTag("checklist");
            viewHolder.txtSize.setText(getFileSize(dataModel.getFilename()));
            viewHolder.imageViewWhatsapp.setVisibility(View.VISIBLE);
            showAnimatedDownload(viewHolder,false);
        } else {
            viewHolder.imageAccess.setImageResource(R.drawable.download);
            viewHolder.imageAccess.setTag("download");
            viewHolder.txtSize.setText("click to download");
            viewHolder.imageViewWhatsapp.setVisibility(View.INVISIBLE);
        }

        viewHolder.txtTitle.setText(dataModel.getTitle());
        viewHolder.txtDate.setText(dataModel.getDate_created());


        if (dataModel.getFilename() != null) {
            if (dataModel.getFilename().contains(".rar")) {
                viewHolder.imageDoc.setImageResource(R.drawable.rar);
            } else if (dataModel.getFilename().contains(".zip")) {
                viewHolder.imageDoc.setImageResource(R.drawable.zip);
            } else if (dataModel.getFilename().contains(".pdf")) {
                viewHolder.imageDoc.setImageResource(R.drawable.pdf);
            } else if (dataModel.getFilename().contains(".png")) {
                viewHolder.imageDoc.setImageResource(R.drawable.png);
            } else if (dataModel.getFilename().contains(".doc") || dataModel.getFilename().contains(".docx")) {
                viewHolder.imageDoc.setImageResource(R.drawable.document);
            } else if (dataModel.getFilename().contains(".psd")) {
                viewHolder.imageDoc.setImageResource(R.drawable.ps);
            } else if (dataModel.getFilename().contains(".jpg") || dataModel.getFilename().contains(".jpeg")) {
                viewHolder.imageDoc.setImageResource(R.drawable.jpg);
            } else if (dataModel.getFilename().contains(".wav") || dataModel.getFilename().contains(".mp3")) {
                viewHolder.imageDoc.setImageResource(R.drawable.audio);
            } else if (dataModel.getFilename().contains(".xls") || dataModel.getFilename().contains(".xlsx")) {
                viewHolder.imageDoc.setImageResource(R.drawable.excel);
            } else if (dataModel.getFilename().contains(".ppt") || dataModel.getFilename().contains(".pptx")) {
                viewHolder.imageDoc.setImageResource(R.drawable.ppoint);
            } else {

                viewHolder.imageDoc.setImageResource(R.drawable.file);


            }


        } else if (dataModel.getUrl().contains("tube")) {
            viewHolder.imageDoc.setImageResource(R.drawable.youtube);
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
