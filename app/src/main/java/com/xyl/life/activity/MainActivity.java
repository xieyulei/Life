package com.xyl.life.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.xyl.life.R;
import com.xyl.life.adapter.FragmentAdapter;
import com.xyl.life.fragment.BookFragment;
import com.xyl.life.fragment.MovieFragment;
import com.xyl.life.fragment.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private long exitTime = 0;
    private BottomNavigationView mNavigationView;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mFragmentAdapter;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();

    private BookFragment bookFragment;
    private MovieFragment movieFragment;
    WeatherFragment weatherFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();

    }

    /**
     * 初始化MainActivity页面控件
     */
    public void initView() {
        mNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);

        bookFragment = new BookFragment();
        movieFragment = new MovieFragment();
        weatherFragment=new WeatherFragment();

        //给FragmentList添加数据
        mFragmentList.add(bookFragment);
        mFragmentList.add(movieFragment);
        mFragmentList.add(weatherFragment);

    }

    /**
     * 控件的事件监听
     */
    public void initListener() {
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
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
                return true;
            }
        });

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
                    mNavigationView.setSelectedItemId(R.id.nav_book);

                } else if (position == 1) {
                    mNavigationView.setSelectedItemId(R.id.nav_movie);
                } else {
                    mNavigationView.setSelectedItemId(R.id.nav_weather);
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
}
