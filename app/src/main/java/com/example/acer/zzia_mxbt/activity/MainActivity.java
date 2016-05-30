package com.example.acer.zzia_mxbt.activity;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.zzia_mxbt.adapters.IndexFragmentPagerAdapter;
import com.example.acer.zzia_mxbt.bean.User;
import com.example.acer.zzia_mxbt.fragment.ViewFragment;
import com.example.acer.zzia_mxbt.fragment.ViewFragment1;
import com.example.acer.zzia_mxbt.fragment.ViewFragment2;
import com.example.acer.zzia_mxbt.fragment.ViewFragment3;
import com.example.acer.zzia_mxbt.fragment.ViewFragment4;
import com.example.acer.zzia_mxbt.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    ViewPager index_viewPager;
    ImageView index_menu;//导航菜单按钮
    ImageView kind_ring;//分类图片（圆环）
    TextView kind_content;//分类内容


    //隐藏的布局
    RelativeLayout loadding_layout;

    //用户头像
    SimpleDraweeView headimg;


    //用户名
    TextView userName;

    //四个点
    ImageView point_any;
    ImageView point_green;
    ImageView point_yellow;
    ImageView point_blue;
    ImageView point_red;

    private List<Fragment> viewProviderList;//视图列表
    //五个分类的视图
    ViewFragment vfm;
    ViewFragment1 vfm1;
    ViewFragment2 vfm2;
    ViewFragment3 vfm3;
    ViewFragment4 vfm4;
    //适配器
    IndexFragmentPagerAdapter ifpa;
    //侧滑视图
    NavigationView navigationView;
    //侧滑头部视图
    View headerView;
    public Handler handler;
    private static final int HIDDEN_CODE = 1;
    private static final int APPEAR_CODE = 2;
    //是否登录
    private boolean isLogin = false;
    private User user;

    //更多按钮
    ImageView more;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(MainActivity.this);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        getLoginParam();
        initView();
        initData();
        setView();
        getHandler();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();



        navigationView.setNavigationItemSelectedListener(this);

    }

    private void getLoginParam() {
        Intent intent = getIntent();
        isLogin = intent.getBooleanExtra("isLogin", false);
        user = (User) intent.getSerializableExtra("user");
    }

    private void initData() {
        Uri uri;
        if(isLogin ){
           uri  = Uri.parse(user.getUhead());
            userName.setText(user.getUnickname());
        }else{
            uri = Uri.parse("http://139.129.58.244:8080/image/fengjing1.jpg");
            userName.setText("游客(点击登录)");
        }
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY)
                .setPlaceholderImage(getResources().getDrawable(R.mipmap.ic_launcher))
                .setRoundingParams(new RoundingParams().setRoundAsCircle(true))
                .build();

