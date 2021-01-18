package com.example.com.book_app2;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

/**
 *
 */

public class MainActivity extends AppCompatActivity  {

    ImageView imageView;
    Toolbar myToolbar;
    TextView boogleTv;
    ImageView settingsV;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        boogleTv= findViewById(R.id.tooltext);

        settingsV= findViewById(R.id.settings);

        imageView = findViewById(R.id.homeImage);

        myToolbar= (Toolbar) findViewById(R.id.toolbar_frame);
        setSupportActionBar(myToolbar);


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) findViewById(R.id.search_bar);
        searchView.setIconifiedByDefault(true);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchViewOnclickFunctions();



        displayImage();

    }

    /**
     * This is to hide and display the @boogleTv and @settingsV
     */
    private void searchViewOnclickFunctions(){

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boogleTv.setVisibility(View.INVISIBLE);
                settingsV.setVisibility(View.INVISIBLE);

            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                boogleTv.setVisibility(View.VISIBLE);
                settingsV.setVisibility(View.VISIBLE);
                return false;
            }
        });

        boogleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayImage();
            }
        });

        settingsV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(getBaseContext(), SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });

    }

    /**
     * Display a random image of ISU campus
     * with Glide
     */
    private void displayImage(){
        boolean isImageGood;
        do{

            try{
                String imageUrl =randomizeUrl();
                //using glide to load the image
                GlideApp.with(getApplicationContext())
                        .load(imageUrl)
                        .fitCenter()
                        .centerCrop()
                        .into(imageView);
                isImageGood=true;

            } catch ( Exception e){
                isImageGood=false;
            }
        }while (!isImageGood);

    }

    /**
     * creates a random image url
     *
     * @return an image url
     */
    private String randomizeUrl(){
        int max = 2511;
        int min = 2000;
        int range = max - min + 1;
        int rand = (int)(Math.random() * range) + min;

        String imageNumber=String.valueOf(rand);

        String imageUrl ="https://photostream.iastate.edu/public/002/"+imageNumber+"/"+imageNumber+"-medium.jpg";

        return imageUrl;
    }



    
}
