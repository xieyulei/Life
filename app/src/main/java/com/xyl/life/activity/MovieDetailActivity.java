package com.xyl.life.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.xyl.life.Interface.GetMovieById;
import com.xyl.life.R;
import com.xyl.life.adapter.CastAdapter;
import com.xyl.life.entity.movie.Casts;
import com.xyl.life.entity.movie.Movie;
import com.xyl.life.util.TextUtil;
import com.xyl.life.view.GravitySnapHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class MovieDetailActivity extends AppCompatActivity {

    private CastAdapter mCastAdapter;
    private List<Casts> mCastsList=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        String id=getIntent().getStringExtra("movie_id");
        String url=getIntent().getStringExtra("movie_url");
        String name=getIntent().getStringExtra("movie_name");
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);

        final CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar_movie_detail);

        final ImageView image=(ImageView)findViewById(R.id.iv_movie_detail_image);
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerView_movie_detail_actors);
        final TextView summary=(TextView)findViewById(R.id.tv_movie_detail_summary);
        mCastAdapter=new CastAdapter(this,mCastsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(mCastAdapter);

        GravitySnapHelper snapHelper=new GravitySnapHelper(GravitySnapHelper.START);
        snapHelper.attachToRecyclerView(recyclerView);

        Glide.with(this).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Palette palette = Palette.from(resource).generate();

                collapsingToolbarLayout.setBackgroundColor(
                        palette.getDarkMutedColor(ContextCompat.getColor(MovieDetailActivity.this,R.color.movie_colorPrimaryDark)));
                image.setImageBitmap(resource);
                //resource.recycle();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_movie_url))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        GetMovieById movie=retrofit.create(GetMovieById.class);
        movie.response(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ResponseBody, Movie>() {
                    @Override
                    public Movie apply(ResponseBody responseBody) throws Exception {
                        Gson gson=new Gson();

                        return gson.fromJson(responseBody.string(),Movie.class);
                    }
                })
                .subscribe(new Observer<Movie>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Movie movie) {
                        summary.setText(TextUtil.firstLineIndent(movie.getSummary(), (int) summary.getPaint().measureText("首行")));
                        mCastsList.addAll(Arrays.asList(movie.getCasts()));
                        mCastAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MovieDetailActivity.this,"onError",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
