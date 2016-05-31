package com.example.acer.zzia_mxbt.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.activity.Article_ReadActivity;
import com.example.acer.zzia_mxbt.activity.CenterActivity;
import com.example.acer.zzia_mxbt.adapters.IndexListAdapter;
import com.example.acer.zzia_mxbt.bean.IndexBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

;

/**
 * Created by acer on 2016/5/7.
 */
public class CenterFragment2 extends Fragment {
    //存放首页文章信息的list
    View view;
    private static CenterFragment2 instance=null;
    public static CenterFragment2 newInstance() {
        if(instance==null){
            instance= new CenterFragment2();
        }
        return instance;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.centerlayout2, container, false);
        return view;
    }


}
