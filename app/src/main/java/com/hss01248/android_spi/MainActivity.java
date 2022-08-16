package com.hss01248.android_spi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.hss01248.android.spi.AndroidSpisReadUtil;
import com.hss01248.module_test.ICallbackTest1;
import com.hss01248.module_test_java_spi.ICallbackTestJava;


import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> classes = AndroidSpisReadUtil.findClasses(getApplicationContext(), ICallbackTest1.class);

        List<ICallbackTest1> classAndNewInstances = AndroidSpisReadUtil.findClassAndNewInstances(getApplicationContext(), ICallbackTest1.class);
        Log.w("AndroidSpis", Arrays.toString(classAndNewInstances.toArray()));



        javaSpi();
    }

    private void javaSpi() {
        ServiceLoader<ICallbackTestJava> serviceLoader = ServiceLoader.load(ICallbackTestJava.class);
        Log.w("AndroidSpis2", "java spi: "+serviceLoader.toString());

//遍历服务
        for (ICallbackTestJava impl : serviceLoader) {
            impl.run2();
        }
    }
}