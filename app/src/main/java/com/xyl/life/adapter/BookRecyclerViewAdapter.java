package com.xyl.life.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xyl.life.R;
import com.xyl.life.activity.BookDetailActivity;
import com.xyl.life.entity.book.Book;

import java.util.List;


/**
 * 首页书籍展示适配器
 */

public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookRecyclerViewAdapter.ViewHolder> {

    private List<Book> mBookItemList;
    private Context mContext;


    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView mBookIcon;
        TextView mBookName;
        View mView;

        public ViewHolder(View view) {
            super(view);
            this.mView=view;
            mBookIcon = (ImageView) view.findViewById(R.id.iv_bookIcon);
            mBookName = (TextView) view.findViewById(R.id.tv_bookName);
        }
    }

    public BookRecyclerViewAdapter(Context context, List<Book> list) {
        this.mContext = context;
        this.mBookItemList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Book item = mBookItemList.get(position);
        holder.mBookName.setText(item.getTitle());
        Glide.with(mContext).load(item.getImages().getSmall()).into(holder.mBookIcon);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position =holder.getAdapterPosition();
                Book bookItem =mBookItemList.get(position);
                Intent intent =new Intent(mContext, BookDetailActivity.class);
                intent.putExtra("book",bookItem);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBookItemList.size();
    }

}
