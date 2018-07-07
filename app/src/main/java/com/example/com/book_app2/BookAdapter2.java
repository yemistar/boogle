package com.example.com.book_app2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link BookAdapter2} knows how to create a list item layout for each earthquake
 * in the data source (a list of {@link Book} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */

public class BookAdapter2 extends ArrayAdapter<Book> {


    /**
     *
     * @param context of the app
     * @param objects is the list of Books, which is the data source of the adapter
     */
    public BookAdapter2(@NonNull Context context, @NonNull List<Book> objects) {
        super(context, 0, objects);

    }

    @NonNull
    @Override
    /**
     * Returns a list item view that displays information about a Book at the given position
     * in the list of earthquakes.
     */
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Find the book at the given position in the list of books
        Book book = getItem(position);

        // An objects that holds all the components that make up the book searchview, it is inti below
        ViewHolder viewHolder;
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.book_layout,parent,false);
            viewHolder.bookauthor = (TextView) convertView.findViewById(R.id.bookauthor);
            viewHolder.bookname = (TextView) convertView.findViewById(R.id.bookname);
            viewHolder.bookrating = (TextView) convertView.findViewById(R.id.bookrating);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.bookimage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Displaying all the info from the book object
        viewHolder.bookrating.setText(book.getBookrating());
        viewHolder.bookname.setText(book.getBookname());
        viewHolder.bookauthor.setText(book.getBooKauthor());

        //using glide to load the image
        GlideApp.with(convertView)
                .load(book.getBookpictureUrl())
                .into(viewHolder.imageView);

        return convertView;
    }


    /**
     * A class that holds all the components that makes up the each book search
     * This is made for a better scrolling experience
     */
    private static class ViewHolder{
        LinearLayout mainLinerLayout;
        LinearLayout bookinfoLinerLayout;

        ImageView imageView;
        TextView bookname;
        TextView bookauthor;
        TextView bookrating;


    }
}
