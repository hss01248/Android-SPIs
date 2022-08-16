package com.hss01248.android.spi;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Despciption todo
 * @Author hss
 * @Date 09/08/2022 10:03
 * @Version 1.0
 */
public class AndroidSpisReadUtil {

    static final String PATH = "androidSpis/";

   public static List<String> findClasses(Context context, Class interfaceOrSuperClass){
         List<String> configs = new ArrayList<>();
        try {
            String[] startupclasses = context.getAssets().list(PATH +interfaceOrSuperClass.getName());
            Log.d("AndroidSpis","find classes in assets/"+ PATH +interfaceOrSuperClass.getName()+", list: "+ Arrays.toString(startupclasses));
            if(startupclasses != null && startupclasses.length >0){
                configs.addAll(Arrays.asList(startupclasses));
            }else {
                Log.w("AndroidSpis","not start up classes in assets/"+ PATH +interfaceOrSuperClass.getName());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return configs;
    }

    public static <T> List<T> findClassAndNewInstances(Context context, Class<T> interfaceOrSuperClass){
        List<T> objects = new ArrayList<>();
        List<String> classes = findClasses(context, interfaceOrSuperClass);
        if(classes.isEmpty()){
            return objects;
        }
        for (String aClass : classes) {
            try {
                Class<?> aClass1 =  Class.forName(aClass);
                T t = (T) aClass1.newInstance();
                objects.add(t);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return objects;
    }
/*  public   static    Object newInstance2(Class tClass){
        try {
            Method getInstance = tClass.getDeclaredMethod("getInstance");
            Object invoke =  getInstance.invoke(tClass);
            return invoke;
        } catch (Throwable e) {
            e.printStackTrace();
            try {
                return tClass.newInstance();
            } catch (Throwable ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }*/
}
