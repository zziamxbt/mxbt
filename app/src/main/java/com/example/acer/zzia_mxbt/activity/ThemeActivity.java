package com.example.acer.zzia_mxbt.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.adapters.ThemeAdapter;
import com.example.acer.zzia_mxbt.application.MyApplication;
import com.example.acer.zzia_mxbt.bean.SubjectBean;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ThemeActivity extends AppCompatActivity {
    //1,找数据
    List<SubjectBean> mList;
    //2，找每一行视图
    //3,确定适配器
    ThemeAdapter mThemeAdapter = null;
    private ListView mListView = null;
    private String mShowSubjectPath;//访问显示专题封面的路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_theme);
        initViews();
        initDatas();


    }

    private void initViews() {
        mListView = (ListView) findViewById(R.id.theme_listView);
    }

    private void initDatas() {
        //从数据库读取专题的封面
        MyApplication myApplication = (MyApplication) getApplication();
        mShowSubjectPath = myApplication.getShowSubjectUrl();

        mList = new ArrayList<>();
        RequestParams params = new RequestParams(mShowSubjectPath);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //专题表有数据
                Gson gson = new Gson();
                Type type = new TypeToken<List<SubjectBean>>() {
                }.getType();
                mList = gson.fromJson(result, type);
                mThemeAdapter = new ThemeAdapter(ThemeActivity.this, mList);
                mListView.setAdapter(mThemeAdapter);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("qiyu", "onError: "+ex.getMessage() );
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}