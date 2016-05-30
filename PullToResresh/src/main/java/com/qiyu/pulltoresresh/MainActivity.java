package com.qiyu.pulltoresresh;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    PullToRefreshListView mListView;
    List<Music> mList = new ArrayList<>();
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initDatas();
        myAdapter = new MyAdapter(MainActivity.this, mList);
        mListView.setAdapter(myAdapter);
        //设置为Both，表明：上拉和下拉同时都适用
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        //初始化刷新操作
        initRefreshListView();
        //listview的监听事件
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new loadDataAsyncTask(MainActivity.this).execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new loadDataAsyncTask(MainActivity.this).execute();
            }
        });
    }

    private void initViews() {
        mListView = (PullToRefreshListView) findViewById(R.id.listview);
    }

    private void initDatas() {
        for (int i = 0; i < 10; i++) {
            mList.add(new Music("Music" + i, "Singer" + i));
        }
    }

    //内部类：实现数据加载的耗时操作
    class loadDataAsyncTask extends AsyncTask<Void, Void, String> {
        private MainActivity activity;
        public loadDataAsyncTask(MainActivity activity) {
            this.activity = activity;
        }
        @Override
        protected String doInBackground(Void... params) {
            //用一个线程来模拟刷新
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //加载数据
            activity.initDatas();
            return "success";
        }
        //对返回值进行操作
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if ("success".equals(s)) {
                //通知数据集改变，刷新页面
                myAdapter.notifyDataSetChanged();
                //刷新完成
                mListView.onRefreshComplete();
            }
        }
    }


    public void initRefreshListView() {
        ILoadingLayout startLables = mListView.getLoadingLayoutProxy(true, false);
        startLables.setPullLabel("下拉刷新");
        startLables.setRefreshingLabel("正在拉");
        startLables.setReleaseLabel("放开刷新");
        ILoadingLayout endLabels = mListView.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉加载");
        endLabels.setRefreshingLabel("正在载入...");
        endLabels.setReleaseLabel("放开加载...");
    }

}
