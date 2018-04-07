package com.example.inikolovski.mvpsampleproject.features.books;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.inikolovski.mvpsampleproject.R;
import com.example.inikolovski.mvpsampleproject.data.Book;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private List<Book> list = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BookAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressWarnings("WeakerAccess")
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_book_title)
        TextView txtBookTitle;
        @BindView(R.id.txt_book_author)
        TextView txtBookAuthor;
        @BindView(R.id.txt_book_genre)
        TextView txtBookGenre;
        @BindView(R.id.layout)
        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        void bind(int position) {
            final Book book = list.get(position);
            txtBookTitle.setText(String.format("Title: %s", book.getTitle()));
            txtBookAuthor.setText(String.format("Author: %s", book.getAuthorName()));
            txtBookGenre.setText(String.format("Genre: %s", book.getGenre()));

        }
    }

    void updateList(List<Book> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    List<Book> getList() {
        return this.list;
    }

}
