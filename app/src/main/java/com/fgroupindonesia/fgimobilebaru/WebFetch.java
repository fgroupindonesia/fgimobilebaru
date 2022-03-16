package com.fgroupindonesia.fgimobilebaru;

import androidx.appcompat.app.AppCompatActivity;

import com.fgroupindonesia.fgimobilebaru.helper.FileOpener;
import com.fgroupindonesia.fgimobilebaru.helper.NavigatorFetch;
import com.fgroupindonesia.fgimobilebaru.helper.ShowDialog;
import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Error;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2.FetchListener;
import com.tonyodev.fetch2.NetworkType;
import com.tonyodev.fetch2.Priority;
import com.tonyodev.fetch2.Request;
import com.tonyodev.fetch2core.DownloadBlock;
import com.tonyodev.fetch2core.Func;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WebFetch {


    // this is the 3rd party library called FETCH Library
    private String targetURL, filename, endResult;
    AppCompatActivity myContext;
    NavigatorFetch webcallFetch;

    public WebFetch(String urlIn, String fileNameSaved){
        targetURL = urlIn;
        filename = fileNameSaved;
    }

    public void setActivity(AppCompatActivity act){
        myContext = act;
    }

    public void setNavigatorFetch( NavigatorFetch nv){
        webcallFetch = nv;
    }

    private Fetch fetchExecutor;
    public void prepareFetchLibrary(){
        FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(myContext)
                .setDownloadConcurrentLimit(3)
                .build();

        fetchExecutor = Fetch.Impl.getInstance(fetchConfiguration);
    }



    Request requestFetch = null;
    public void executeFetch(){

        final String endPath = FileOpener.getSystemFilePath(myContext) + filename;

        requestFetch = new Request(targetURL, endPath);
        requestFetch.setPriority(Priority.HIGH);
        requestFetch.setNetworkType(NetworkType.ALL);
        requestFetch.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG");

        FetchListener fetchListener = new FetchListener() {
            @Override
            public void onWaitingNetwork(@NotNull Download download) {

            }

            @Override
            public void onStarted(@NotNull Download download, @NotNull List<? extends DownloadBlock> list, int i) {

            }

            @Override
            public void onError(@NotNull Download download, @NotNull Error error, @Nullable Throwable throwable) {

            }

            @Override
            public void onDownloadBlockUpdated(@NotNull Download download, @NotNull DownloadBlock downloadBlock, int i) {

            }

            @Override
            public void onAdded(@NotNull Download download) {

            }

            @Override
            public void onQueued(@NotNull Download download, boolean waitingOnNetwork) {
                if (requestFetch.getId() == download.getId()) {
                    //showDownloadInList(download);
                }
            }

            @Override
            public void onCompleted(@NotNull Download download) {
                // execute the same interface
                // the respond is actually the file path completely

                endResult = endPath;

                webcallFetch.onSuccessByFetch(targetURL, endResult);
                ShowDialog.message(myContext, "fetch is completed!");

            }



            @Override
            public void onProgress(@NotNull Download download, long etaInMilliSeconds, long downloadedBytesPerSecond) {
                if (requestFetch.getId() == download.getId()) {
                    //updateDownload(download, etaInMilliSeconds);
                }
                int progress = download.getProgress();
            }

            @Override
            public void onPaused(@NotNull Download download) {

            }

            @Override
            public void onResumed(@NotNull Download download) {

            }

            @Override
            public void onCancelled(@NotNull Download download) {

            }

            @Override
            public void onRemoved(@NotNull Download download) {

            }

            @Override
            public void onDeleted(@NotNull Download download) {

            }
        };

        fetchExecutor.addListener(fetchListener);

        fetchExecutor.enqueue(requestFetch, new Func<Request>() {
            @Override
            public void call(@NotNull Request updatedRequest) {
                //Request was successfully enqueued for download.
            }
        }, new Func<Error>() {
            @Override
            public void call(@NotNull Error error) {
                //An error occurred enqueuing the request.
            }
        });


    }

}
