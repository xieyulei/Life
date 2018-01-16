package com.xyl.life.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class GuideAdapter extends PagerAdapter {
    private List<ImageView> mImageList=new ArrayList<>();


    public GuideAdapter(List<ImageView> mImageList) {
        this.mImageList=mImageList;
    }

    /**
     * 获取要显示图片的数量，这里为imageView的数量
     */
    @Override
    public int getCount() {
        return mImageList.size();
    }

//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return false;
//    }

    /**
     * 此方法用于判断是否是同一张图片，这里将两个参数比较返回即可
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    /**
     * PagerAdapter只缓存四张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       // super.destroyItem(container, position, object);
        container.removeView((View) object);
    }


    /**
     * 当要显示图片可以进行缓存的时候，就会调用这个方法进行图片的初始化，
     * 我们将imageView加入到viewGroup中，然后作为返回值返回即可
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // 将图片添加到ViewPager容器
        container.addView(mImageList.get(position));

        return mImageList.get(position);

    }
}
