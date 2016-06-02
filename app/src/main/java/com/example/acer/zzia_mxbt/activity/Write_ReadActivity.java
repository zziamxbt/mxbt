package com.example.acer.zzia_mxbt.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.adapters.Write_ReadAdapter;
import com.example.acer.zzia_mxbt.application.MyApplication;
import com.example.acer.zzia_mxbt.bean.Write_ReadBean;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Write_ReadActivity extends AppCompatActivity {
    //Xutil的 RequestParams params
    private RequestParams params;
    // 投票和关注flag以及图片
    private boolean PeopleVote_flag = true;
    private boolean PeopleFocus_flag = true;
    private ImageView mVote_image;
    private ImageView mFocus_image;
    //头部和底部滑动控件
    private boolean Top_Buttom_flag = true;
    private ObjectAnimator mObjectAnimator;
    private RelativeLayout mWrite_ChapterTop;
    private LinearLayout mWrite_ChapterButtom;

    private TextView mArticleName;
    private ListView mWrite_readListView;
    private List<Write_ReadBean> mWrite_readBeanList;
    private Write_ReadAdapter mWriteReadAdapter;
    //  private List<Write_ReadBean> mListData;
    //访问网络数据的路径
    private String mPath;
    private String mVotePath;
    //是否投票flag和章节内容
    private int AWid;
    private String Uid;
    private String Cid;
    private String  mvote_flag;
    //AWid的flag
   private boolean AWidFlag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        Fresco.initialize(this);
        setContentView(R.layout.activity_write__read);
        //初始化控件
        init();
        //获取跳转的值
        getIntentData();
        //获取路径
        getPath();
        //获取续写内容
        getTest(mvote_flag);
        //listview头部和底部滑动监听
        setAddListerner();

    }

    private void getIntentData() {
        Intent intent = getIntent();
        AWid= intent.getIntExtra("AWid", 0);
        Uid=intent.getStringExtra("Uid");
        Log.e("Uid", "Uid: " + Uid);
        Cid=intent.getStringExtra("Cid");
        Log.e("Cid", "Cid: " + Cid);
        mvote_flag=intent.getStringExtra("vote_flag");
        Log.e("mvote_flag", "mvote_flag: " + mvote_flag);
        if(mvote_flag!=null){
            if(mvote_flag.equals("false")){
                mVote_image.setImageResource(R.drawable.toupiao_success);
            }else{
                mVote_image.setImageResource(R.drawable.toupiao);
            }
        }

    }


    private void setAddListerner() {
        mWrite_readListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Top_Buttom_flag) {
                    mObjectAnimator.ofFloat(mWrite_ChapterButtom, "translationY", 0F, mWrite_ChapterButtom.getHeight() + mWrite_ChapterTop.getHeight())
                            .setDuration(200).start();
                    Top_Buttom_flag = false;
                } else {
                    mObjectAnimator.ofFloat(mWrite_ChapterButtom, "translationY", mWrite_ChapterButtom.getHeight(), mWrite_ChapterButtom.getHeight() - mWrite_ChapterButtom.getHeight())
                            .setDuration(200).start();
                    Top_Buttom_flag = true;
                }
            }
        });
    }


    private void init() {
        mArticleName = (TextView) findViewById(R.id.ArticleName);
        mWrite_readListView = (ListView) findViewById(R.id.Write_read);
        //底部和顶部框架初始化
        mWrite_ChapterButtom = (LinearLayout) findViewById(R.id.Write_ChapterButtom);
        mWrite_ChapterTop = (RelativeLayout) findViewById(R.id.Write_ChapterTop);
        //投票和关注图片初始化
        mVote_image = (ImageView) findViewById(R.id.Vote_image);
        mFocus_image = (ImageView) findViewById(R.id.Focus_image);
        mWrite_readBeanList = new ArrayList<>();
    }

    //投票监听,需要补充
  /*  public void PeopleVote(View view) {
        if(mvote_flag.equals("false")){
            mVote_image.setImageResource(R.drawable.toupiao);
            mvote_flag="true";
        }else{
            mVote_image.setImageResource(R.drawable.toupiao_success);
            mvote_flag="false";
        }
        //使数据库刷新投票
        getTest(mvote_flag);
        Toast.makeText(Write_ReadActivity.this, "你点击了投票", Toast.LENGTH_SHORT).show();
    }*/

    //评论监听
    public void PeopleCommand(View view) {
        Toast.makeText(Write_ReadActivity.this, "你点击了评论", Toast.LENGTH_SHORT).show();
    }

    //分享监听
    public void PeopleShare(View view) {
        Toast.makeText(Write_ReadActivity.this, "你点击了分享", Toast.LENGTH_SHORT).show();
    }

    //关注监听
    public void PeopleFocus(View view) {
        if (PeopleFocus_flag) {
            mFocus_image.setImageResource(R.drawable.guanzhu_success);
            PeopleFocus_flag=false;
        } else {
            mFocus_image.setImageResource(R.drawable.guanzhu);
            PeopleFocus_flag=true;
        }
        Toast.makeText(Write_ReadActivity.this, "你点击了关注", Toast.LENGTH_SHORT).show();
    }
    //返回监听

    public void Back_Article(View view) {
        finish();
    }

    public void getPath() {
        MyApplication myApplication = (MyApplication) getApplication();
        mPath = myApplication.getUrl_WriteArticle();
        mVotePath=myApplication.getVote_url();
    }

    //gest请求的到网络数据
    public void getTest(String mvote_flag) {
        //get请求
        //第一步：设置访问路径
        Log.e("abc", "getTest: " + AWid);
        if(AWidFlag){
            params= new RequestParams(mPath);
            params.addQueryStringParameter("AWid", AWid + "");
        }else{
            params = new RequestParams(mVotePath);
            params.addQueryStringParameter("flag", mvote_flag);
            params.addQueryStringParameter("Uid",Uid+"");
            params.addQueryStringParameter("Cid",""+Cid);
        }

        //第二步：开始请求，设置请求方式，同时实现回调函数
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //访问成功，参数其实就是PrintWriter写回的值
                //把JSON格式的字符串改为Student对象
                if(AWidFlag){
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Write_ReadBean>>() {
                    }.getType();
                    mWrite_readBeanList = gson.fromJson(result, type);

                    Log.e("mWrite_readBeanList", "data：" + gson.fromJson(result, type));
                    mArticleName.setText(mWrite_readBeanList.get(0).getWrite_articleName());

                    mWriteReadAdapter = new Write_ReadAdapter(Write_ReadActivity.this, mWrite_readBeanList);
                    mWrite_readListView.setAdapter(mWriteReadAdapter);
                    AWidFlag=false;
                }



            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //访问失败
                Log.e("data", "错误原因：" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                //访问取消
            }

            @Override
            public void onFinished() {
                //访问完成
            }
        });
    }


}
