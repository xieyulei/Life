package com.xyl.life.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.Gson;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.xyl.life.R;
import com.xyl.life.adapter.ImageLoopAdapter;
import com.xyl.life.adapter.BookRecyclerViewAdapter;
import com.xyl.life.entity.book.Book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 用于显示图书的Fragment
 */

public class BookFragment extends BaseFragment {

    private String[] urls = {
            "https://api.douban.com/v2/book/isbn/9787115439789",
            "https://api.douban.com/v2/book/isbn/9787302315582",
            "https://api.douban.com/v2/book/isbn/9787115457592",

            "https://api.douban.com/v2/book/isbn/9787121267734",
            "https://api.douban.com/v2/book/isbn/9787121296024",
            "https://api.douban.com/v2/book/isbn/9787115385703",

            "https://api.douban.com/v2/book/isbn/9787121269394",
            "https://api.douban.com/v2/book/isbn/9787115461230",
            "https://api.douban.com/v2/book/isbn/9787512393981",

            "https://api.douban.com/v2/book/isbn/9787111539582",
            "https://api.douban.com/v2/book/isbn/9787121275470",
            "https://api.douban.com/v2/book/isbn/9787302470069",

            "https://api.douban.com/v2/book/isbn/9787115213945",
            "https://api.douban.com/v2/book/isbn/9781430226598",
            "https://api.douban.com/v2/book/isbn/9787115362865"
    };

    private String[] urls_native = {
            "http://book.dangdang.com/20180110_jrkr",
            "http://baby.dangdang.com/20180109_cyto",
            "http://baby.dangdang.com/20180111_kjrw",
            "http://book.dangdang.com/20180110_1pfu",
            "http://baby.dangdang.com/20180109_cyto"
    };
    private List<Book> list;
    private BookRecyclerViewAdapter adapter;
    private RollPagerView mBookViewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void initView(LayoutInflater inflater, View view, Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.book_recommend).findViewById(R.id.recycler_list);


        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();

        adapter = new BookRecyclerViewAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);

        //图书页面上方相关信息展示
        mBookViewPager = (RollPagerView) view.findViewById(R.id.view_pager_bookFragment);
        mBookViewPager.setAdapter(new ImageLoopAdapter(mBookViewPager));

        //ViewPager页面监听 使用add而不是set
        mBookViewPager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int index = position;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(urls_native[position]));
                startActivity(intent);
            }
        });

        sendRequestWithOkHttp(recyclerView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book;
    }


    private void sendRequestWithOkHttp(final View recyclerView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    for (int i = 0; i < urls.length; i++) {
                        Request request = new Request.Builder().url(urls[i]).build();
                        Response response = client.newCall(request).execute();
                        if (response.code() == 200) {
                            String responseData = response.body().string();
                            parseJSONWithGSON(responseData, recyclerView);
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void parseJSONWithGSON(String jsonData, View recyclerView) {
        Gson gson = new Gson();
        Book book = gson.fromJson(jsonData, Book.class);
        //BookItem item = new BookItem(book.getTitle(), book.getImages().getSmall());
        list.add(book);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
