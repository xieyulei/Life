package com.xyl.life.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xyl.life.R;
import com.xyl.life.entity.movie.Casts;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {
    private Context mContext;
    private List<Casts> mList;
    public CastAdapter(Context context, List<Casts> list){
        this.mContext=context;
        this.mList=list;
    }

    @Override
    public CastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_movie_cast,parent,false);

        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CastViewHolder holder, int position) {
        Casts casts=mList.get(position);
        holder.mTextView.setText(casts.getName());
        Glide.with(mContext).load(casts.getAvatars().getSmall()).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class CastViewHolder extends RecyclerView.ViewHolder{

        protected ImageView mImageView;
        protected TextView mTextView;
        public CastViewHolder(View view){
            super(view);
            mImageView=view.findViewById(R.id.iv_movie_cast_avatars);
            mTextView=view.findViewById(R.id.tv_movie_cast_name);
        }
    }

}
