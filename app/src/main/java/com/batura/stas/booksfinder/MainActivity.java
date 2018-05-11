package com.batura.stas.booksfinder;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
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
                ArrayAdapter.createFromResource(this, R.array.languages, R.layout.spinner);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

        final Spinner findInSpinner =(Spinner)findViewById(R.id.findInSpinner);
        ArrayAdapter<?> findAdapter =
                ArrayAdapter.createFromResource(this, R.array.findIn,R.layout.spinner);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        findInSpinner.setAdapter(findAdapter);

        final Spinner sortSpinner =(Spinner)findViewById(R.id.sortedSpinner);
        ArrayAdapter<?> sortAdapter =
                ArrayAdapter.createFromResource(this, R.array.sorted, R.layout.spinner);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        sortSpinner.setAdapter(sortAdapter);


        Button searchButton = (Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int langSelectedInt    = languageSpinner.getSelectedItemPosition();
                final int findInSelectedInt    = findInSpinner.getSelectedItemPosition();
                final int sortedSelectedInt    = sortSpinner.getSelectedItemPosition();

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
                    String[] sortedArray = getResources().getStringArray(R.array.sortedId);
                    intent.putExtra("sorted",sortedArray[sortedSelectedInt]);
                    startActivity(intent);
                }
                else {
                    Toast toast01 = Toast.makeText(getApplicationContext(),"No search respond",Toast.LENGTH_SHORT);
                    toast01.show();
                }
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();

        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.menuAbout:
                Intent graphIntent = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(graphIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}
