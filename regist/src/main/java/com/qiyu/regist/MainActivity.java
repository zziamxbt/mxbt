package com.qiyu.regist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Intent intent=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void userLogin(View view) {
        intent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    public void userRegist(View view) {
        intent=new Intent(MainActivity.this,RegistActivity.class);
        startActivity(intent);
    }

}
