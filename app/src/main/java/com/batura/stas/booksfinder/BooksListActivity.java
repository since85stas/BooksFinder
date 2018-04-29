package com.batura.stas.booksfinder;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HOME on 29.04.2018.
 */

public class BooksListActivity extends AppCompatActivity {

    private BooksAdapter mAdapter;

    private static final String BOOKS_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=1";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.books_activity);
        BooksAsyncClass booksAsyncClass = new BooksAsyncClass();
        booksAsyncClass.execute(BOOKS_REQUEST_URL);
        ListView booksListView =(ListView)findViewById(R.id.list);
        mAdapter =  new BooksAdapter(this, new ArrayList<Book>());
        booksListView.setAdapter(mAdapter);
    };



    private class BooksAsyncClass extends AsyncTask<String,Void,List<Book>> {

        @Override
        protected List<Book> doInBackground(String... urls) {

            if (urls.length <1 || urls[0]==null  ){

                return null ;
            }
            String urlString = urls[0];
            List<Book> books = QueryUtils.fetchBooksData(urlString);
            return (books);
        }

        @Override
        protected void onPostExecute(List<Book> data) {
            mAdapter.clear();

            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }
}