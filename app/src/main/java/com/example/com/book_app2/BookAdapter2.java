package com.example.com.book_app2;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * An {@link BookAdapter2} knows how to create a list item layout
 * in the data source (a list of {@link Book} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */

public class BookAdapter2 extends ArrayAdapter<Book> {

    public static String TAG ="BookAdapter2";

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

            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.bookimage);
            viewHolder.star1=convertView.findViewById(R.id.star1);
            viewHolder.star2=convertView.findViewById(R.id.star2);
            viewHolder.star3=convertView.findViewById(R.id.star3);
            viewHolder.star4=convertView.findViewById(R.id.star4);
            viewHolder.star5=convertView.findViewById(R.id.star5);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Displaying all the info from the book object
        viewHolder.bookname.setText(book.getBookname());
        viewHolder.bookauthor.setText(book.getBooKauthor());

        Double rate;
        if(TextUtils.equals(book.getBookrating(),"not avalable")){
            rate = null;
        }else {

            rate = Double.parseDouble(book.getBookrating());
        }

        setStars(rate,viewHolder);
        //using glide to load the image
        GlideApp.with(convertView)
                .load(book.getBookpictureUrl())
                .into(viewHolder.imageView);

        return convertView;
    }


    /**
     * Sets the stars for the book ratings
     * @param rate the book rating
     * @param viewHolder the view {@Link ViewHolder}
     */
    private void setStars(Double rate,ViewHolder viewHolder){


        if(rate==null){
            viewHolder.star1.setBackgroundResource(R.drawable.star_border);
            viewHolder.star2.setBackgroundResource(R.drawable.star_border);
            viewHolder.star3.setBackgroundResource(R.drawable.star_border);
            viewHolder.star4.setBackgroundResource(R.drawable.star_border);
            viewHolder.star5.setBackgroundResource(R.drawable.star_border);
            return;
        }
        Double see =Math.floor(rate);

        Double a= rate%see;
        boolean half=false;
        if(a>0){
            half=true;
        }


        if(see==1.0){

            viewHolder.star1.setBackgroundResource(R.drawable.star_icon);
            if(half){
                viewHolder.star2.setBackgroundResource(R.drawable.star_half);
            }

        }else if(see==2.0){

            viewHolder.star1.setBackgroundResource(R.drawable.star_icon);
            viewHolder.star2.setBackgroundResource(R.drawable.star_icon);
            if(half){
                viewHolder.star3.setBackgroundResource(R.drawable.star_half);
            }

        }else if(see==3.0){

            viewHolder.star1.setBackgroundResource(R.drawable.star_icon);
            viewHolder.star2.setBackgroundResource(R.drawable.star_icon);
            viewHolder.star3.setBackgroundResource(R.drawable.star_icon);
            if(half){
                viewHolder.star4.setBackgroundResource(R.drawable.star_half);
            }

        }else if(see==4.0){
            viewHolder.star1.setBackgroundResource(R.drawable.star_icon);
            viewHolder.star2.setBackgroundResource(R.drawable.star_icon);
            viewHolder.star3.setBackgroundResource(R.drawable.star_icon);
            viewHolder.star4.setBackgroundResource(R.drawable.star_icon);

            if(half){
                viewHolder.star5.setBackgroundResource(R.drawable.star_half);
            }

        }else if(see==5.0){
            viewHolder.star1.setBackgroundResource(R.drawable.star_icon);
            viewHolder.star2.setBackgroundResource(R.drawable.star_icon);
            viewHolder.star3.setBackgroundResource(R.drawable.star_icon);
            viewHolder.star4.setBackgroundResource(R.drawable.star_icon);
            viewHolder.star5.setBackgroundResource(R.drawable.star_icon);

        }
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
        ImageView star1;
        ImageView star2;
        ImageView star3;
        ImageView star4;
        ImageView star5;


    }
}
