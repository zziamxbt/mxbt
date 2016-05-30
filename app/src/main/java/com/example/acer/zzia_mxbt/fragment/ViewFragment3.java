package com.example.acer.zzia_mxbt.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.acer.zzia_mxbt.R;

/**
 * Created by acer on 2016/5/7.
 */
public class ViewFragment3 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.index_pager_provider3,container , false);
        return view;
    }
}
