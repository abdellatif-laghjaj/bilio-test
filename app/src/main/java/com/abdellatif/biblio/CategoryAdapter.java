package com.abdellatif.biblio;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private List<Category> categories;
    private Context context;

    public CategoryAdapter(List<Category> data, Context context) {
        this.categories = data;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Category item = categories.get(position);

        holder.categoryName.setText(item.getName());

        //click listener on view image
        holder.view_iv.setOnClickListener(v -> {
            Intent intent = new Intent(context, BooksActivity.class);
            intent.putExtra("category_id", item.getId());
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryName;
        private ImageView view_iv;

        public MyViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name);
            view_iv = itemView.findViewById(R.id.view_iv);
        }
    }
}