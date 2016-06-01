package com.example.acer.zzia_mxbt.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.zzia_mxbt.R;
import com.example.acer.zzia_mxbt.bean.ArticleBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 晓勇 on 2015/7/12 0012.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<ArticleBean> datas;
    private Context context;
    private List<Integer> lists;

    public MyRecyclerViewAdapter(Context context, List<ArticleBean> datas) {
        this.datas = datas;
        this.context = context;
        getRandomHeights(datas);
    }

    private void getRandomHeights(List<ArticleBean> datas) {
        lists = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            lists.add((int) (700));
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.index_content, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = lists.get(position);//把随机的高度赋予item布局
        holder.itemView.setLayoutParams(params);
        holder.mTextView.setText(position+"");
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
    SimpleDraweeView headimg;
    TextView name;
    TextView time;
    TextView maincontent;
    ImageView kindimg;
    TextView title;
    TextView kindcontent;
    SimpleDraweeView mainimg;
    public MyViewHolder(View itemView) {
        super(itemView);
        headimg= (SimpleDraweeView) itemView.findViewById(R.id.index_head);
        name = (TextView) itemView.findViewById(R.id.index_username);
        time = (TextView) itemView.findViewById(R.id.index_time);
        kindimg = (ImageView) itemView.findViewById(R.id.index_list_kind_img);
        kindcontent = (TextView) itemView.findViewById(R.id.index_list_kind);
        mainimg = (SimpleDraweeView) itemView.findViewById(R.id.index_img);
        title = (TextView) itemView.findViewById(R.id.list_title);
        maincontent = (TextView) itemView.findViewById(R.id.list_maincontent);
    }
}