//

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .build();
        headimg.setController(controller);
        headimg.setHierarchy(hierarchy);

    }


    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.side_search) {
            // Handle the camera action
            Toast.makeText(MainActivity.this, "发现", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.side_message) {
            Toast.makeText(MainActivity.this, "消息中心", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.side_collect) {
            Toast.makeText(MainActivity.this, "我的收藏", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.side_recentread) {
            Toast.makeText(MainActivity.this, "最近阅读", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.side_download) {
            Toast.makeText(MainActivity.this, "离线阅读", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.side_config) {
            Toast.makeText(MainActivity.this, "设置", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.side_nightmode) {
            Toast.makeText(MainActivity.this, "夜间模式", Toast.LENGTH_SHORT).show();
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void initView() {
        index_menu = (ImageView) findViewById(R.id.index_menu);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        viewProviderList = new ArrayList<Fragment>();
        addView(viewProviderList);
        ifpa = new IndexFragmentPagerAdapter(getSupportFragmentManager(), viewProviderList);
        index_viewPager = (ViewPager) findViewById(R.id.index_pager);
        index_viewPager.setAdapter(ifpa);
        kind_ring = (ImageView) findViewById(R.id.kind_ring);
        kind_content = (TextView) findViewById(R.id.kind_content);
        point_any = (ImageView) findViewById(R.id.point_any);
        point_green = (ImageView) findViewById(R.id.point_green);
        point_yellow = (ImageView) findViewById(R.id.point_yellow);
        point_blue = (ImageView) findViewById(R.id.point_blue);
        point_red = (ImageView) findViewById(R.id.point_red);
        ViewGroup v = (ViewGroup) findViewById(R.id.nav_view);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        headimg = (SimpleDraweeView) headerView.findViewById(R.id.sidehead);
        userName = (TextView) headerView.findViewById(R.id.sidetext);
        loadding_layout = (RelativeLayout) findViewById(R.id.loadding_layout);
        more = (ImageView) findViewById(R.id.index_more);
    }

    //向视图列表中添加视图作为数据源
    public void addView(List<Fragment> list) {

        vfm = new ViewFragment();
        vfm1 = new ViewFragment1();
        vfm2 = new ViewFragment2();
        vfm3 = new ViewFragment3();
        vfm4 = new ViewFragment4();

        list.add(vfm);

        list.add(vfm1);

        list.add(vfm2);

        list.add(vfm3);

        list.add(vfm4);
    }

    private void setView() {
        index_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        index_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        kind_ring.setImageResource(R.drawable.ring_any);
                        kind_content.setText("首页");
                        resetPoint();
                        point_any.setImageResource(R.drawable.point_select);
                        break;
                    case 1:
                        kind_ring.setImageResource(R.drawable.ring_green);
                        kind_content.setText("真事");
                        resetPoint();
                        point_green.setImageResource(R.drawable.point_select);
                        break;
                    case 2:
                        kind_ring.setImageResource(R.drawable.ring_yellow);
                        kind_content.setText("创作");
                        resetPoint();
                        point_yellow.setImageResource(R.drawable.point_select);
                        break;
                    case 3:
                        kind_ring.setImageResource(R.drawable.ring_blue);
                        kind_content.setText("灵异");
                        resetPoint();
                        point_blue.setImageResource(R.drawable.point_select);
                        break;
                    case 4:
                        kind_ring.setImageResource(R.drawable.ring_red);
                        kind_content.setText("生活");
                        resetPoint();
                        point_red.setImageResource(R.drawable.point_select);
                        break;

                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        headimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isLogin) {
                    //已登录
                    Toast.makeText(MainActivity.this, "您自己的头像被点击了！", Toast.LENGTH_SHORT).show();
                } else {
                    //未登录
                    Intent intent = new Intent(MainActivity.this, QiyuActivity.class);
                    startActivity(intent);
                }
            }
        });


        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = getLayoutInflater();
                PopupWindow popupWindow;
                View view;
                view = layoutInflater.inflate(R.layout.index_drop_down, null);
                TextView drop1, drop2, drop3;
                drop1 = (TextView) view.findViewById(R.id.drop1);
                drop2 = (TextView) view.findViewById(R.id.drop2);
                drop3 = (TextView) view.findViewById(R.id.drop3);

                    drop1.setText("排行榜");
                    drop2.setText("2");
                    drop3.setText("3");


                popupWindow = new PopupWindow(view, 250, 400, true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(0 * 1101000000));
                popupWindow.setOutsideTouchable(true);
                popupWindow.showAsDropDown(more, -230, 20);
                popupWindow.setAnimationStyle(R.style.anim_menu_animation);
                drop1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, RankActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    public void getHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case HIDDEN_CODE:
                        navigationView.bringToFront();
                        loadding_layout.setVisibility(View.GONE);

                        break;
                    case APPEAR_CODE:
                        loadding_layout.bringToFront();
                        loadding_layout.setVisibility(View.VISIBLE);
                        break;
                }
            }
        };
    }

    public void resetPoint() {
        point_any.setImageResource(R.drawable.point_unselect);
        point_green.setImageResource(R.drawable.point_unselect);
        point_yellow.setImageResource(R.drawable.point_unselect);
        point_blue.setImageResource(R.drawable.point_unselect);
        point_red.setImageResource(R.drawable.point_unselect);
    }
}