package com.tavarlabs.tavarsystem.utils;

public class AppFunctions {
    public static boolean differentStrings(String currentVal, String newVal){
        if(currentVal == null || newVal == null) return true;
        return !currentVal.equalsIgnoreCase(newVal);
    }
}
