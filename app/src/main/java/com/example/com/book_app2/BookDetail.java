package com.example.com.book_app2;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 *  A class for more info about a book
 */

public class BookDetail extends AppCompatActivity {

    /**
     * A object that hold all the stars {@link VeiwHolder}
      */
   private VeiwHolder veiwHolder;
    /**
     * The Star rating of the book
     */
   private Double rate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.book_layout2);

        /**
         * Getting the info stired in the intent from the {@link SearchActivity}
         */
        Intent intent =getIntent();
        String book_name =intent.getStringExtra("BookName");
        String imageurl = intent.getStringExtra("ImageURL");
        String des =intent.getStringExtra("BooKDes");
        String bookrating = intent.getStringExtra("BookRating");

        // Checking if the book has a rating
        // if it does it goes into the method "getStar"
        if(TextUtils.equals(bookrating,"not avalable")){
            rate = null;
        }else {
            rate = Double.parseDouble(bookrating);
        }

        //inti the book image
        ImageView imageView = findViewById(R.id.backdrop);
        //inti the description textview
        TextView textView = findViewById(R.id.des);
        //Putting the book description in the textview
        textView.setText(des);

        //Loading the book image with glide
        GlideApp.with(getApplicationContext())
                .load(imageurl)
                .centerInside()
                .override(imageView.getWidth(),imageView.getHeight())
                .into(imageView);

        //inti toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //inti collapsingtoolbar
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.cardview_dark_background));

        //setting book name to show in the collapsingtoolbar
        collapsingToolbarLayout.setTitle(book_name);


        //Todo use gulide to load the star to use less merory.

        //inti viewhold for the stars
        veiwHolder = new VeiwHolder();
        veiwHolder.star1 =(TextView) findViewById(R.id.star1);
        veiwHolder.star2 =(TextView) findViewById(R.id.star2);
        veiwHolder.star3 =(TextView) findViewById(R.id.star3);
        veiwHolder.star4 = (TextView) findViewById(R.id.star4);
        veiwHolder.star5 = (TextView) findViewById(R.id.star5);

        //passing args into getstar method to show the stars
        getStar(rate);
    }



    /**
     * Get the book rating and converts it to a visable star
     * on the UI
     * @param rate
     */
    private void getStar( Double rate){

        if(rate ==null){
            veiwHolder.star1.setVisibility(View.INVISIBLE);
            veiwHolder.star2.setVisibility(View.INVISIBLE);
            veiwHolder.star3.setVisibility(View.INVISIBLE);
            veiwHolder.star4.setVisibility(View.INVISIBLE);
            veiwHolder.star5.setVisibility(View.INVISIBLE);
            return;
        }
        Double see =Math.floor(rate);
        Double a= rate%see;
        boolean half=false;
        if(a>0){
            half=true;
        }

        if(see==1.0){
            veiwHolder.star1.setBackgroundResource(R.mipmap.baseline_star_black_24);
            if(half){
                veiwHolder.star2.setBackgroundResource(R.mipmap.baseline_star_half_black_18);
            }

        }else if(see==2.0){
            veiwHolder.star1.setBackgroundResource(R.mipmap.baseline_star_black_24);
            veiwHolder.star2.setBackgroundResource(R.mipmap.baseline_star_black_24);
            if(half){
                veiwHolder.star3.setBackgroundResource(R.mipmap.baseline_star_half_black_18);
            }

        }else if(see==3.0){
            veiwHolder.star1.setBackgroundResource(R.mipmap.baseline_star_black_24);
            veiwHolder.star2.setBackgroundResource(R.mipmap.baseline_star_black_24);
            veiwHolder.star3.setBackgroundResource(R.mipmap.baseline_star_black_24);
            if(half){
                veiwHolder.star4.setBackgroundResource(R.mipmap.baseline_star_half_black_18);
            }

        }else if(see==4.0){
            veiwHolder.star1.setBackgroundResource(R.mipmap.baseline_star_black_24);
            veiwHolder.star2.setBackgroundResource(R.mipmap.baseline_star_black_24);
            veiwHolder.star3.setBackgroundResource(R.mipmap.baseline_star_black_24);
            veiwHolder.star4.setBackgroundResource(R.mipmap.baseline_star_black_24);

            if(half){
                veiwHolder.star5.setBackgroundResource(R.mipmap.baseline_star_half_black_18);
            }

        }else if(see==5.0){
            veiwHolder.star1.setBackgroundResource(R.mipmap.baseline_star_black_24);
            veiwHolder.star2.setBackgroundResource(R.mipmap.baseline_star_black_24);
            veiwHolder.star3.setBackgroundResource(R.mipmap.baseline_star_black_24);
            veiwHolder.star4.setBackgroundResource(R.mipmap.baseline_star_black_24);
            veiwHolder.star5.setBackgroundResource(R.mipmap.baseline_star_black_24);

        }

    }


    /**
     * Holds the stars that will be shown
     */
    static  class VeiwHolder{
        TextView star1;
        TextView star2;
        TextView star3;
        TextView star4;
        TextView star5;

    }


}
