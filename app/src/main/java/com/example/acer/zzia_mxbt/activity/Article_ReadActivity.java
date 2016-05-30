package com.example.acer.zzia_mxbt.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.adapters.MyAdapter_article;
import com.example.acer.zzia_mxbt.adapters.MyAdapter_chapter;
import com.example.acer.zzia_mxbt.application.MyApplication;
import com.example.acer.zzia_mxbt.bean.ArticleBean;
import com.example.acer.zzia_mxbt.bean.JavaBean_article;
import com.example.acer.zzia_mxbt.bean.JavaBean_chapter;
import com.example.acer.zzia_mxbt.utils.SetPicture;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Article_ReadActivity extends AppCompatActivity {
    //接受上下文
    private Bitmap bitmap;
    //从网上获取的数据放入list集合中
    List<ArticleBean> listData;
    public static final String TAG = "data";
    //访问网络数据的路径
    public String mPath;
    //被标记的章节，距离手机顶部的距离为首级的五分之一
    public static final int INT = 5;
    private LayoutInflater mInflater;
    private ObjectAnimator mObjectAnimator;
    private JavaBean_article mJavaBean_article;
    private List<JavaBean_article> mArticleList;
    private MyAdapter_article myAdapter_article;

    private ListView mArticleListView;
    private RelativeLayout mArticlt_Top;
    private LinearLayout mArticlt_Buttom;
    //屏幕显示文章的listview的位置
    private int mArticleListPosition;
    private boolean Top_Buttom_flag = true;

    private ListView mChapterListView;
    private JavaBean_chapter mJavaBean_chapter;
    private List<JavaBean_chapter> mChapterList;
    private MyAdapter_chapter myAdapter_chapter;
    private boolean Chapter_flag = true;

    //头部的View
    private LayoutInflater mTop_ViewInflater;
    private View mTop_View;
    private SimpleDraweeView mArticleHeadBackground;
    private TextView mHeadArticleTitle;
    private ImageView mArticleTypeImage;
    private TextView mArticleType;
    private SimpleDraweeView mHeadAuthouPortraits;
    private TextView mHeadAuthorName;
    //底部的View
    private LayoutInflater mButtom_ViewInflater;
    private View mButtom_View;
    private SimpleDraweeView mFootRoundPortraits;
    private TextView mFootAuthorName;
    private TextView mFootArticleTitle;
    private TextView mFocusNumber;
    private TextView mReaderNumber;
    private RelativeLayout mbackground;
    private ImageView mauthor_sex;


    //圆形头像类
    // private RoundPicture mRoundPicture;

    //设置布局
    private TextView mSettings_Text;
    private LinearLayout mSettings_Layout;
    private boolean mSettings_flag = true;

    //文章章节和提示下拉
    private Button mChapter_Button_Text;
    private ImageView mshow_down;

    //设置文章字体大小以及大小改变的内容
    private SeekBar mSeekBar;
    //给适配器传入字体设置的大小
    private int mListView_Item_position;
    //下载背景
    private Handler handler_background;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        Fresco.initialize(this);
        setContentView(R.layout.activity_article__read);
        //初始化方法
        init();
        //得到网络数据的路径
        getPath();
        //获取网络数据
        getTest();

        //添加数据方法
        //  initdata(listData);
        //添加listview的head和foot方法
        setListview();
        //头部和底部布局控件初始化
        initListview();
        //设置适配器方法
        setArticle_Adapter();
        //封面适配器
        setChapter_Adapter();

        //设置footerview中的圆形图片
        //   mRoundPicture.initView();
        //设置监听方法
        addListerner();
    }

    private void initListview() {
        //头部组件初始化
        mArticleHeadBackground = (SimpleDraweeView) mTop_View.findViewById(R.id.ArticleHeadBackground);
        mHeadArticleTitle = (TextView) mTop_View.findViewById(R.id.HeadArticleTitle);
        mArticleTypeImage = (ImageView) mTop_View.findViewById(R.id.ArticleTypeImage);
        mArticleType = (TextView) mTop_View.findViewById(R.id.ArticleType);
        mHeadAuthouPortraits = (SimpleDraweeView) mTop_View.findViewById(R.id.HeadAuthouPortraits);
        mHeadAuthorName = (TextView) mTop_View.findViewById(R.id.HeadAuthorName);

        //底部组件初始化
        mFootRoundPortraits = (SimpleDraweeView) mButtom_View.findViewById(R.id.FootRoundPortraits);
        mFootAuthorName = (TextView) mButtom_View.findViewById(R.id.FootAuthorName);
        mFootArticleTitle = (TextView) mButtom_View.findViewById(R.id.FootArticleTitle);
        mFocusNumber = (TextView) mButtom_View.findViewById(R.id.FocusNumber);
        mReaderNumber = (TextView) mButtom_View.findViewById(R.id.ReaderNumber);
        mbackground = (RelativeLayout) mButtom_View.findViewById(R.id.background);
        mauthor_sex = (ImageView) mButtom_View.findViewById(R.id.AuthorSex);
    }

    private void setArticle_Adapter() {
        //文章适配器
        myAdapter_article = new MyAdapter_article(Article_ReadActivity.this,mArticleList,mListView_Item_position);
        mArticleListView.setAdapter(myAdapter_article);
//        myAdapter_article.notifyDataSetChanged();
        //  Log.e(TAG, "数据为" + mHeadAuthorName.getText());
    }

    //封面适配器方法
    private void setChapter_Adapter() {
        myAdapter_chapter = new MyAdapter_chapter(Article_ReadActivity.this, mChapterList, mArticleListPosition);
        mChapterListView.setAdapter(myAdapter_chapter);
    }

    private void addListerner() {
        //滑动监听文章章节数
        mArticleListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mArticleListPosition = firstVisibleItem;
                if (mChapterList.size() == 0) {
                    mChapter_Button_Text.setText("封面");
                } else {
                    mChapter_Button_Text.setText(mChapterList.get(mArticleListPosition).getChapterNumber());
                }

                setChapter_Adapter();
                mChapterListView.setSelectionFromTop(mArticleListPosition, getHeight(Article_ReadActivity.this) / INT);
            }
        });
        //监听文章内容的章节数
        mChapterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mArticleListPosition = position;
                setChapter_Adapter();
                setArticle_Adapter();
                mArticleListView.setSelectionFromTop(mArticleListPosition, 0);
                mChapterListView.setSelectionFromTop(mArticleListPosition, getHeight(Article_ReadActivity.this) / INT);
                mChapterListView.setVisibility(View.GONE);
                mArticlt_Buttom.setVisibility(View.VISIBLE);
                mObjectAnimator.ofFloat(mArticlt_Buttom, "translationY", 0F, mArticlt_Buttom.getHeight() + mArticlt_Top.getHeight())
                        .setDuration(200).start();
                mObjectAnimator.ofFloat(mArticlt_Top, "translationY", 0F, -mArticlt_Top.getHeight())
                        .setDuration(200).start();
                mshow_down.setImageResource(R.drawable.show_down);
                Top_Buttom_flag = false;
                Chapter_flag = true;
            }
        });
        //头部和底部滑动监听
        mArticleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Top_Buttom_flag) {
                    mObjectAnimator.ofFloat(mArticlt_Buttom, "translationY", 0F, mArticlt_Buttom.getHeight() + mArticlt_Top.getHeight())
                            .setDuration(200).start();
                    mObjectAnimator.ofFloat(mArticlt_Top, "translationY", 0F, -mArticlt_Top.getHeight())
                            .setDuration(200).start();
                    Top_Buttom_flag = false;
                } else {
                    mObjectAnimator.ofFloat(mArticlt_Top, "translationY", -mArticlt_Top.getHeight(), 0F)
                            .setDuration(200).start();
                    mObjectAnimator.ofFloat(mArticlt_Buttom, "translationY", mArticlt_Buttom.getHeight(), mArticlt_Buttom.getHeight() - mArticlt_Top.getHeight())
                            .setDuration(200).start();
                    Top_Buttom_flag = true;
                }

            }
        });

        //"设置"监听
        mSettings_Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArticlt_Buttom.setVisibility(View.VISIBLE);
                mObjectAnimator.ofFloat(mSettings_Text, "translationY", 0F, -mSettings_Text.getHeight())
                        .setDuration(1000).start();
                mObjectAnimator.ofFloat(mSettings_Layout, "translationY", 0F, 2 * mSettings_Layout.getHeight())
                        .setDuration(1000).start();
                mObjectAnimator.ofFloat(mArticlt_Buttom, "translationY", 0F, mArticlt_Buttom.getHeight() + mArticlt_Top.getHeight())
                        .setDuration(200).start();
                mObjectAnimator.ofFloat(mArticlt_Top, "translationY", 0F, -mArticlt_Top.getHeight())
                        .setDuration(200).start();
                Top_Buttom_flag = false;
                mSettings_flag = true;
            }
        });
        //字体大小seekbar设置
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mListView_Item_position = seekBar.getProgress();
                setArticle_Adapter();
                mArticleListView.setSelectionFromTop(mArticleListPosition, 0);
                //     Toast.makeText(Article_ReadActivity.this,""+seekBar.getProgress(),Toast.LENGTH_SHORT).show();
            }
        });
        //head的作者头像的监听
        mHeadAuthouPortraits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Article_ReadActivity.this, "你点击的本文创建者的头像", Toast.LENGTH_SHORT).show();
            }
        });
        //foot的作者头像的监听
        mFootRoundPortraits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Article_ReadActivity.this, "你点击的本文创建者的头像", Toast.LENGTH_SHORT).show();
            }
        });
        //foot的关注监听
        mFocusNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Article_ReadActivity.this, "你点击的本文的关注量", Toast.LENGTH_SHORT).show();
            }
        });
        //foot的读者监听
        mReaderNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Article_ReadActivity.this, "你点击的本文的读者量", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setListview() {
        //头部布局
        mTop_ViewInflater = LayoutInflater.from(Article_ReadActivity.this);
        mTop_View = mTop_ViewInflater.inflate(R.layout.listview_article_head, null);
        mTop_View.setMinimumWidth(getWidth(Article_ReadActivity.this));
        mTop_View.setMinimumHeight(getHeight(Article_ReadActivity.this));
        mArticleListView.addHeaderView(mTop_View);

        //底部布局
        mButtom_ViewInflater = LayoutInflater.from(Article_ReadActivity.this);
        mButtom_View = mButtom_ViewInflater.inflate(R.layout.listview_article_footer, null);
        mButtom_View.findViewById(R.id.footerLiner).setMinimumHeight(getHeight(Article_ReadActivity.this));
        mButtom_View.findViewById(R.id.footerLiner).setMinimumWidth(getWidth(Article_ReadActivity.this));
        mButtom_View.setMinimumWidth(getWidth(Article_ReadActivity.this));
        mButtom_View.setMinimumHeight(getHeight(Article_ReadActivity.this));
        mArticleListView.addFooterView(mButtom_View);
        mArticleListView.setSelector(R.drawable.list_bg);
    }

    private void init() {

        //设置字体大小滑动条初始化
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        //文章按钮button初始化和下拉图片提示
        mChapter_Button_Text = (Button) findViewById(R.id.ChapterButton);
        mshow_down = (ImageView) findViewById(R.id.show_down);
        //总布局初始化
        mArticleListView = (ListView) findViewById(R.id.ArticleList);
        mArticlt_Buttom = (LinearLayout) findViewById(R.id.ArticleButtom);
        mArticlt_Top = (RelativeLayout) findViewById(R.id.ArticleTop);
        //封面初始化
        mChapterListView = (ListView) findViewById(R.id.ChapterList);
        //初始化设置布局
        mSettings_Text = (TextView) findViewById(R.id.SettingsText);
        mSettings_Layout = (LinearLayout) findViewById(R.id.SettingsLayout);

        //获取布局添加listview的头部和底部
        mInflater = LayoutInflater.from(Article_ReadActivity.this);
        //圆形图片头像初始化
        //mRoundPicture=new RoundPicture(Article_ReadActivity.this);
        mArticleList = new ArrayList<>();
        mChapterList = new ArrayList<>();
    }

    private void initdata(List<ArticleBean> listData) {

        //章节内容赋值

        for (int i = 1; i <= listData.get(0).getChapter_number().size(); i++) {
            mJavaBean_article = new JavaBean_article();
            mJavaBean_article.setChapterTitle(listData.get(0).getChapter_number().get(i - 1));
            mJavaBean_article.setArticleContent(listData.get(0).getChapter_content().get(i - 1));
            mJavaBean_article.setChapterAuthorName(listData.get(0).getAuthor_chapter_name().get(i - 1));
            mJavaBean_article.setArticleTime(listData.get(0).getCreate_chapter_time().get(i - 1));
            mJavaBean_article.setmAuthor_portraits(listData.get(0).getAuthor_chapter_head().get(i - 1));
            mArticleList.add(mJavaBean_article);
            //封面简介赋值
        }
        for (int j = 0; j <= listData.get(0).getChapter_number().size() + 1; j++) {
            mJavaBean_chapter = new JavaBean_chapter();
            if (j == 0) {
                mJavaBean_chapter.setArticleEasyIntroduction(listData.get(0).getArticle_title());
                mJavaBean_chapter.setChapterNumber("封面");
            } else if (j == listData.get(0).getChapter_number().size() + 1) {
                mJavaBean_chapter.setArticleEasyIntroduction(listData.get(0).getAuthor_name());
                mJavaBean_chapter.setChapterNumber("封底");
            } else {
                mJavaBean_chapter.setArticleEasyIntroduction(listData.get(0).getChapter_content().get(j-1));
                mJavaBean_chapter.setChapterNumber(j + "/" + listData.get(0).getChapter_number().size());
            }
            mChapterList.add(mJavaBean_chapter);
        }
        //listview头部获取网络值
        mHeadAuthorName.setText(listData.get(0).getAuthor_name().toString());
        mArticleHeadBackground.setImageURI(Uri.parse(listData.get(0).getArticle_cover()));
        mHeadArticleTitle.setText(listData.get(0).getArticle_title());
        if (listData.get(0).getArticle_type().equals("生活")){
            mArticleTypeImage.setImageResource(R.drawable.ring_red);
        }else if(listData.get(0).getArticle_type().equals("真事")){
            mArticleTypeImage.setImageResource(R.drawable.ring_green);
        }else if(listData.get(0).getArticle_type().equals("创作")){
            mArticleTypeImage.setImageResource(R.drawable.ring_yellow);
        }else if(listData.get(0).getArticle_type().equals("灵异")){
            mArticleTypeImage.setImageResource(R.drawable.ring_blue);
        }

        mArticleType.setText(listData.get(0).getArticle_type());
        mHeadAuthouPortraits.setImageURI(Uri.parse(listData.get(0).getAuthor_headportrait()));


        //listview低部获取网络值
        mFootRoundPortraits.setController(SetPicture.controller(Uri.parse(listData.get(0).getAuthor_headportrait())));
        mFootRoundPortraits.setHierarchy(SetPicture.hierarchy(listData.get(0).getAuthor_headportrait(), Article_ReadActivity.this));

        mFootAuthorName.setText(listData.get(0).getAuthor_name());
        mFootArticleTitle.setText(listData.get(0).getArticle_title());
        mFocusNumber.setText("关注 " + listData.get(0).getFocus_number());
        mReaderNumber.setText("读者 " + listData.get(0).getReader_number());
        if (listData.get(0).getAuthor_sex().equals("男")) {
            mauthor_sex.setImageResource(R.drawable.sex_boy);
        } else if (listData.get(0).getAuthor_sex().equals("女")) {
            mauthor_sex.setImageResource(R.drawable.sex_girl);
        } else {
            mauthor_sex.setImageResource(0);
        }
        ReadBackground(listData.get(0).getArticle_background());
        handler_background = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    Drawable drawable = new BitmapDrawable(bitmap);
                    //   drawable.setAlpha(150);
                    mButtom_View.findViewById(R.id.footerLiner).setAlpha(0.8f);
                    mbackground.setBackgroundDrawable(drawable);
                }
            }
        };


    }

    //封面序列点击事件
    public void ButtonChapterListerner(View view) {
        if (Chapter_flag) {
            mChapterListView.setVisibility(View.VISIBLE);
            mArticlt_Buttom.setVisibility(View.GONE);
            Chapter_flag = false;
            mshow_down.setImageResource(R.drawable.show_up);
        } else {
            mChapterListView.setVisibility(View.GONE);
            mArticlt_Buttom.setVisibility(View.VISIBLE);
            Chapter_flag = true;
            mshow_down.setImageResource(R.drawable.show_down);
        }

    }

    //封面提示图片序列点击事件
    public void ImageViewChapterListerner(View view) {
        if (Chapter_flag) {
            mChapterListView.setVisibility(View.VISIBLE);
            mArticlt_Buttom.setVisibility(View.GONE);
            Chapter_flag = false;
            mshow_down.setImageResource(R.drawable.show_up);
        } else {
            mChapterListView.setVisibility(View.GONE);
            mArticlt_Buttom.setVisibility(View.VISIBLE);
            Chapter_flag = true;
            mshow_down.setImageResource(R.drawable.show_down);
        }

    }

    //"设置"按钮点击事件
    public void ButtonSettingsListerner(View view) {
        if (mSettings_flag) {
            mObjectAnimator.ofFloat(mSettings_Text, "translationY", -mSettings_Text.getHeight(), 0F)
                    .setDuration(1000).start();
            mObjectAnimator.ofFloat(mSettings_Layout, "translationY", mSettings_Layout.getHeight(), mSettings_Layout.getHeight() - mSettings_Layout.getHeight())
                    .setDuration(1000).start();

            mSettings_Text.setVisibility(View.VISIBLE);
            mSettings_Layout.setVisibility(View.VISIBLE);

            mSettings_flag = false;
        } else {
            mObjectAnimator.ofFloat(mSettings_Text, "translationY", 0F, -mSettings_Text.getHeight())
                    .setDuration(1000).start();
            mObjectAnimator.ofFloat(mSettings_Layout, "translationY", 0F, 2 * mSettings_Layout.getHeight())
                    .setDuration(1000).start();
            mSettings_flag = true;
        }

    }
    //返回监听
    public void Back(View view){
        finish();
    }
    //foot的e_mail监听
    public void Foot_e_mail(View view){
        Toast.makeText(Article_ReadActivity.this, "你点击了foot的e_mail", Toast.LENGTH_SHORT).show();
    }
    //foot的addfriend监听
    public void Foot_addfriend(View view){
        Toast.makeText(Article_ReadActivity.this, "你点击了foot的Foot_addfriend", Toast.LENGTH_SHORT).show();
    }

    //推荐监听
    public void MyRecommended(View view){
        Toast.makeText(Article_ReadActivity.this, "你点击了推荐", Toast.LENGTH_SHORT).show();
    }
    //收藏监听
    public void MyCollection(View view){
        Toast.makeText(Article_ReadActivity.this, "你点击了收藏", Toast.LENGTH_SHORT).show();
    }
    //评论监听
    public void MyComments(View view){
        Toast.makeText(Article_ReadActivity.this, "你点击了评论", Toast.LENGTH_SHORT).show();
    }
    //分享监听
    public void MyShare(View view){
        Toast.makeText(Article_ReadActivity.this, "你点击了分享", Toast.LENGTH_SHORT).show();
    }
    //续写监听
    public void MyWrite(View view){
        Toast.makeText(Article_ReadActivity.this, "你点击了续写", Toast.LENGTH_SHORT).show();
    }
    //投票监听
    public void MyVote(View view){
        Intent intent=new Intent(this,VoteActivity.class);
        intent.putExtra("Chapter_Id",listData.get(0).getChapter_id());
//        Toast.makeText(Article_ReadActivity.this, "你点击了投票", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    /**
     * 手机屏幕宽
     *
     * @return
     */
    public static int getWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 手机屏幕高
     *
     * @return
     */
    public static int getHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }


    public void getPath() {
        MyApplication myApplication = (MyApplication) getApplication();
        mPath = myApplication.getUrl_Article();
    }

    //gest请求的到网络数据
    public void getTest() {
        //get请求
        //第一步：设置访问路径

        Intent intent=getIntent();
        int article_id=intent.getIntExtra("Article_Id",1);
        Log.e("abc", "getTest: "+article_id );
        RequestParams params = new RequestParams(mPath);
        params.addQueryStringParameter("article_id",article_id+"");
        //第二步：开始请求，设置请求方式，同时实现回调函数
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //访问成功，参数其实就是PrintWriter写回的值
                //把JSON格式的字符串改为Student对象
                Gson gson = new Gson();
                Type type = new TypeToken<List<ArticleBean>>() {
                }.getType();
                listData = gson.fromJson(result, type);
                initdata(listData);


                //    initdata(list);
          /*      mArticleList.notify();*/

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //访问失败
                Log.e(TAG, "错误原因：" + ex.getMessage());
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

    public void ReadBackground(final String path) {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection mConnection = (HttpURLConnection) url.openConnection();
                    mConnection.setRequestMethod("GET");
                    if (mConnection.getResponseCode() == 200) {
                        InputStream inputStream = mConnection.getInputStream();
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        inputStream.close();

                        Message message = new Message();
                        message.what = 0;
                        handler_background.sendMessage(message);
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}