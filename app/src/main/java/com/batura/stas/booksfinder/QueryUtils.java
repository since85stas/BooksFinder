package com.batura.stas.booksfinder;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
import java.util.List;

/**
 * Created by HOME on 26.04.2018.
 */

public final class QueryUtils implements LoadImageTask.Listener{
    private QueryUtils () {
    }
    private ImageView mImageView;

    private Bitmap mBitmap;

    public static final String LOG_TAG = QueryUtils.class.getName();

    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    public static String makeHttpRequest(URL url) throws IOException {
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
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    public static String readFromStream(InputStream inputStream) throws IOException {
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

    public static List<Book> fetchBooksData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        List<Book> currentList = extractDataFromJsonResponse(jsonResponse);

        // Extract relevant fields from the JSON response and create an {@link Event} object
        return(currentList);
    }

    public static ArrayList<Book>  extractDataFromJsonResponse (String jsonResponse)   {

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.

        ArrayList<Book> books = new ArrayList<>();

        try {

            // Create a JSONObject from the SAMPLE_JSON_RESPONSE string
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);

            // Extract the JSONArray associated with the key called "items",
            // which represents a list of features (or earthquakes).
            JSONArray bookArray = baseJsonResponse.getJSONArray("items");

            // For each earthquake in the bookArray, create an {@link Earthquake} object
            for (int i = 0; i < bookArray.length(); i++) {

                // Get a single earthquake at position i within the list of earthquakes
                JSONObject currentBook = bookArray.getJSONObject(i);

                // For a given earthquake, extract the JSONObject associated with the
                // key called "volumeInfo", which represents a list of all properties
                // for that earthquake.
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");

                String author = volumeInfo.getString("authors");

                String title = volumeInfo.getString("title");

                String description;
                if (volumeInfo.has("description")) {
                    description = volumeInfo.optString("description");
                }
                else {
                    description = "No decription";
                }

                String imagePreview;
                Image image;
                if (volumeInfo.has("imageLinks")) {
                    JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                    imagePreview = imageLinks.getString("smallThumbnail");
                    //image = Picasso.get().load(imagePreview);
                }
                else {
                    imagePreview = "No cover";
                }

                JSONObject salesInfo = currentBook.getJSONObject("saleInfo");
                String price;
                String currency;
                if (salesInfo.has("listPrice")) {
                    JSONObject listPrice = salesInfo.getJSONObject("listPrice");
                    price = listPrice.getString("amount");
                    currency = listPrice.getString("currencyCode");
                }
                else {
                    price = "N/A";
                    currency = "";
                }

                books.add(new Book(imagePreview,author,title,description));

               }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the books JSON results", e);
        }
        return (books);

    }

    @Override
    public void onImageLoaded(Bitmap bitmap) {

        mBitmap = bitmap;
        //mImageView.setImageBitmap(bitmap);
    }

    @Override
    public void onError() {
        //Toast.makeText(this, "Error Loading Image !", Toast.LENGTH_SHORT).show();
    }
}
