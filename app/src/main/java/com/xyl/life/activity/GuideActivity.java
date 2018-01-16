package com.xyl.life.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.xyl.life.R;
import com.xyl.life.adapter.GuideAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieyulei on 2017/11/23.
 */
public class GuideActivity extends Activity implements View.OnClickListener {

    private ViewPager mViewPager;
    private Button mBtnSkip;
    private Button mBtnDone;
    private ImageButton mIbNext;
    private LinearLayout mPointGroup;
    private ImageView mWhitePoint;

    //声明圆点之剑的间隔
    private int mPointMargin;

    public List<ImageView> mImageList =new ArrayList<>();
    private int[] mIcons = {R.mipmap.guide1, R.mipmap.guide2, R.mipmap.guide3, R.mipmap.guide4};
    private SharedPreferences mSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

    //    ActivityCollector.addActivity(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        mSp = getSharedPreferences("config", MODE_PRIVATE);
        //初始化控件
        mViewPager = (ViewPager) findViewById(R.id.splash_view_pager);
        mBtnSkip = (Button) findViewById(R.id.button_skip);
        mBtnDone = (Button) findViewById(R.id.button_done);
        mIbNext = (ImageButton) findViewById(R.id.imageButton_next);
        mPointGroup = (LinearLayout) findViewById(R.id.point_group);
        mWhitePoint = (ImageView) findViewById(R.id.white_point);

        mIbNext.setOnClickListener(this);
        mBtnDone.setOnClickListener(this);
        mBtnSkip.setOnClickListener(this);

        //准备要展示的图片集合
        mImageList = new ArrayList<>();
        for (int i = 0; i < mIcons.length; i++) {
            ImageView image = new ImageView(this);
            image.setBackgroundResource(mIcons[i]);

            mImageList.add(image);

            // 设置底部小圆点
            ImageView point = new ImageView(this);
            point.setImageResource(R.drawable.shape_point_normal);

            // 设置白点的布局参数
            int pointSize = getResources().getDimensionPixelSize(R.dimen.point_size);
            RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(pointSize, pointSize);
            mWhitePoint.setLayoutParams(params1);

            // 设置灰色点的布局参数
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(pointSize, pointSize);
            if (i > 0) {
                params2.leftMargin = getResources().getDimensionPixelSize(R.dimen.left_margin);
            }
            point.setLayoutParams(params2);
            mPointGroup.addView(point);
        }

        GuideAdapter adapter = new GuideAdapter(mImageList);
        mViewPager.setAdapter(adapter);

        // 获取视图树对象，通过监听白点布局的显示，然后获取两个圆点之间的距离
        mWhitePoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mPointMargin = mPointGroup.getChildAt(1).getLeft() - mPointGroup.getChildAt(0).getLeft();
                mWhitePoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


        /**
         * 为viewPager添加监听事件
         */


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 页面滑动的时候，动态的获取小圆点的左边距
                int marginLeft = (int) (mPointMargin * (position + positionOffset));

                // 获取布局参数，然后设置布局参数
                RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) mWhitePoint.getLayoutParams();

                // 修改参数
                params3.leftMargin = marginLeft;

                // 重新设置布局参数
                mWhitePoint.setLayoutParams(params3);
            }

            @Override
            public void onPageSelected(int position) {
                //如果viewPager展示页面是页面最后一页
                if (position == mIcons.length - 1) {
                    mBtnDone.setVisibility(View.VISIBLE);
                    mIbNext.setVisibility(View.GONE);
                    mBtnSkip.setVisibility(View.GONE);

                } else {
                    //如果viewPager展示页面不是最后一页
                    mBtnSkip.setVisibility(View.VISIBLE);
                    mIbNext.setVisibility(View.VISIBLE);
                    mBtnDone.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_skip:
                EnterMain();
                break;
            case R.id.button_done:
                EnterMain();
                break;
            case R.id.imageButton_next:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            default:
                break;
        }

    }

    /**
     * 定义进入主页的方法
     */
    private void EnterMain() {
        startActivity(new Intent(GuideActivity.this, MainActivity.class));
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ActivityCollector.removeActivity(this);
    }
}
