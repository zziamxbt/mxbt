package com.example.acer.zzia_mxbt.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.acer.zzia_mxbt.R;
import com.facebook.drawee.backends.pipeline.Fresco;

public class CenterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(CenterActivity.this);
        setContentView(R.layout.activity_center);

    }
}
