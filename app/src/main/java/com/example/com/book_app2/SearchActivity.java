package com.example.com.book_app2;

import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A Search Activty class
 * It displays the search results and you can search with in it.
 * It is a "SINGLE TOP" class
 */

public class SearchActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<List<Book>>,SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = "SearchActivity";

    //The base url for querying the data
    private  final String BASE ="https://www.googleapis.com/books/v1/volumes?";

    private final String TEST =
            "https://www.googleapis.com/books/v1/volumes?q=flowers&maxResults=31&key=AIzaSyAQ61-SYl2_MpLfj6ffr69t_nUcLeoFSEA";
    private static final int BOOK_LOADER_ID = 1;
    private static final int BOOK_LOADER_ID_2=2;
    private BookAdapter bookAdapter;
    private BookAdapter2 bookAdapter2;
    private GridView gridView;
    private ViewPager viewPager;
    private ArrayList<Book> bookArrayList;
    private LoaderManager loaderManager;
    private String see ="https://www.googleapis.com/books/v1/volumes?q=hee";
    private  ListView listView;
    private boolean reste=false;
    private  View loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loaderManager = getLoaderManager();

        //inti listview
        listView = (ListView) findViewById(R.id.listview);
        bookAdapter2 = new BookAdapter2(getApplicationContext(),new ArrayList<Book>());
        listView.setAdapter(bookAdapter2);

        // inti loading indicator
        loadingIndicator = findViewById(R.id.loading_indicator);
        //setting the visibility to gone, not needed now
        loadingIndicator.setVisibility(View.GONE);

        //inti shared preference, for when the user make changes in the settings
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(this);// setting a listener for when changes happens


        handleIntent(getIntent());


        /**
         *  Whan the user click on a book it will take them to a new activity {@link BookDetail}
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Book book = bookAdapter2.getItem(position);

                Intent intent = new Intent(parent.getContext(),BookDetail.class);
                intent.putExtra("BookName",book.getBookname());
                intent.putExtra("BookAuthor",book.getBooKauthor());
                intent.putExtra("BooKDes",book.getBookdiscription());
                intent.putExtra("ImageURL",book.getBookpictureUrlHD());
                intent.putExtra("BookRating",book.getBookrating());

                startActivity(intent);


            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);


            handleuri(query);

        }
    }

    /**
     * Gets the query  from the "handleIntent" method
     * and bulids upon it for requesting the query that from the web
     * @param uri
     */
    private void handleuri (String uri){
        Uri baseuri = Uri.parse(BASE);
        Uri.Builder uribulder = baseuri.buildUpon();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
       String syncConnPref = sharedPref.getString("Max_Search_Result","");

       //api key "PLEASE DON'T USE. USE YOUR OWN"
       String key ="AIzaSyAQ61-SYl2_MpLfj6ffr69t_nUcLeoFSEA";

        uribulder.appendQueryParameter("q",uri);
        uribulder.appendQueryParameter("maxResults",syncConnPref);
        uribulder.appendQueryParameter("key",key);
        Log.d(TAG, "handleuri: "+uribulder.toString());
        see=uribulder.toString();
        getReset(reste);


        loaderManager.initLoader(BOOK_LOADER_ID_2,null,this);






    }

    @NonNull
    @Override
    public Loader<List<Book>> onCreateLoader(int id, @Nullable Bundle args) {

        Log.d(TAG, "onCreateLoader: see:"+see);
        loadingIndicator.setVisibility(View.VISIBLE);

        return new BookLoader(this,see);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Book>> loader, List<Book> data) {

        //adding all the books to the adapter
        bookAdapter2.addAll(data);
        reste=true;

        //setting the loading indecator to gone because it is done getting the data
        loadingIndicator.setVisibility(View.GONE);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Book>> loader) {

        bookAdapter2.clear();


    }

    /**
     * To reset the loader when the searches again
     * @param what
     */
    private void getReset (boolean what){
        if(what){
            loaderManager.destroyLoader(BOOK_LOADER_ID_2);
            bookAdapter2.clear();

        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("Max_Search_Result")){
           // bookAdapter2.clear();
            loaderManager.restartLoader(BOOK_LOADER_ID_2,null,this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchmenu).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default

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
