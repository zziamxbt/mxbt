package com.example.acer.zzia_mxbt.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.adapters.IndexListAdapter1;
import com.example.acer.zzia_mxbt.application.MyApplication;
import com.example.acer.zzia_mxbt.bean.IndexBean;
import com.example.acer.zzia_mxbt.bean.SubjectBean;
import com.facebook.drawee.backends.pipeline.Fresco;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ThemeContentActivity extends AppCompatActivity {
    //1,找数据
    List<IndexBean> mList;
    //2,找每一行视图
    //3,确定适配器
    IndexListAdapter1 mIndexListAdapter1=null;
    private PullToRefreshListView mListView;//专题具体页面的listview
    // viewHolder.maincontent = (TextView) convertView.findViewById(R.id.list_maincontent);
    private String mShowSubjectArticlePath;//方位专题对应文章的路径
    private TextView mListContent;//文章内容
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_theme_content);
        //初始化布局
        initViews();
        initDatas();

    }

    private void initViews() {
        mListView = (PullToRefreshListView) findViewById(R.id.theme_content_lisyview);
        mListContent= (TextView) findViewById(R.id.list_maincontent);
    }

    private void initDatas() {
        //从数据库获得相关文章及作者信息
        MyApplication myApplication= (MyApplication) getApplication();
        mShowSubjectArticlePath=myApplication.getShowSubjectArticleUrl();
        Intent intent = getIntent();
        int sid = intent.getIntExtra("sid", 0);

        mList=new ArrayList<>();
        RequestParams params=new RequestParams(mShowSubjectArticlePath);
        params.addParameter("sid",sid);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //访问后台成功
                Gson gson=new Gson();
                Type type = new TypeToken<List<IndexBean>>() {
                }.getType();
                mList = gson.fromJson(result,type);
                selectTxt(mList,0,mList.size());
                mIndexListAdapter1=new IndexListAdapter1(ThemeContentActivity.this,mList);
                mListView.setAdapter(mIndexListAdapter1);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /*private void selectTxt(List<IndexBean> mList, int begin, int end) {
        for(int i=begin;i<end;i++){
            String txtHttp=mList.get(i).getContent();
            StringBuilder content = null;
            URL url=null;
            try {
                url=new URL(txtHttp);
                InputStream is=url.openStream();
                BufferedReader buff=new BufferedReader(new InputStreamReader(is,"UTF-8"));
                content=new StringBuilder();
                String valueString;

                while((valueString=buff.readLine())!=null){
                    content.append(valueString);
                }

                if(content.toString().length()>=200){
                    mList.get(i).setContent(content.toString().substring(0,200)+"...");
                }else{
                    mList.get(i).setContent(content.toString());
                }
                buff.close();
                is.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }*/

    static class loadDataAsyncTask extends AsyncTask<Void,Void,String>{
        private ThemeContentActivity activity;
        public loadDataAsyncTask(ThemeContentActivity activity){
            this.activity=activity;
        }

        @Override
        protected String doInBackground(Void... params) {
            
            return null;
        }
    }




    private void selectTxt(List<IndexBean> mList, int begin, int end) {
        for(int i=begin;i<end;i++){
            String txtHttp=mList.get(i).getContent();
            StringBuilder content = null;
            URL url=null;
            try {
                url=new URL(txtHttp);
                InputStream is=url.openStream();
                BufferedReader buff=new BufferedReader(new InputStreamReader(is,"UTF-8"));
                content=new StringBuilder();
                String valueString;

                while((valueString=buff.readLine())!=null){
                    content.append(valueString);
                }

                if(content.toString().length()>=200){
                    mList.get(i).setContent(content.toString().substring(0,200)+"...");
                }else{
                    mList.get(i).setContent(content.toString());
                }
                buff.close();
                is.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
