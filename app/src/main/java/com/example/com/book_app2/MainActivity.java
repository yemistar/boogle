package com.example.com.book_app2;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;

/**
 *
 */

public class MainActivity extends AppCompatActivity  {

    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);


        imageView = findViewById(R.id.homeImage);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) findViewById(R.id.mainsearch2);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        displayImage();

    }


    /**
     * Display a random image of ISU campus
     * with Glide
     */
    private void displayImage(){
        boolean isImageGood=true;
        do{

            String imageUrl =randomizeUrl();

            try{
                //using glide to load the image
                GlideApp.with(getApplicationContext())
                        .load(imageUrl)
                        .fitCenter()
                        .centerCrop()
                        .into(imageView);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    
}
