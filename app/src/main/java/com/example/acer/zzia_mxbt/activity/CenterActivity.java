package com.example.acer.zzia_mxbt.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.adapters.CenterViewPagerAdapter;
import com.example.acer.zzia_mxbt.fragment.CenterFragment;
import com.example.acer.zzia_mxbt.fragment.CenterFragment1;
import com.example.acer.zzia_mxbt.fragment.CenterFragment2;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class CenterActivity extends AppCompatActivity {
    private static final int BACK_IMAGE_CODE = 1;
    Toolbar mToolbar;
    CollapsingToolbarLayout ctbl ;
    SimpleDraweeView simpleDraweeView;
    SimpleDraweeView head_simpleDraweeView;
    TextView textView ;
    ViewPager center_viewPager ;
    CenterViewPagerAdapter centerViewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(CenterActivity.this);
        setContentView(R.layout.activity_center);
        iniView();
        setView();
        intiPager();

    }

    private void intiPager() {
        center_viewPager = (ViewPager) findViewById(R.id.center_pager);
        centerViewPagerAdapter = new CenterViewPagerAdapter(getSupportFragmentManager());
        centerViewPagerAdapter.addFragment(CenterFragment.newInstance(), "我的故事");//添加Fragment
        centerViewPagerAdapter.addFragment(CenterFragment1.newInstance(), "我的收藏");
        centerViewPagerAdapter.addFragment(CenterFragment2.newInstance(), "我的推荐");
        center_viewPager.setAdapter(centerViewPagerAdapter);//设置适配器

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("我的故事"));//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab().setText("我的收藏"));
        mTabLayout.addTab(mTabLayout.newTab().setText("我的推荐"));
        mTabLayout.setupWithViewPager(center_viewPager);
    }

    private void setView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String path2 = "http://139.129.58.244:8080/image/fengjing1.jpg";
        Uri uri2 = Uri.parse(path2);
        GenericDraweeHierarchyBuilder builder2 = new GenericDraweeHierarchyBuilder(CenterActivity.this.getResources());
        GenericDraweeHierarchy hierarchy2 = builder2
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setPlaceholderImage(CenterActivity.this.getResources().getDrawable(R.drawable.index_img1))
                .build();
        DraweeController controller2 = Fresco.newDraweeControllerBuilder()
                .setUri(uri2)
                .build();
        simpleDraweeView.setController(controller2);
        simpleDraweeView.setHierarchy(hierarchy2);



        String path1 = "http://139.129.58.244:8080/image/UserDefault.png";
        Uri uri1 = Uri.parse(path1);
        GenericDraweeHierarchyBuilder builder1 = new GenericDraweeHierarchyBuilder(CenterActivity.this.getResources());
        GenericDraweeHierarchy hierarchy1 = builder1
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setPlaceholderImage(CenterActivity.this.getResources().getDrawable(R.drawable.index_img1))
                .setRoundingParams(new RoundingParams().setRoundAsCircle(true))
                .build();
        DraweeController controller1 = Fresco.newDraweeControllerBuilder()
                .setUri(uri1)
                .build();
        head_simpleDraweeView.setController(controller1);
        head_simpleDraweeView.setHierarchy(hierarchy1);
        final Handler hanler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case BACK_IMAGE_CODE:
                        Palette.from((Bitmap)msg.obj).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(final Palette palette) {
                                int defaultColor = getResources().getColor(R.color.blue);
                                int defaultTitleColor = getResources().getColor(R.color.white);
                                int bgColor = palette.getDarkVibrantColor(defaultColor);
                                int titleColor = palette.getLightVibrantColor(defaultTitleColor);
                                ctbl.setContentScrimColor(bgColor);
                                ctbl.setCollapsedTitleTextColor(titleColor);
                                ctbl.setExpandedTitleColor(titleColor);
                                textView.setTextColor(titleColor);
                            }
                        });

                }
            }
        };


            new Thread(){
                @Override
                public void run() {
                    super.run();
                    URL url = null;
                    try {
                        url = new URL("http://139.129.58.244:8080/image/fengjing1.jpg");
                        InputStream is = url.openStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        Message msg = new Message();
                        msg.obj = bitmap;
                        msg.what = BACK_IMAGE_CODE;
                        hanler.sendMessage(msg);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
    }

    private void iniView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        ctbl= (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.center_background);
       textView = (TextView) findViewById(R.id.center_edit);
        head_simpleDraweeView = (SimpleDraweeView) findViewById(R.id.center_head);

    }
}
