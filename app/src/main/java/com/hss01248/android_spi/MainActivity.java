package com.hss01248.android_spi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hss01248.android.spi.AndroidSpisReadUtil;
import com.hss01248.module_test.ICallbackTest1;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> classes = AndroidSpisReadUtil.findClasses(getApplicationContext(), ICallbackTest1.class);
    }
}