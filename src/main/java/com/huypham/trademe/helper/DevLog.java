package com.huypham.trademe.helper;

public class DevLog {
    public static void print(Object whatClass, String msg){
        System.out.println("[" + whatClass.getClass().getName() + "] " + msg);
    }
}
