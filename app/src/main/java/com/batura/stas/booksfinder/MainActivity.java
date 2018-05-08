package com.batura.stas.booksfinder;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searchButton = (Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText keyWord = (EditText) findViewById(R.id.editText);
                if (keyWord.getText().length() != 0) {
                    String keyWordString = keyWord.getText().toString();

                    Intent intent = new Intent(MainActivity.this, BooksListActivity.class);
                    intent.putExtra("keyWord",keyWordString );
                    startActivity(intent);
                }
                else {
                    Toast toast01 = Toast.makeText(getApplicationContext(),"No search respond",Toast.LENGTH_SHORT);
                    toast01.show();
                }
            }
        });


    }

}
