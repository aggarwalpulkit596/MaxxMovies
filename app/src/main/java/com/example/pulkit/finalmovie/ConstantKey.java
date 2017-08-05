package com.example.pulkit.finalmovie;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Pulkit on 8/2/2017.
 */

public class ConstantKey {
    public static String YOUTUBE_API = "AIzaSyDeJ4_bztNQv7xDZUeehvC0ZsHf9fjy79o";
    public static String MOVIEDB_API = "a908fc789577ee9ead5deab36bcfbc1b";
    public static String TOKEN;
    public static String SESSION;
    public static Integer ID;

    public static String getSESSION() {
        return SESSION;
    }

    public static void setSESSION(String SESSION) {
        ConstantKey.SESSION = SESSION;
    }

    public static Integer getID() {
        return ID;
    }
    public static void setID(Integer ID) {
        ConstantKey.ID = ID;
    }
    public static String getNAME() {
        return NAME;
    }

    public static void setNAME(String NAME) {
        ConstantKey.NAME = NAME;
    }

    public static String NAME;

    public static String getTOKEN() {
        return TOKEN;
    }

    public static void setTOKEN(String TOKEN) {
        ConstantKey.TOKEN = TOKEN;
    }

}
