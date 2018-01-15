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
import com.xyl.life.entities.book.BookItem;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<BookItem> mBookItemList;
    private Context mContext;


    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mBookIcon;
        TextView mBookName;

        public ViewHolder(View view) {
            super(view);
            mBookIcon = (ImageView) view.findViewById(R.id.iv_bookIcon);
            mBookName = (TextView) view.findViewById(R.id.tv_bookName);
        }
    }

    public RecyclerViewAdapter(Context context,List<BookItem> list){
        this.mContext=context;
        this.mBookItemList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_fragment_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BookItem item = mBookItemList.get(position);
        holder.mBookName.setText(item.getName());
        Glide.with(mContext).load(item.getIcon()).into(holder.mBookIcon);
    }





    @Override
    public int getItemCount() {
        return mBookItemList.size();
    }

}
