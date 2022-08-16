package com.hss01248.module_test_java_spi2;

import android.util.Log;

import androidx.annotation.Keep;

import com.google.auto.service.AutoService;
import com.hss01248.module_test_java_spi.ICallbackTestJava;

/**
 * @Despciption todo
 * @Author hss
 * @Date 16/08/22 17:12
 * @Version 1.0
 */
@Keep
@AutoService(ICallbackTestJava.class)
public class CallbackTestJavaImpl implements ICallbackTestJava{
    @Override
    public void run2() {
        Log.w("java spi","CallbackTestJavaImpl---> run--module_test_java_spi2");

    }
}
