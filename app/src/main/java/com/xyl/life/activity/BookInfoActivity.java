package com.xyl.life.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xyl.life.R;
import com.xyl.life.entity.book.Book;

import java.util.List;

/**
 * 用于展示相关图书详情信息
 */

public class BookInfoActivity extends AppCompatActivity {

    private ImageView mIvBook;
    private TextView mTvBookTitle; //title图书名称
    private TextView mTvAuthor_intro;  //author_intro  作者简介
    private TextView mTvSummary; //summary 图书简介
    private TextView mTvPrice; // price 图书价格
    private Context mContext;

    private List<Book> mBookItemList;
    //title图书名称; author_intro  作者简介; summary 图书简介;price 图书价格


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_info_activity);
        initView();//初始化布局页面的控件`
    }


    private void initView() {
        mIvBook=(ImageView) findViewById(R.id.book_info_image);//图书图片
        mTvBookTitle=(TextView)findViewById(R.id.book_info_title);//图书名称
        mTvPrice=(TextView)findViewById(R.id.book_info_price);//图书价格
        mTvAuthor_intro=(TextView)findViewById(R.id.book_info_author_intro);//作者介绍
        mTvSummary = (TextView)findViewById(R.id.book_info_summary);//图书简介


        Book book= (Book) getIntent().getSerializableExtra("book");
        mTvBookTitle.setText(book.getTitle());
        mTvPrice.setText(book.getPrice());
        mTvAuthor_intro.setText(book.getAuthor_into());
        mTvSummary.setText(book.getSummary());

//        Book item = mBookItemList.get(position);
//        mIvBook.setImageResource(Glide.with(mContext).load(item.getImages().getSmall()).into(holder.mBookIcon));

    }
}
