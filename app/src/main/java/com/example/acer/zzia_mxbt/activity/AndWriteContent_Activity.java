package com.example.acer.zzia_mxbt.activity;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.acer.zzia_mxbt.R;

public class AndWriteContent_Activity extends AppCompatActivity {
TextView andwrite_title,andwrite_next;
    EditText andwrite_contenttitle,andwrite_content;
    ImageView andwrite_back;
    static int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_and_write_content_);
        initview();
        initdata();
    }

    private void initdata() {
        andwrite_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = getLayoutInflater();
                PopupWindow popupWindow;
                View view;
                view = layoutInflater.inflate(R.layout.andwrite_item, null);
                TextView  preview, publish;
                final Button single,contiue,finish;
                preview = (TextView) view.findViewById(R.id.andwrite_preview);
                publish = (TextView) view.findViewById(R.id.andwrite_publish);
                single = (Button) view.findViewById(R.id.andwrite_single);
                contiue = (Button) view.findViewById(R.id.andwrite_contiue);
                finish = (Button) view.findViewById(R.id.andwrite_finish);
                popupWindow=new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(0*1101000000));
                popupWindow.setOutsideTouchable(true);
                popupWindow.showAtLocation(andwrite_next, Gravity.BOTTOM,0,0);
                popupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
                //子控件的单击事件
                //单篇单击事件
                single.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        single.setBackgroundResource(R.drawable.anniu);
                        contiue.setBackgroundResource(R.drawable.anniu3);
                        finish.setBackgroundResource(R.drawable.anniu2);
                        flag=1;
                    }
                });
                //连载中单击事件
                contiue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        single.setBackgroundResource(R.drawable.author_anniu);
                        contiue.setBackgroundResource(R.drawable.continue_anniu3);
                        finish.setBackgroundResource(R.drawable.anniu2);
                        flag=2;
                    }
                });
                //已完结单击事件
                finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        single.setBackgroundResource(R.drawable.author_anniu);
                        contiue.setBackgroundResource(R.drawable.anniu3);
                        finish.setBackgroundResource(R.drawable.novel_anniu2);
                        flag=3;
                    }
                });
                //预览单击事件,预览会把文章信息先取出，进行显示，暂不存入数据库
                preview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       String title= andwrite_contenttitle.getText().toString();
                        String content=andwrite_content.getText().toString();
                        //然后跳转到内容界面
                    }
                });
                //发布单击事件，将文章信息存入到数据库
                publish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title= andwrite_contenttitle.getText().toString();
                        String content=andwrite_content.getText().toString();
                        String selected="";
                        if(flag==1) {
                             selected = "单篇";
                        }else if(flag==2){
                             selected = "连载中";
                        }else if(flag==3){
                             selected = "已完结";
                        }

                    }
                });

            }
        });
    }



    private void initview() {
        andwrite_title= (TextView) findViewById(R.id.andwrite_title);
        andwrite_next= (TextView) findViewById(R.id.andwrite_next);
        andwrite_contenttitle= (EditText) findViewById(R.id.andwrite_contenttitle);
        andwrite_content= (EditText) findViewById(R.id.andwrite_content);
        andwrite_back= (ImageView) findViewById(R.id.andwrite_back);
    }
}
