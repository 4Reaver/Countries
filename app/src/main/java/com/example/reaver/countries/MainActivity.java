package com.example.reaver.countries;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.Locale;
import java.util.Scanner;


public class MainActivity extends Activity implements View.OnClickListener, FragmentAdd.OnOkButtonClickListener,
        AdapterView.OnItemClickListener, TextWatcher {
    private static final int DELETE_ID = 1;

    private ArrayAdapter<Country> adapter;
    //private ArrayList<String> countries = new ArrayList<String>();
    private ArrayList<Country> countries = new ArrayList<Country>();
    //private ArrayList<String> squares = new ArrayList<String>();
    private DialogFragment fragmentAdd;
    private EditText filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button addButton;
        ListView lvMain;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            InputStream is = getAssets().open("names_s_a.txt");
            Scanner scanner = new Scanner(is).useLocale(Locale.US);
            String name;
            int area;
            int population;


            while ( scanner.hasNextLine() ) {
                name = scanner.nextLine();
                area = scanner.nextInt();
                population = scanner.nextInt();
                countries.add(new Country(name, area, population));
                scanner.nextLine();
            }
        } catch (IOException e) {
            Log.d("MY_LOG", "File \"namesandsquares\" not found");
        }

        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(this);
        filter = (EditText) findViewById(R.id.filter);
        filter.addTextChangedListener(this);
        lvMain = (ListView) findViewById(R.id.listViewMain);
        adapter = new ArrayAdapter<Country>(this, R.layout.item, countries);
        lvMain.setAdapter(adapter);

        //lvMain.setOnItemLongClickListener(this);
        lvMain.setOnItemClickListener(this);
        registerForContextMenu(lvMain);
        fragmentAdd = new FragmentAdd();
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
        switch (view.getId()) {
            case R.id.addButton:
                fragmentAdd.show(getFragmentManager(), "add_TAG");
                break;
        }
    }

    @Override
    public void onOkButtonClicked(Country newCountry) {
        Toast.makeText(this, "Добавлена страна: ".concat(newCountry.toString()), Toast.LENGTH_SHORT).show();
        countries.add(newCountry);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, "Удалить");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if ( item.getItemId() == DELETE_ID ) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

            countries.remove(info.position);
            adapter.notifyDataSetChanged();
        }
        return super.onContextItemSelected(item);
    }

    /*@Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        countries.remove(i);
        adapter.notifyDataSetChanged();
        return true;
    }*/

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("Country", countries.get(i));
        startActivity(intent);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        adapter.getFilter().filter(filter.getText().toString());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
