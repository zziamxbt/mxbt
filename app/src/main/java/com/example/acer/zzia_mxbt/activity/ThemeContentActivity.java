package com.example.acer.zzia_mxbt.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.adapters.ThemeContentAdapter;
import com.example.acer.zzia_mxbt.application.MyApplication;
import com.example.acer.zzia_mxbt.bean.SubjectArticleBean;
import com.facebook.drawee.backends.pipeline.Fresco;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
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
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ThemeContentActivity extends AppCompatActivity {
    //1,找数据
    List<SubjectArticleBean> mList;
    //2,找每一行视图
    //3,确定适配器
    ThemeContentAdapter mThemeContentAdapter = null;
    private PullToRefreshListView mListView;//专题具体页面的listview
    private String mShowSubjectArticlePath;//方位专题对应文章的路径
    private ImageView mReturnImg;//返回箭头

    /**
     * listview头部布局控件
     */
    private TextView mHeadTimeTextView;//头部时间
    private TextView mHeadContentTextView;//头部简介
    private TextView mHeadTxtTextView;//头部txt文件
    private SimpleDraweeView mHeadImageView;//头部专题标题image
    private ImageView mExpandImg;//头部展开图标
    private int maxLine = 3;//默认最大展示行数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);//处理图片
        setContentView(R.layout.activity_theme_content);
        //1，初始化布局
        initViews();

      /*  View head = View.inflate(ThemeContentActivity.this, R.layout.theme_content_listviewhead, null);
        mListView.getRefreshableView().addHeaderView(head, null, false);*/
        //2,初始化数据
        initDatas();

        //3,在listview上添加头部

//        addListViewHead();

        //4，监听事件(返回键，分享按钮)
        initListeners();

        //5,上拉加载，下拉刷新
        mListView.setMode(PullToRefreshBase.Mode.BOTH);//表示既可以加载又可以刷新
        initRefreshListView();//初始化刷新事件
        listViewListeners();//listview监听事件

       /* View head = View.inflate(ThemeContentActivity.this, R.layout.theme_content_listviewhead, null);
        mListView.getRefreshableView().addHeaderView(head, null, false);*/
    }

    /**
     * 1，初始化布局
     */
    private void initViews() {
        mListView = (PullToRefreshListView) findViewById(R.id.theme_content_listview);
        mReturnImg = (ImageView) findViewById(R.id.theme_content_returnback);
    }

    /**
     * 2,初始化数据：将专题页面传递的id交于后台处理获取所有的文章集合，再转交适配器处理
     */

    /**********************************************以下是获取后台数据集合并处理后交由适配器处理BEGIN**************************************************/
    private void initDatas() {
        MyApplication myApplication = (MyApplication) getApplication();
        mShowSubjectArticlePath = myApplication.getShowSubjectArticleUrl();//访问后台的地址

        //获取Theme页面传递的专题id
        Intent intent = getIntent();
        int sid = intent.getIntExtra("sid", 0);
        Log.e("qiyu接收sid", sid + "");

        mList = new ArrayList<>();//保存后台的bean集合

        /*View head = View.inflate(ThemeContentActivity.this, R.layout.theme_content_listviewhead, null);
        mListView.getRefreshableView().addHeaderView(head, null, false);

        mThemeContentAdapter = new ThemeContentAdapter(ThemeContentActivity.this, mList);
        mListView.setAdapter(mThemeContentAdapter);
*/



        RequestParams params = new RequestParams(mShowSubjectArticlePath);
        params.addParameter("sid", sid);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //访问后台成功
                Gson gson = new Gson();
                Type type = new TypeToken<List<SubjectArticleBean>>() {
                }.getType();
                mList = gson.fromJson(result, type);
                //将后台取出的文章章节地址解析读取后再传递到适配器。
                loadListTxtAsyncTask myTask = new loadListTxtAsyncTask();
                myTask.execute(mList);

              /*  View head = View.inflate(ThemeContentActivity.this, R.layout.theme_content_listviewhead, null);
                mListView.getRefreshableView().addHeaderView(head, null, false);*/

                AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
                View header = getLayoutInflater().inflate(R.layout.theme_content_listviewhead, mListView, false);
                header.setLayoutParams(layoutParams);
                ListView lv = mListView.getRefreshableView();
                lv.addHeaderView(header);




                mThemeContentAdapter = new ThemeContentAdapter(ThemeContentActivity.this, mList);
                mListView.setAdapter(mThemeContentAdapter);

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

    //耗时操作，异步加载取出文章集合的第一章，

    /**
     * 参数一：表示需要处理的数据类型
     * 参数二：。。。
     * 参数三：doInBackground（）方法的返回类型
     */
    static class loadListTxtAsyncTask extends AsyncTask<List<SubjectArticleBean>, Void, String> {
        List<SubjectArticleBean> mList = null;
        @Override
        protected String doInBackground(List<SubjectArticleBean>... params) {
            mList = params[0];
            for (int i = 0; i < mList.size(); i++) {
                String txtHttp = mList.get(i).getContent();
                StringBuilder content = null;
                URL url = null;
                try {
                    url = new URL(txtHttp);
                    InputStream is = url.openStream();
                    BufferedReader buff = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    content = new StringBuilder();
                    String valueString;
                    while ((valueString = buff.readLine()) != null) {
                        content.append(valueString);
                    }
                    //只将数据部分显示
                    if (content.toString().length() >= 30) {
                        mList.get(i).setContent(content.toString().substring(0, 30) + "...");
                    } else {
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
            return "success";//返回的结果作为onPostExecute(String str)的参数处理
        }

        @Override
        protected void onPostExecute(String str) {
            if (str.equals("success")) {
               /* //通知数据集改变，刷新页面
                activity.mThemeContentAdapter.notifyDataSetChanged();
                //刷新完成
                activity.mListView.onRefreshComplete();*/
            }
        }
    }

    /**********************************************以上是获取后台数据集合并处理后交由适配器处理END***********************************************/


    /**
     * 3,在listview上添加头部，并将由专题页面传递的参数显示在页面
     */
    /**********************************************以下是listview添加头部BEGIN***********************************************/
    private void addListViewHead() {
        mHeadTimeTextView = (TextView) findViewById(R.id.theme_content_listviewhead_timetext);
        mHeadImageView = (SimpleDraweeView) findViewById(R.id.theme_content_listviewhead_image);
        mHeadContentTextView = (TextView) findViewById(R.id.theme_content_listviewhead_contenttext);
        mHeadTxtTextView = (TextView) findViewById(R.id.theme_content_listviewhead_txt);
        mExpandImg = (ImageView) findViewById(R.id.theme_content_listviewhead_expandimg);

        Intent intent = getIntent();
        Uri imageUri = Uri.parse(intent.getStringExtra("imageUri"));
        String content = intent.getStringExtra("content");
        String txt=intent.getStringExtra("textUri");

        Log.e("qiyu接收img", imageUri + "");
        Log.e("qiyu接收content", content + "");
        Log.e("qiyu接收txt",txt + "");

        //简介显示
        mHeadContentTextView.setText(content);

        //图片显示
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(this.getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                .setPlaceholderImage(this.getResources().getDrawable(R.drawable.jiazai))
                .setRoundingParams(new RoundingParams().setRoundAsCircle(true))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(imageUri)
                .build();
        mHeadImageView.setController(controller);
        mHeadImageView.setHierarchy(hierarchy);

        //txt显示
        loadTxtAsyncTask txtTask=new loadTxtAsyncTask(ThemeContentActivity.this);
        txtTask.execute(txt);

      /*  //文本展开
        mHeadTxtTextView.setHeight(mHeadTxtTextView.getLineHeight()*maxLine);//设置默认显示高度
        //依据高度来控制是否翻转icon
        mHeadTxtTextView.post(new Runnable() {
            @Override
            public void run() {
                mExpandImg.setVisibility(mHeadTxtTextView.getLineCount()>maxLine? View.VISIBLE:View.GONE);
            }
        });

      mExpandImg.setOnClickListener(new MyTurnListener());//翻转监听事件*//**//*
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings){
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyTurnListener implements View.OnClickListener {
        boolean isExpand;//是否翻转
        @Override
        public void onClick(View v) {
            isExpand=!isExpand;
            mHeadTxtTextView.clearAnimation();//清除动画
            final int tempHight;
            final int startHight=mHeadTxtTextView.getHeight();//起始高度
            int durationMillis=200;
            if(isExpand){
                //折叠，长变短
                tempHight=mHeadTxtTextView.getLineHeight()*mHeadTxtTextView.getLineCount()-startHight;//为正值
                //180翻转icon
                RotateAnimation animation=new RotateAnimation(0,180, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                animation.setDuration(durationMillis);
                animation.setFillAfter(true);
                mExpandImg.startAnimation(animation);
            }else{
                //展开，短变长
                tempHight=mHeadTxtTextView.getLineHeight()*maxLine-startHight;//为负值
                //180翻转icon
                RotateAnimation animation=new RotateAnimation(180,0, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                animation.setDuration(durationMillis);
                animation.setFillAfter(true);
                mExpandImg.startAnimation(animation);
            }
            Animation animation=new Animation() {
                protected void applyTransformation(float interpolatedTime, Transformation t){
                    mHeadTxtTextView.setHeight((int) (startHight+tempHight*interpolatedTime));
                }
            };
            animation.setDuration(durationMillis);
            mHeadTxtTextView.startAnimation(animation);

        }*/
    }
    //专题txt异步加载
    static class loadTxtAsyncTask extends AsyncTask<String, Void, String> {
        private ThemeContentActivity activity;
        public loadTxtAsyncTask(ThemeContentActivity activity) {
            this.activity = activity;
        }
        String txt = null;
        @Override
        protected String doInBackground(String... params) {
            txt = params[0];
            StringBuilder content = null;
            URL url = null;
            try {
                url = new URL(txt);
                InputStream is = url.openStream();
                BufferedReader buff = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                content = new StringBuilder();
                String valueString;
                while ((valueString = buff.readLine()) != null) {
                    content.append(valueString);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String str) {
            activity.mHeadTxtTextView.setText(str);
            //文本展开
            /*activity.mHeadTxtTextView.setHeight(activity.mHeadTxtTextView.getLineHeight()*activity.maxLine);//设置默认显示高度
            //依据高度来控制是否翻转icon
            activity.mHeadTxtTextView.post(new Runnable() {
                @Override
                public void run() {
                   activity. mExpandImg.setVisibility(activity.mHeadTxtTextView.getLineCount()>activity.maxLine? View.VISIBLE:View.GONE);
                }
            });

            activity.mExpandImg.setOnClickListener(activity.new MyTurnListener());//翻转监听事件*/
        }
    }

    /**********************************************以上是listview添加头部END***********************************************/

    /**
     * 4，其他控件监听事件
     */
    private void initListeners() {
        mReturnImg.setOnClickListener(new View.OnClickListener() {
            //返回键
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThemeContentActivity.this, ThemeActivity.class);
                startActivity(intent);

            }
        });
    }

    /**
     * 5,listview上拉加载下拉刷新
     */
    /**********************************************以下是listview上拉加载下拉刷新BEGIN*************************************************/
    //listview刷新初始化
    private void initRefreshListView() {
        ILoadingLayout startLables = mListView.getLoadingLayoutProxy(true, false);
        startLables.setPullLabel("下拉刷新");
        startLables.setRefreshingLabel("正在拉");
        startLables.setReleaseLabel("放开刷新");
        ILoadingLayout endLabels = mListView.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉加载");
        endLabels.setRefreshingLabel("正在载入...");
        endLabels.setReleaseLabel("放开加载...");
    }

    //listView的刷新监听事件
    private void listViewListeners() {
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //加载耗时，异步操作
                new loadListViewAsyncTask(ThemeContentActivity.this).execute();
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                new loadListViewAsyncTask(ThemeContentActivity.this).execute();
            }
        });
    }

    //内部类：实现数据加载的耗时操作
    static class loadListViewAsyncTask extends AsyncTask<Void, Void, String> {
        private ThemeContentActivity activity;
        public loadListViewAsyncTask(ThemeContentActivity activity) {
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
                activity.mThemeContentAdapter.notifyDataSetChanged();
                //刷新完成
                activity.mListView.onRefreshComplete();
            }
        }
    }
    /**********************************************以上是listview上拉加载下拉刷新END*************************************************/
}




