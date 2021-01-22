package com.example.com.book_app2;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 *
 */

public class MainActivity extends AppCompatActivity  {

    private static final String TAG = "MainActivity";
    ImageView imageView;
    Toolbar myToolbar;
    TextView boogleTv;
    TextView greetingTv;
    TextView yearTv;
    TextView timeTv;
    ImageView settingsV;
    SearchView searchView;
    ArrayList<Book> bookArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        boogleTv= findViewById(R.id.tooltext);

        greetingTv= findViewById(R.id.day);

        yearTv= findViewById(R.id.year);

        timeTv=findViewById(R.id.data_time);

        settingsV= findViewById(R.id.settings);

        imageView = findViewById(R.id.homeImage);

        myToolbar= (Toolbar) findViewById(R.id.toolbar_frame);
        setSupportActionBar(myToolbar);


        bookArrayList = new ArrayList<>();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) findViewById(R.id.search_bar);
        searchView.setIconifiedByDefault(true);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchViewOnclickFunctions();
        displayImage();
        setDate();
        getDB();

    }



    private void getDB(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("bookList", null);
        Type type = new TypeToken<ArrayList<Book>>() {}.getType();
        bookArrayList = gson.fromJson(json, type);
        if (bookArrayList == null) {
            bookArrayList = new ArrayList<>();
            Log.i(TAG, "getDB: ArraySize :"+bookArrayList.size());
        }else {
            Log.i(TAG, "getDB: ArraySize :"+bookArrayList.size());
        }
    }

    /**
     *  Set the date and time
     */
    private  void  setDate(){
        String time = new SimpleDateFormat("KK:mm aa", Locale.getDefault()).format(new Date());
        String month = new SimpleDateFormat("MMM", Locale.getDefault()).format(new Date());
        String year = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
        String day = new SimpleDateFormat("EEEE", Locale.getDefault()).format(new Date());

        greetingTv.setText("Hello, It's "+day);
        yearTv.setText(month+", "+year);
        timeTv.setText(time);
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
