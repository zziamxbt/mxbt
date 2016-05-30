package com.example.acer.zzia_mxbt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.acer.zzia_mxbt.R;

public class QiyuActivity extends AppCompatActivity {
    Intent intent=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qiyu);

    }

    public void userLogin(View view) {
        intent=new Intent(QiyuActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    public void userRegist(View view) {
        intent=new Intent(QiyuActivity.this,RegistActivity.class);
        startActivity(intent);
    }

}
