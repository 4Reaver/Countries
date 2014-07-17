package com.example.reaver.countries;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends Activity implements View.OnClickListener {
    private ArrayList<String> countries = new ArrayList<String>();
    private ArrayList<String> squares = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayAdapter<String> adapter;
        Button add;
        Button add2;
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

        add = (Button) findViewById(R.id.addButton);
        add2 = (Button) findViewById(R.id.OKbutton);
        add.setOnClickListener(this);
        add2.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        EditText editText = (EditText)findViewById(R.id.editText);
        switch (view.getId()) {
            case R.id.addButton:
                Toast.makeText(this, "Добавить нажата", Toast.LENGTH_SHORT).show();
                view.setVisibility(View.INVISIBLE);
                findViewById(R.id.ll_enter).setVisibility(View.VISIBLE);
                editText.requestFocus();
                break;
            case R.id.OKbutton:
                String country = editText.getText().toString();

                editText.setText("");
                Toast.makeText(this, "OK нажата и добавлена страна: ".concat(country), Toast.LENGTH_SHORT).show();
                findViewById(R.id.ll_enter).setVisibility(View.INVISIBLE);
                findViewById(R.id.addButton).setVisibility(View.VISIBLE);
                break;
        }
    }
}
