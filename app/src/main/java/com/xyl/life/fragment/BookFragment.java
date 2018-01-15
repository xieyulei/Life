package com.xyl.life.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.google.gson.Gson;
import com.jude.rollviewpager.RollPagerView;
import com.xyl.life.R;
import com.xyl.life.adapter.ImageLoopAdapter;
import com.xyl.life.adapter.RecyclerViewAdapter;
import com.xyl.life.entity.book.Book;
import com.xyl.life.entity.book.BookItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * -------------------------
 * Author：doraemon
 * Created by xyl on 2018/1/15.
 * ---------------------------
 * This class is used for:
 */

public class BookFragment extends BaseFragment {

    private String[] urls = {
            "https://api.douban.com/v2/book/isbn/9787115439789",
            "https://api.douban.com/v2/book/isbn/9787302470069",
            "https://api.douban.com/v2/book/isbn/9787115457592",
            "https://api.douban.com/v2/book/isbn/9787121267734",
            "https://api.douban.com/v2/book/isbn/9787121296024",
            "https://api.douban.com/v2/book/isbn/9787115385703",
            "https://api.douban.com/v2/book/isbn/9787121269394",
            "https://api.douban.com/v2/book/isbn/9787115461230",
            "https://api.douban.com/v2/book/isbn/9787512393981"
    };
    private List<BookItem> list;
    private RecyclerViewAdapter adapter;
    private RollPagerView mBookViewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void initView(LayoutInflater inflater, View view, Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.book_recommend).findViewById(R.id.recycler_list);
        // recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));//滑动式图水平显示

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        adapter = new RecyclerViewAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);

        mBookViewPager = (RollPagerView) view.findViewById(R.id.view_pager_bookFragment);
        mBookViewPager.setAdapter(new ImageLoopAdapter(mBookViewPager));

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
                        String responseData = response.body().string();
                        parseJSONWithGSON(responseData, recyclerView);
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

        BookItem item = new BookItem(book.getTitle(), book.getImages().getSmall());
        list.add(item);
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
