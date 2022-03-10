package com.fgroupindonesia.fgimobilebaru.helper;

import com.fgroupindonesia.fgimobilebaru.object.Schedule;

import java.util.ArrayList;

public class ScheduleSummary {

    ArrayList<Schedule> list = new ArrayList<Schedule>();

    public String getText(){

        ArrayList <String> classReg = new ArrayList<String>();
      // get all the regclass of this user
        for(Schedule s: list){
            if(!classReg.contains(s.getClass_registered())){
                classReg.add(s.getClass_registered());
            }
        }

        // now abtaining the time & day output
        // for each classes

        // CLASS-1 : day1 hour:mm & day2 hour:mm
        // example:
        // Java web : senin 10:00 & kamis 13:00
        StringBuffer stb = new StringBuffer("");
        StringBuffer stbFinal  = new StringBuffer("");
        int rotation = 0;

        for(String classNameReg: classReg){
            for(Schedule s: list){
                if(s.getClass_registered().equalsIgnoreCase(classNameReg)){
                    if(rotation==0) {
                        stb.append(s.getDay_schedule() + " " + s.getTime_schedule());
                    }else{
                        stb.append(" & " + s.getDay_schedule() + " " + s.getTime_schedule());
                    }
                }
            rotation++;
            }

            stbFinal.append(classNameReg + " : " + stb.toString() + "  ");

            // get the rotation back to zero
            // for each class checking
            rotation = 0;
        }

        return stbFinal.toString();

    }

    private void addData(Schedule in){
        list.add(in);
    }

    public ScheduleSummary(Schedule dataIn){
        addData(dataIn);
    }

    public ScheduleSummary(Schedule [] dataIn){
        for(Schedule s: dataIn){
            addData(s);
        }
    }

}
