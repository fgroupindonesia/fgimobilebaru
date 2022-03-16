package com.fgroupindonesia.fgimobilebaru.helper;

import com.fgroupindonesia.fgimobilebaru.object.Document;
import com.fgroupindonesia.fgimobilebaru.object.RemoteLoginClient;
import com.fgroupindonesia.fgimobilebaru.object.Sertifikat;

import java.util.ArrayList;

public class ArrayHelper {

    public static ArrayList<Sertifikat> fillArrayList(ArrayList <Sertifikat> dataOut, Sertifikat [] dataIn){

        for(Sertifikat satuan: dataIn){
            dataOut.add(satuan);
        }

        return dataOut;

    }


    public static ArrayList<Document> fillArrayList(ArrayList <Document> dataOut, Document [] dataIn){

        for(Document satuan: dataIn){
            dataOut.add(satuan);
        }

        return dataOut;

    }

    public static ArrayList<RemoteLoginClient> fillArrayList(ArrayList <RemoteLoginClient> dataOut, RemoteLoginClient [] dataIn){

        for(RemoteLoginClient satuan: dataIn){
            dataOut.add(satuan);
        }

        return dataOut;

    }

    public static ArrayList<RemoteLoginClient> copyBackRemoteLoginClient(ArrayList<RemoteLoginClient> arraySource, ArrayList<RemoteLoginClient> arrayTarget){

        arrayTarget.clear();
        for(RemoteLoginClient d:arraySource){
            arrayTarget.add(d);
        }

        return arrayTarget;

    }

    public static ArrayList<Document> copyBackDocument(ArrayList<Document> arraySource, ArrayList<Document> arrayTarget){

        arrayTarget.clear();
        for(Document d:arraySource){
            arrayTarget.add(d);
        }

        return arrayTarget;

    }

}
