package com.batura.stas.booksfinder;

import android.app.Dialog;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HOME on 29.04.2018.
 */

public class BooksListActivity extends AppCompatActivity  {

    private BooksAdapter mAdapter;
    AlertDialog.Builder ad;

    private TextView mEmptyTextView;

    private static final String BOOKS_REQUEST_URL_1 =
            "https://www.googleapis.com/books/v1/volumes?q=";

    private static final String BOOKS_REQUEST_URL_2 = "&maxResults=40";
    private static final String BOOKS_LANG_REQUEST  = "&langRestrict=";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.books_activity);

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        mEmptyTextView = (TextView) findViewById(R.id.empty_state);

        if(networkInfo != null && networkInfo.isConnected() ) {
            String keyWordString = getIntent().getExtras().getString("keyWord");
            String langWordString = getIntent().getExtras().getString("language");
            String findWordString = getIntent().getExtras().getString("findIn");
            String sortedWordString = getIntent().getExtras().getString("sorted");

            String books_request_url = BOOKS_REQUEST_URL_1+findWordString + keyWordString;
            books_request_url = books_request_url + BOOKS_REQUEST_URL_2 + BOOKS_LANG_REQUEST+langWordString+"&"+sortedWordString;
            BooksAsyncClass booksAsyncClass = new BooksAsyncClass();
            booksAsyncClass.execute(books_request_url);
            ListView booksListView = (ListView) findViewById(R.id.list);
            mAdapter = new BooksAdapter(this, new ArrayList<Book>());
            booksListView.setAdapter(mAdapter);

            booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Book book = mAdapter.getItem(position);

                    ad = new AlertDialog.Builder(BooksListActivity.this);
                    ad.setTitle("Book description");
                    ad.setMessage(book.getDescription());

                    AlertDialog alert = ad.create();
                    alert.show();
                    //ad.create();
                }
            });
        }
        else {
            ListView booksListView = (ListView) findViewById(R.id.list);

            mEmptyTextView.setText("No internet connectivity");
            booksListView.setEmptyView(mEmptyTextView);
        }
    }


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
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading_bar);
            progressBar.setVisibility(View.GONE);
            mAdapter.clear();


            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
            else {
                mEmptyTextView.setText("No books");
            }
        }
    }
}
