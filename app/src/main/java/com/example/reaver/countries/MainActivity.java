package com.example.reaver.countries;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends Activity {
    private ArrayList<String> countries = new ArrayList<String>();
    private ArrayList<String> squares = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayAdapter<String> adapter;
        ListView lvMain;
        String name;
        String square;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            InputStream is = getAssets().open("namesandsquares.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ( (name = br.readLine()) != null && (square = br.readLine()) != null ) {
                countries.add(name);
                squares.add(square);
            }
        } catch (IOException e) {
            Log.d("MY_LOG", "File \"namesandsquares\" not found");
        }

        lvMain = (ListView) findViewById(R.id.listViewMain);
        adapter = new ArrayAdapter<String>(this, R.layout.item, countries);
        lvMain.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
