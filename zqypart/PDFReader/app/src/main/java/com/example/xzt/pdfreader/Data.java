package com.example.xzt.pdfreader;

/**
 * Created by xzt on 2018/6/7.
 */

import android.app.Application;

public class Data extends Application{
    public static int THUMB=1;
    public static int INDEX=1<<1;
    public static int MIDDLE=1<<2;
    public static int RING=1<<3;
    public static int LITTLE=1<<4;
    public static int FINGER=THUMB|INDEX|MIDDLE|RING|LITTLE;
    public static boolean[] isFingerPress={false,false,false,false,false};
    public static String buffer="";
    public static int index=0;
    public static String getFinger(int key){
        String res="";
        boolean isSet=false;
        if((key&THUMB)!=0){
            res+="THUMB+";
            isSet=true;
        }
        if((key&INDEX)!=0){
            res+="INDEX+";
            isSet=true;
        }
        if((key&MIDDLE)!=0){
            res+="MIDDLE+";
            isSet=true;
        }
        if((key&RING)!=0){
            res+="RING+";
            isSet=true;
        }
        if((key&LITTLE)!=0){
            res+="LITTLE+";
            isSet=true;
        }
        if(isSet) {
            String temp = "";
            for (int i = 0; i < res.length() - 1; i++) {
                temp += res.charAt(i);
            }
            res = temp;
        }
        else{
            res="NONE";
        }
        return res;
    }
}
