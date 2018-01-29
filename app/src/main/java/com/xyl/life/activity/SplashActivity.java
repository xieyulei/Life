package com.xyl.life.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.xyl.life.R;

/**
 * APP启动页
 */
public class SplashActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();//创建一个新的线程
    private SharedPreferences mSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);//加载启动页页面布局

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);//让引导页图片完全显示，包括状态栏

        //当Activity创建时，使用SharedPreference将config信息存储，MODE_PRIVATE：为默认操作模式，表示该文件是私有数据，只能被应用本身访问，在该模式下，写入的内容会覆盖原文件的内容，
        mSp = getSharedPreferences("config", MODE_PRIVATE);//设置偏好存储，用于存储参数
        final SharedPreferences.Editor editor = mSp.edit();

        //默认第一次打开APP,变量firstEnter为true;
        boolean firstEnter = mSp.getBoolean("guide", true);

        /**
         * 如果是第一次打开，则进入引导页面
         * 引导页面画面结束之后，设置为非第一次进入APP
         */
        if (firstEnter) {
            //mHandler.postDelayed:让线程等待1秒钟
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //从启动页跳转到引导页面
                    startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    //结束，否则进入引导页面点击退出，程序会退出到引导页面
                    finish();
                   // editor.putBoolean("guide", true);//设置为true，每次都会进入启动页，设置为false，第一次打开会进入启动页，之后则不会
                    editor.putBoolean("guide", false);
                    editor.commit();
                }
            }, 1000);
        } else {
            //第一次程序不会走到这里，但是第二次启动时，程序会走到这里，因为此时的 firstEnter已经为false(第一次启动后，设置为false)
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //延迟两秒启动，从启动页直接跳转到APP首页
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }, 2000);
        }
    }

    /**
     * 此方法在Activity被销毁时调用，添加finish方法，即进入首页或引导页，点击返回不会再退到启动页面
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
