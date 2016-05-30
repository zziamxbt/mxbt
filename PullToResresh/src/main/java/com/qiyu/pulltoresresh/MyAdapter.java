package com.qiyu.pulltoresresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by qiyu on 2016/5/13.
 */
public class MyAdapter extends BaseAdapter {
    LayoutInflater mInflater;
    Context mContext;
    List<Music> mList;

    public MyAdapter(Context context, List<Music> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.title_text = (TextView) convertView.findViewById(R.id.title_textview);
            viewHolder.singer_text = (TextView) convertView.findViewById(R.id.singer_textview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Music music = mList.get(position);
        viewHolder.title_text.setText(music.getTitle());
        viewHolder.singer_text.setText(music.getSinger());
        return convertView;
    }

    class ViewHolder {
        TextView title_text;
        TextView singer_text;
    }
}
