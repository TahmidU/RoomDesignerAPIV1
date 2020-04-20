package com.aarrd.room_designer.util;

public class Log {

    public static void printMsg(Class<?> fromClass, String msg)
    {
        if(fromClass.getEnclosingClass() != null)
            System.out.println(fromClass.getEnclosingClass().getName() + " -> " + fromClass.getEnclosingMethod().getName() + " :: " + msg);
        else
            System.out.println(fromClass.getName() + " :: " + msg);
    }

}
