package com.xyl.life.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xyl.life.R;
import com.xyl.life.entity.book.Book;

import java.util.List;

/**
 * 用于展示相关图书详情信息
 */

public class BookDetailActivity extends AppCompatActivity {

    private ImageView mIvBook;
    private TextView mTvBookTitle; //title图书名称
    private TextView mTvAuthor_intro;  //author_intro  作者简介
    private TextView mTvSummary; //summary 图书简介
    private TextView mTvPrice; // price 图书价格

    private TextView authorTv;
    private TextView bookTv;


    private List<Book> mBookItemList;
    //title图书名称; author_intro  作者简介; summary 图书简介;price 图书价格


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail_item);
        initView();//初始化布局页面的控件`
    }

    private void initView() {
        mIvBook = (ImageView) findViewById(R.id.book_info_image);//图书图片
        mTvBookTitle = (TextView) findViewById(R.id.book_info_title);//图书名称
        mTvPrice = (TextView) findViewById(R.id.book_info_price);//图书价格
        mTvAuthor_intro = (TextView) findViewById(R.id.book_info_author_intro);//作者介绍
        mTvSummary = (TextView) findViewById(R.id.book_info_summary);//图书简介

        authorTv = (TextView) findViewById(R.id.authorTv);
        bookTv = (TextView) findViewById(R.id.bookTv);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_book_detail);

        Book book = (Book) getIntent().getSerializableExtra("book");

        if (book.getAuthor_intro().equals("")) {
            authorTv.setVisibility(View.GONE);
        } else {
            mTvAuthor_intro.setText(book.getAuthor_intro());
        }


        if (book.getSummary().equals("")) {
            bookTv.setVisibility(View.GONE);
        } else {
            mTvSummary.setText(book.getSummary());
        }


        mTvBookTitle.setText(book.getTitle());
        mTvPrice.setText(book.getPrice());
//      mTvAuthor_intro.setText(book.getAuthor_intro());
//      mTvSummary.setText(book.getSummary());
//      mIvBook.setImageResource();
//      Glide.with(this).load(book.getImages().getLarge()).into(mIvBook);
        Glide.with(this).asBitmap().load(book.getImages().getLarge()).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Palette palette = Palette.from(resource).generate();

//                collapsingToolbarLayout.setBackgroundColor(//获取柔和颜色中的暗色
//                        palette.getDarkMutedColor(ContextCompat.getColor(BookInfoActivity.this,R.color.colorPrimaryDark)));

                collapsingToolbarLayout.setBackgroundColor(//获取柔和颜色中的亮颜色
                        palette.getLightMutedColor(ContextCompat.getColor(BookDetailActivity.this, android.R.color.transparent)));

//                collapsingToolbarLayout.setBackgroundColor(//获取鲜艳颜色中的亮颜色
//                        palette.getLightVibrantColor(ContextCompat.getColor(BookInfoActivity.this,android.R.color.transparent)));
//
//                collapsingToolbarLayout.setBackgroundColor(//获取鲜艳颜色中的暗色
//                        palette.getDarkVibrantColor(ContextCompat.getColor(BookInfoActivity.this,android.R.color.transparent)));
//
//                collapsingToolbarLayout.setBackgroundColor(//获取鲜艳颜色
//                        palette.getVibrantColor(ContextCompat.getColor(BookInfoActivity.this,android.R.color.transparent)));

//效果差                collapsingToolbarLayout.setBackgroundColor(//获取柔和颜色
//                        palette.getMutedColor(ContextCompat.getColor(BookInfoActivity.this,android.R.color.transparent)));

                mIvBook.setImageBitmap(resource);
            }
        });
    }

}
