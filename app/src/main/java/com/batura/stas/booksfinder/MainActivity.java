package com.batura.stas.booksfinder;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner languageSpinner =(Spinner)findViewById(R.id.languageSpinner);
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

        final Spinner findInSpinner =(Spinner)findViewById(R.id.findInSpinner);
        ArrayAdapter<?> findAdapter =
                ArrayAdapter.createFromResource(this, R.array.findIn, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        findInSpinner.setAdapter(findAdapter);


        Button searchButton = (Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int langSelectedInt    = languageSpinner.getSelectedItemPosition();
                final int findInSelectedInt    = findInSpinner.getSelectedItemPosition();
                EditText keyWord = (EditText) findViewById(R.id.editText);
                if (keyWord.getText().length() != 0) {
                    String keyWordString = keyWord.getText().toString();
                    Intent intent = new Intent(MainActivity.this, BooksListActivity.class);
                    intent.putExtra("keyWord",keyWordString );
                    String[] languagesIdArray = getResources().getStringArray(R.array.languagesId);
                    intent.putExtra("language",languagesIdArray[langSelectedInt]);
                    String[] findInIdArray = getResources().getStringArray(R.array.findInId);
                    intent.putExtra("findIn",findInIdArray[findInSelectedInt]);
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
