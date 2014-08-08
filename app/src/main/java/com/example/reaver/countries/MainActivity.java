package com.example.reaver.countries;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
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


public class MainActivity extends Activity implements View.OnClickListener, FragmentAdd.OnOkButtonClickListener
       , FragmentCountriesList.OnAddButtonClickListener {

    private static final int PEOPLE_REQUEST_CODE = 42;
    private static final int COLOR_REQUEST_CODE = 43;
    public static final String LOG_TAG = "My";

    private ArrayList<Country> countries;
    private CountryAdapter adapter;
    private DialogFragment fragmentAdd;
    private EditText filter;
    private DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentAdd = new FragmentAdd();
        findViewById(R.id.start_peopleActivity).setOnClickListener(this);
        findViewById(R.id.start_colorsActivity).setOnClickListener(this);

        Fragment fragment_countries_list = getFragmentManager().findFragmentById(R.id.fragment_countries_list);
        filter = (EditText) fragment_countries_list.getView().findViewById(R.id.filter);
        countries = ((FragmentCountriesList) fragment_countries_list).getCountries();
        adapter = ((FragmentCountriesList) fragment_countries_list).getAdapter();
        dataBase = ((FragmentCountriesList) fragment_countries_list).getDataBase();
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

    public ArrayList<Country> getCountries() {
        return countries;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.start_peopleActivity:
                intent = new Intent(this, PeopleActivity.class);
                startActivityForResult(intent, PEOPLE_REQUEST_CODE);
                break;
            case R.id.start_colorsActivity:
                intent = new Intent(this, ColorActivity.class);
                startActivityForResult(intent, COLOR_REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onOkButtonClicked(Country newCountry) {
        Toast.makeText(this, "Добавлена страна: ".concat(newCountry.toString()), Toast.LENGTH_SHORT).show();

        countries.add(newCountry);
        dataBase.addCountry(newCountry);
        adapter.getFilter().filter(filter.getText().toString());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == Activity.RESULT_OK ) {
            if ( requestCode == PEOPLE_REQUEST_CODE ) {
                ArrayList<String> selectedCountries =
                        data.getStringArrayListExtra(PeopleActivity.setCountries);
                adapter.showCountries(selectedCountries);
            } else if ( requestCode == COLOR_REQUEST_CODE ) {
                String color = data.getStringExtra(ColorActivity.COLOR_TAG);

                adapter.setBackgroungColor(Color.parseColor(color));
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onAddButtonClick() {
        fragmentAdd.show(getFragmentManager(), "add_TAG");
    }

    @Override
    protected void onDestroy() {
        dataBase.close();
        super.onDestroy();
    }
}
