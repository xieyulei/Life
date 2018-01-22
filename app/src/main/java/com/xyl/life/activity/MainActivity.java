package com.xyl.life.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.xyl.life.R;
import com.xyl.life.adapter.FragmentAdapter;
import com.xyl.life.fragment.BookFragment;
import com.xyl.life.fragment.MovieFragment;
import com.xyl.life.fragment.WeatherFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * APP主页面
 */
public class MainActivity extends AppCompatActivity {

    private long exitTime = 0;

    //private BottomNavigationView mNavigationView;//底部导航
    //底部控件初始化
    private BottomBar mBottomBar;

    private ViewPager mViewPager;//首页中间的viewPager
    private FragmentPagerAdapter mFragmentAdapter;
    private List<Fragment> mFragmentList = new ArrayList<>();//声明存放fragment的集合

    private Context context;   //上下文对象

    private boolean quit = false; //设置退出的标识


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_main);
        initView();//控件初始化
        initListener();//添加事件监听
    }

    /**
     * 初始化MainActivity页面控件
     */
    public void initView() {
        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);
        // mNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        BookFragment bookFragment = new BookFragment();
        MovieFragment movieFragment = new MovieFragment();
        WeatherFragment weatherFragment = new WeatherFragment();

        //给FragmentList添加数据
        mFragmentList.add(bookFragment);
        mFragmentList.add(movieFragment);
        mFragmentList.add(weatherFragment);

        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
    }

    /**
     * 控件的事件监听
     */
    public void initListener() {

        /**
         * 添加事件监听，当点击条目时，会将页面设置对应的文字
         */

        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.nav_book:
                        mViewPager.setCurrentItem(0, true);

                        break;
                    case R.id.nav_movie:
                        mViewPager.setCurrentItem(1, true);

                        break;
                    case R.id.nav_weather:
                        mViewPager.setCurrentItem(2, true);

                        break;
                }
            }
        });

        /**
         * 添加事件监听，如果点击就弹出toast提示
         */

        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setCurrentItem(0);

        //ViewPager页面监听 使用add而不是set
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mBottomBar.setDefaultTab(R.id.nav_book);

                } else if (position == 1) {
                    mBottomBar.setDefaultTab(R.id.nav_movie);

                } else if(position ==2){
                    mBottomBar.setDefaultTab(R.id.nav_weather);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    /**
     * 首页两秒之内连续点击返回键，退出程序
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


//    @Override
//    public void onBackPressed() {
//
//        if (quit == false){     //询问退出程序
//            Toast.makeText(context, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//            new Timer(true).schedule(new TimerTask() {      //启动定时任务
//                @Override
//                public void run() {
//                    quit = false;   //重置退出标识
//                }
//            } , 2000);  //延时２秒执行
//            quit = true;
//        }else {     //确认退出程序
//            super.onBackPressed();
//            finish();
//        }
//    }


}
