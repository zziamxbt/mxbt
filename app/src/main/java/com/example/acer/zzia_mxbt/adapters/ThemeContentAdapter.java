package com.example.acer.zzia_mxbt.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.acer.zzia_mxbt.bean.IndexBean;


import java.util.List;

/**
 * Created by qiyu on 2016/5/31.
 */
public class ThemeContentAdapter extends BaseAdapter {
    List<IndexBean> mList;
    Context mContext;
    LayoutInflater mInflater;

    public ThemeContentAdapter(Context context, List<IndexBean> list) {
        mContext = mContext;
        mList = mList;
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

   // class ViewHolder

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
