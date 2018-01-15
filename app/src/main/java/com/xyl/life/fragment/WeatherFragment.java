package com.xyl.life.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xyl.life.R;

/**
 * -------------------------
 * Author：doraemon
 * Created by xyl on 2018/1/15.
 * ---------------------------
 * This class is used for:
 */

public class WeatherFragment  extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_weather,container,false);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
