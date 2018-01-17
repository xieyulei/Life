package com.xyl.life.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xyl.life.R;
import com.xyl.life.entity.movie.Casts;
import com.xyl.life.entity.movie.Movie;

import java.util.ArrayList;
import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context mContext;
    private List<Movie> mList=new ArrayList<>();
    private OnItemClickListener mListener;
    public MovieAdapter(Context context, List<Movie> list){
        this.mContext=context;
        this.mList=list;
    }
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_movie,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        final Movie movie=mList.get(position);
        holder.mName.setText(movie.getTitle());
        holder.mInfo.setText(GetInfo(movie.getCasts()));
        holder.mTime.setText(GetTime(movie.getYear(),movie.getGenres()));
        float v=movie.getRating().getAverage();
        Log.e("getRating", String.valueOf(v));
        holder.mRating.setRating(v);
        holder.mAverage.setText(String.valueOf(v));
       Glide.with(mContext).load(movie.getImages().getSmall()).into(holder.mCover);
       holder.mParentView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mListener.onClick(v,position,movie);
           }
       });

    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener=listener;
    }

    private static String GetInfo(Casts[] casts){
        StringBuilder builder=new StringBuilder();
        for (Casts c: casts) {
            builder.append(c.getName()).append(" ");
        }
        return builder.toString();
    }

    private static String GetTime(String time,String[] country){
        return time+" / "+country[0];
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public ImageView mCover;
        public TextView mName;
        public TextView mInfo;
        public TextView mTime;
        public AppCompatRatingBar mRating;
        public TextView mAverage;
        public View mParentView;
        public MovieViewHolder(View view){
            super(view);
            mParentView=view;
            mCover=view.findViewById(R.id.iv_movie_cover);
            mName=view.findViewById(R.id.tv_movie_name);
            mInfo=view.findViewById(R.id.tv_movie_info);
            mTime=view.findViewById(R.id.tv_movie_time);
            mRating=view.findViewById(R.id.rb_movie_rating);
            mRating.setNumStars(10);
            mAverage=view.findViewById(R.id.tv_movie_rating);


        }
    }

    public interface OnItemClickListener{
        void onClick(View view, int position, Movie movie);
    }
}
