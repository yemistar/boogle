package com.example.com.book_app2;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Helper methods related to requesting and receiving book data from GoogleBooks API.
 */
public class QuareBookapi {
    private static final String TAG = "QueryBookapi";


    /**
     *
     * @param url
     * @return list of books {@link Book}
     */
    public  static ArrayList<Book> josonobj(String url){
        // Create URL objec
        URL quarysite = createUrl(url);

        //the string where the jason respond will the stored
        String jasonresponds="";

        // Perform HTTP request to the URL and receive a JSON response back
        try {
             jasonresponds = makeHttpRequest(quarysite);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Book> bookList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject( jasonresponds);
            //getting the items array
            JSONArray items = jsonObject.getJSONArray("items");

            //getting the info from the JASON array to be stored in the book obj
            for (int i=0; i<items.length(); i++){


                JSONObject volumeInfo = items.getJSONObject(i).getJSONObject("volumeInfo");

                String bookname = volumeInfo.getString("title");

                String bookauthor="";

                if(volumeInfo.has("authors")){
                    bookauthor = volumeInfo.getJSONArray("authors").getString(0);
                }
                else {
                    bookauthor= "Onknow";
                }

                String bookdes;
                String rating;

                if(volumeInfo.has("description")){

                    bookdes =  volumeInfo.get("description").toString();
                }
                else{
                    bookdes = "No descripetion";
                }

                if(volumeInfo.has("averageRating")){
                    rating = volumeInfo.getString("averageRating");
                }
                else{
                    rating ="not avalable";
                }

                //getting the image url
                JSONObject image = (JSONObject) volumeInfo.get("imageLinks");

                String imageurl = image.getString("smallThumbnail");
                String imageuri2 = image.getString("thumbnail");

                //adding the info about the book to the book obj
                bookList.add(new Book(bookname,bookauthor,rating,bookdes,imageurl,imageuri2));
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


        return bookList;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
            }
        } catch (IOException e) {
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }
}
