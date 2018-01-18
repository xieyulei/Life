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

    private Handler mHandler = new Handler();
    private SharedPreferences mSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        mSp = getSharedPreferences("config", MODE_PRIVATE);
        final SharedPreferences.Editor editor = mSp.edit();

        //默认第一次打开APP
        boolean firstEnter = mSp.getBoolean("guide", true);

        /**
         * 如果是第一次打开，则进入引导页面
         * 引导页面画面结束之后，设置为非第一次进入APP
         */
        if (firstEnter) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    finish();
                    //  editor.putBoolean("guide", true);//设置为true，每次都会进入启动页，设置为false，第一次打开会进入启动页，之后则不会
                    editor.putBoolean("guide", false);
                    editor.commit();
                }
            }, 1000);
        } else {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }, 2000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
