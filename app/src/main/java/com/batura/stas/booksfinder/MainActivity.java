package com.batura.stas.booksfinder;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getName();

    private static final String BOOKS_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BooksAsyncClass booksAsyncClass = new BooksAsyncClass();
        booksAsyncClass.execute(BOOKS_REQUEST_URL);

    }


    private class BooksAsyncClass extends AsyncTask<String,Void,Void> {

        @Override
        protected Void doInBackground(String... urls) {

            if (urls.length <1 || urls[0]==null  ){

                return null ;
            }
            String urlString = urls[0];
            QueryUtils.fetchBooksData(urlString);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
