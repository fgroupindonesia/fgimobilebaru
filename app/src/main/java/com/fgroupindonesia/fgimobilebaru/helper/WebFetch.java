package com.fgroupindonesia.fgimobilebaru.helper;

import android.text.format.Formatter;

import androidx.appcompat.app.AppCompatActivity;

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

import java.io.File;
import java.util.List;
import java.util.Random;

public class WebFetch {


    // this is the 3rd party library called FETCH Library
    private String targetURL, filename, endResult, fileSize;
    AppCompatActivity myContext;
    NavigatorFetch webcallFetch;

    public WebFetch(AppCompatActivity ap, NavigatorFetch nv) {
        myContext = ap;
        webcallFetch = nv;

        prepareFetchLibrary();
    }

    private String getEndPath() {
        return endPath;
    }


    public void setTargetURL(String anURL) {
        targetURL = anURL;
    }

    public void setFileNameToBeSaved(String fn) {
        filename = fn;
    }

    public void setActivity(AppCompatActivity act) {
        myContext = act;
    }

    private Fetch fetchExecutor;

    private void prepareFetchLibrary() {
        FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(myContext)
                .setDownloadConcurrentLimit(3)
                .build();

        fetchExecutor = Fetch.Impl.getInstance(fetchConfiguration);
    }

    private String generateRandomKey() {

        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        // combine all strings
        String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

        // create random string builder
        StringBuilder sb = new StringBuilder();

        // create an object of Random class
        Random random = new Random();

        // specify length of random string
        int length = 25;

        for (int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(alphaNumeric.length());

            // get character specified by index
            // from the string
            char randomChar = alphaNumeric.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }

        String randomString = sb.toString();
        return randomString;
    }

    String endPath;
    Request requestFetch = null;

    public void executeFetch() {

        endPath = FileOpener.getSystemFilePath(myContext) + filename;

        requestFetch = new Request(targetURL, endPath);
        requestFetch.setPriority(Priority.HIGH);
        requestFetch.setNetworkType(NetworkType.ALL);
        requestFetch.addHeader("clientKey", generateRandomKey());

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
