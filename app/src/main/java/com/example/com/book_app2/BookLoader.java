package com.example.com.book_app2;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 *Loads a list of books by using an AsyncTask to perform the
 * network request to the given URL.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    //A Tag for debugging
    private static final String TAG = "BookLoader";
    // The given url to load the books from
    private String url;

    /**
     *
     * @param context of the app
     * @param url of the search to get the books from
     */
    public BookLoader(Context context, String url) {
        super(context);
        this.url=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    /**
     * This is on a background thread.
     */
    public List<Book> loadInBackground() {

        if(url==null){
            return null;
        }
        Log.d(TAG, "loadInBackground: url"+url);
        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Book> bookList = QuareBookapi.josonobj(url);

        return bookList;
    }
}
