package com.example.com.book_app2;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book>{
    private int length;

    public BookAdapter(@NonNull Context context, @NonNull List<Book> objects) {
        super(context, 0, objects);

    }



    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Book book = getItem(position);
        if(convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.book_layout,parent,false);
        }

        //inti varables
        ImageView imageView = convertView.findViewById(R.id.bookimage);
        TextView bookname = convertView.findViewById(R.id.bookname);
        TextView bookautor = convertView.findViewById(R.id.bookauthor);
        TextView bookrating = convertView.findViewById(R.id.bookrating);

        //setting the book contents
        bookname.setText(book.getBookname());
        bookautor.setText(book.getBooKauthor());
        bookrating.setText(book.getBookrating());

        // using glide to load image
        GlideApp.with(convertView)
                .load(book.getBookpictureUrl())
                .into(imageView);



        return convertView;


    }

}
