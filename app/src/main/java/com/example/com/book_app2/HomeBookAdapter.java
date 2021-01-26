package com.example.com.book_app2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeBookAdapter extends RecyclerView.Adapter<HomeBookAdapter.ViewHolder> {

    private List<Book> bookList;

    Context context;
    public  HomeBookAdapter(List<Book> books){
        bookList=books;
    }
    @NonNull
    @Override
    public HomeBookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context =parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.home_book_layout, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeBookAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        final Book book = bookList.get(position);
        TextView textView=holder.textView;
        ImageView imageView=holder.imageView;
        textView.setText(book.getBookname());


        GlideApp.with(context)
                .load(book.getBookpictureUrl())
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,BookDetail.class);
                intent.putExtra("BookName",book.getBookname());
                intent.putExtra("BookAuthor",book.getBooKauthor());
                intent.putExtra("BooKDes",book.getBookdiscription());
                intent.putExtra("ImageURL",book.getBookpictureUrlHD());
                intent.putExtra("BookRating",book.getBookrating());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.homeBookImage);
            textView = itemView.findViewById(R.id.homeBookName);
        }
    }
}
