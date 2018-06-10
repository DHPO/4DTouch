package com.example.xzt.pdfreader;

/**
 * Created by xzt on 2018/6/7.
 */

import android.app.Application;

public class Data extends Application{
    public static int pageturnKey=2;
    public static int writeKey=1;
    public static int settingKey=0;
    public static boolean[] isFingerPress={false,false,false,false,false};
    public static String getFinger(int key){
        switch(key){
            case 0:
                return "Thumb";
            case 1:
                return "Index finger";
            case 2:
                return "Middle finger";
            case 3:
                return "Ring finger";
            case 4:
                return "Little finger";
            default:
                return "Error";
        }
    }
}
