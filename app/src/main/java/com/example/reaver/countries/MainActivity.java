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
        TextWatcher {
    private static final int DELETE_ID = 1;
    private static final int PEOPLE_REQUEST_CODE = 42;

    private CountryAdapter adapter;
    private ArrayList<Country> countries = new ArrayList<Country>();
    private DialogFragment fragmentAdd;
    private EditText filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button addButton;
        Button delSelected;
        Button changeBackground;
        ListView lvMain;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillData();

        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(this);
        delSelected = (Button) findViewById(R.id.btnDelSelected);
        delSelected.setOnClickListener(this);
        changeBackground = (Button) findViewById(R.id.btnChangeBackground);
        changeBackground.setOnClickListener(this);
        filter = (EditText) findViewById(R.id.filter);
        filter.addTextChangedListener(this);
        lvMain = (ListView) findViewById(R.id.listViewMain);
        adapter = new CountryAdapter(this, countries);
        lvMain.setAdapter(adapter);

        lvMain.setOnItemClickListener(new ListViewItemDoubleClickListener(this, adapter));
        registerForContextMenu(lvMain);
        fragmentAdd = new FragmentAdd();
        findViewById(R.id.start_peopleActivity).setOnClickListener(this);
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

    private void fillData() {
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
                countries.add(new Country(this, name, area, population));
                scanner.nextLine();
            }
        } catch (IOException e) {
            Log.d("MY_LOG", "File \"namesandsquares\" not found");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addButton:
                fragmentAdd.show(getFragmentManager(), "add_TAG");
                break;
            case R.id.btnDelSelected:
                ArrayList<Country> countriesSet = new ArrayList<Country>(countries);

                for ( Country c : countriesSet ) {
                    if ( c.isChecked() ) {
                        countries.remove(c);
                    }
                }
                adapter.getFilter().filter(filter.getText().toString());
                adapter.notifyDataSetChanged();
                break;
            case R.id.btnChangeBackground:
                adapter.invertChangeBackground();
                adapter.notifyDataSetChanged();
                break;
            case R.id.start_peopleActivity:
                Intent intent = new Intent(this, PeopleActivity.class);
                startActivityForResult(intent, PEOPLE_REQUEST_CODE);
        }
    }

    @Override
    public void onOkButtonClicked(Country newCountry) {
        Toast.makeText(this, "Добавлена страна: ".concat(newCountry.toString()), Toast.LENGTH_SHORT).show();

        countries.add(newCountry);
        adapter.getFilter().filter(filter.getText().toString());
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

            countries.remove((int) info.id);
            adapter.getFilter().filter(filter.getText().toString());
            adapter.notifyDataSetChanged();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        adapter.getFilter().filter(filter.getText().toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == PEOPLE_REQUEST_CODE && resultCode == Activity.RESULT_OK ) {
            ArrayList<String> selectedCountries =
                    data.getStringArrayListExtra(PeopleActivity.setCountries);
             adapter.showCountries(selectedCountries);
        }

    }
}
