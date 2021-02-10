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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A Search Activty class
 * It displays the search results and you can search with in it.
 * It is a "SINGLE TOP" class
 */

public class SearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>, SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = "SearchActivity";
    private static final int BOOK_LOADER_ID_2 = 2;
    //The base url for querying the data
    private final String BASE = "https://www.googleapis.com/books/v1/volumes?";
    Toolbar myToolbar;
    TextView boogleTv;
    ImageView settingsV;
    SearchView searchView;
    SharedPreferences preferences;
    private BookAdapter bookAdapter;
    private ArrayList<Book> bookArrayList;
    private LoaderManager loaderManager;
    private String see = "https://www.googleapis.com/books/v1/volumes?q=hee";
    private ListView listView;
    private boolean reset = false;
    private View loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity_layout);

        setUpCustomAppBar();
        searchViewOnclickFunctions();

        loaderManager = getLoaderManager();

        //inti listview
        listView = (ListView) findViewById(R.id.listview);
        bookAdapter = new BookAdapter(getApplicationContext(), new ArrayList<Book>());
        listView.setAdapter(bookAdapter);

        // inti loading indicator
        loadingIndicator = findViewById(R.id.loading_indicator);
        //setting the visibility to gone, not needed now
        loadingIndicator.setVisibility(View.GONE);

        //inti shared preference, for when the user make changes in the settings
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(this);// setting a listener for when changes happens


        bookArrayList = new ArrayList<>();
        handleIntent(getIntent());


        /**
         *  Whan the user click on a book it will take them to a new activity {@link BookDetail}
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Book book = bookAdapter.getItem(position);

                if (!bookArrayList.contains(book)) {
                    bookArrayList.add(book);
                    addClickedBook();
                }

                Intent intent = new Intent(parent.getContext(), BookDetail.class);
                intent.putExtra("BookName", book.getBookname());
                intent.putExtra("BookAuthor", book.getBooKauthor());
                intent.putExtra("BooKDes", book.getBookdiscription());
                intent.putExtra("ImageURL", book.getBookpictureUrlHD());
                intent.putExtra("BookRating", book.getBookrating());

                startActivity(intent);


            }
        });
    }


    /**
     * Adds the book that was clicked to the
     * sharedPreference DB
     */
    private void addClickedBook() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = sharedPreferences.getString("bookList", null);
        Type type = new TypeToken<ArrayList<Book>>() {
        }.getType();
        ArrayList<Book> bookArray = gson.fromJson(json, type);
        if (bookArray == null) {
            String json2 = gson.toJson(bookArrayList);
            editor.putString("bookList", json2);

        } else {
            bookArray.addAll(bookArrayList);
            String json2 = gson.toJson(bookArray);
            editor.putString("bookList", json2);
        }
        editor.apply();

    }

    /**
     * for setting up the appbar view
     */
    private void setUpCustomAppBar() {
        myToolbar = (Toolbar) findViewById(R.id.Toolbar);
        setSupportActionBar(myToolbar);

        boogleTv = findViewById(R.id.tooltext);

        settingsV = findViewById(R.id.settings);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) findViewById(R.id.search_bar);
        searchView.setIconifiedByDefault(true);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    }

    /**
     * This is to hide and display the @boogleTv and @settingsV
     * and to set navigation to home page and settings page
     */
    private void searchViewOnclickFunctions() {

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
                Intent homeIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(homeIntent);
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
     *
     * @param uri
     */
    private void handleuri(String uri) {
        Uri baseuri = Uri.parse(BASE);
        Uri.Builder uribulder = baseuri.buildUpon();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String syncConnPref = sharedPref.getString("Max_Search_Result", "10");

        //api key "PLEASE DON'T USE. USE YOUR OWN"
        String key = "AIzaSyAQ61-SYl2_MpLfj6ffr69t_nUcLeoFSEA";

        uribulder.appendQueryParameter("q", uri);
        uribulder.appendQueryParameter("maxResults", syncConnPref);
        uribulder.appendQueryParameter("key", key);
        see = uribulder.toString();
        getReset(reset);

        loaderManager.initLoader(BOOK_LOADER_ID_2, null, this);

    }

    @NonNull
    @Override
    public Loader<List<Book>> onCreateLoader(int id, @Nullable Bundle args) {
        loadingIndicator.setVisibility(View.VISIBLE);
        return new BookLoader(this, see);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Book>> loader, List<Book> data) {
        //adding all the books to the adapter
        bookAdapter.addAll(data);
        reset = true;
        //setting the loading indecator to gone because it is done getting the data
        loadingIndicator.setVisibility(View.GONE);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Book>> loader) {
        bookAdapter.clear();
    }

    /**
     * To reset the loader when the searches again
     *
     * @param what
     */
    private void getReset(boolean what) {
        if (what) {
            loaderManager.destroyLoader(BOOK_LOADER_ID_2);
            bookAdapter.clear();
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("Max_Search_Result")) {
            loaderManager.restartLoader(BOOK_LOADER_ID_2, null, this);
        }
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
