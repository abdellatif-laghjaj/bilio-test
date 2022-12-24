package com.abdellatif.biblio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {
    private List<Book> books;
    private Context context;

    public BookAdapter(List<Book> data, Context context) {
        this.books = data;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.book_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Book item = books.get(position);

        holder.bookName.setText(item.getTitle());
        holder.bookAuthor.setText(item.getAuthor());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView bookName, bookAuthor;

        public MyViewHolder(View itemView) {
            super(itemView);

            bookName = itemView.findViewById(R.id.book_name);
            bookAuthor = itemView.findViewById(R.id.book_author);
        }
    }
}