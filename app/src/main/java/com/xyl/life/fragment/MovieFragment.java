package com.xyl.life.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.xyl.life.Interface.Top250Movie;
import com.xyl.life.R;
import com.xyl.life.activity.MovieDetailActivity;
import com.xyl.life.adapter.MovieAdapter;
import com.xyl.life.entity.movie.Movie;
import com.xyl.life.entity.movie.Top250;
import com.xyl.life.util.customView.SwipeRefreshLayoutEx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 用于显示电影的Fragment
 */

public class MovieFragment extends BaseFragment implements SwipeRefreshLayoutEx.OnRefreshListener,MovieAdapter.OnItemClickListener{

    private SwipeRefreshLayoutEx mRefreshLayout;
    private List<Movie> list = new ArrayList<>();
    private MovieAdapter adapter;
    private int start = 0;
    private static final int COUNT = 10;
    final String baseUrl = "https://api.douban.com/v2/";
    private Top250Movie movie;
    private Retrofit retrofit;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(LayoutInflater inflater, View view, Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_movie_main);
        mRefreshLayout = view.findViewById(R.id.SwipeRefreshLayout_top250);
        mRefreshLayout.setPullPosition(Gravity.BOTTOM);
        mRefreshLayout.setRefreshDrawableStyle(SwipeRefreshLayoutEx.ARROW);
        mRefreshLayout.setOnRefreshListener(this);

        adapter = new MovieAdapter(getContext(), list);
        adapter.setOnItemClickListener(this);
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movie = retrofit.create(Top250Movie.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            //TODO now it's visible to user
            getData();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie;
    }

    @Override
    public void onRefresh() {
        getData();
    }

    private void getData() {
//        final ProgressDialog dialog = new ProgressDialog(getActivity());
//        dialog.show();
        movie.response(0, 10)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Top250>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Top250 movies) {
                        Log.e("ttt", movies.getTitle());
                        list.addAll(Arrays.asList(movies.getSubjects()));
                        adapter.notifyDataSetChanged();
                        mRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError", e.getMessage());
                      //  dialog.dismiss();
                        mRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onComplete() {
                        start += COUNT;
                        mRefreshLayout.setRefreshing(false);
                     //   dialog.dismiss();
                    }
                });

    }




    @Override
    public void onClick(View view, int position, Movie movie) {
        Intent intent=new Intent(getActivity(),MovieDetailActivity.class);
        intent.putExtra("movie_id",movie.getId());
        intent.putExtra("movie_url",movie.getImages().getMedium());
        intent.putExtra("movie_name",movie.getTitle());
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(), view.findViewById(R.id.iv_movie_cover), getString(R.string.share_element_movie));
        startActivity(intent,options.toBundle());
    }
}
