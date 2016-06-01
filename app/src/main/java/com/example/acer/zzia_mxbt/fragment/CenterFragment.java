package com.example.acer.zzia_mxbt.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.activity.Article_ReadActivity;
import com.example.acer.zzia_mxbt.activity.CenterActivity;
<<<<<<< HEAD
import com.example.acer.zzia_mxbt.activity.MainActivity;
import com.example.acer.zzia_mxbt.adapters.IndexListAdapter;
import com.example.acer.zzia_mxbt.adapters.MyAdapter;
import com.example.acer.zzia_mxbt.adapters.MyRecyclerViewAdapter;
import com.example.acer.zzia_mxbt.application.MyApplication;
import com.example.acer.zzia_mxbt.bean.ArticleBean;
=======
import com.example.acer.zzia_mxbt.adapters.IndexListAdapter;
import com.example.acer.zzia_mxbt.adapters.MyRecyclerViewAdapter;
>>>>>>> a4904fc3aebcfe5d3f3106fbd0f9c8cc1554a62d
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
public class CenterFragment extends Fragment {
<<<<<<< HEAD
    //存放文章信息的list
    List<ArticleBean> list ;

    //Oncreate中用以返回的view
    View view;

    //定义recyclerView
    RecyclerView recyclerView ;

    //recyclerView 适配器
    MyRecyclerViewAdapter  myRecyclerViewAdapter;
=======
    //存放首页文章信息的list

    View view;
>>>>>>> a4904fc3aebcfe5d3f3106fbd0f9c8cc1554a62d
    private static CenterFragment instance = null;

    public static CenterFragment newInstance() {
        if (instance == null) {
            instance = new CenterFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.centerlayout, container, false);
<<<<<<< HEAD
//        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.center_recyclerView);
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        List<Integer> datas = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            datas.add(i);
//        }
//        mRecyclerView.setAdapter(new MyRecyclerViewAdapter(getActivity(), datas));
        //初始化各个控件和视图
        initView();
        //设置各个控件和视图
        setView();
        //向服务器请求数据
        askForData();
        return view;
    }

    private void askForData() {
        RequestParams params = new RequestParams(MyApplication.getCenter_url());

//       RequestParams params= new RequestParams("http://139.129.58.244:8080/ZZIA_MXBT/index_servlet");
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<IndexBean>>() {
                }.getType();
                list = gson.fromJson(result, type);
                myRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(),list);
                recyclerView.setAdapter(myRecyclerViewAdapter);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("a", "错误原因：" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {


            }
        });


}

    private void setView() {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.center_recyclerView);

    }


//AsyncTask 内部类
    class loadDataAsyncTask extends AsyncTask<List<IndexBean>, Integer, String> {
        private static final int HIDDEN_CODE = 1;
        private static final int APPEAR_CODE = 2;
        private MainActivity activity;

        public loadDataAsyncTask(MainActivity activity) {
            this.activity = activity;
        }

        @Override
        protected String doInBackground(List<IndexBean>... params) {
            //用一个线程来模拟刷新
            List<IndexBean> list;
            list = params[0];
            publishProgress(1);
            addData(list, 0, list.size());
            publishProgress(0);
            return "success";
        }

        //对返回值进行操作
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if ("success".equals(s)) {
                //通知数据集改变，刷新页面
                myRecyclerViewAdapter.notifyDataSetChanged();
                //刷新完成
//                recyclerView.();
            }
        }


        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            Log.e("load", "onProgressUpdate: " + values[0]);
            if (values[0] != 0) {
                activity.handler.sendEmptyMessage(APPEAR_CODE);
            } else {
                activity.handler.sendEmptyMessage(HIDDEN_CODE);
            }

        }
    }


//数据添加方法
    public void addData(List<IndexBean> beanList, int begin, int end) {
        for (int i = begin; i <= end; i++) {
            String contentHttp = beanList.get(i).getContent();
            StringBuilder content = null;

            URL url = null;
            try {
                url = new URL(contentHttp);
                InputStream is = url.openStream();
                BufferedReader buff = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                content = new StringBuilder();
                String valueString;

                while ((valueString = buff.readLine()) != null) {
                    content.append(valueString);

//                        Log.e("lalala", "getView: "+mlist.get(p).getNickName()+stringBuilder);
                }
                if (content.toString().length() >= 60) {
                    beanList.get(i).setContent(content.toString().substring(0, 60) + "...");
                } else {
                    beanList.get(i).setContent(content.toString());
                }
                buff.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

=======
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.center_recyclerView);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        List<Integer> datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas.add(i);
        }
        mRecyclerView.setAdapter(new MyRecyclerViewAdapter(getActivity(), datas));
        return view;
>>>>>>> a4904fc3aebcfe5d3f3106fbd0f9c8cc1554a62d
    }
}

