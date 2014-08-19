package com.example.reaver.countries;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by Reaver on 02.08.2014.
 */
public class FragmentCountriesList extends Fragment implements View.OnClickListener, TextWatcher {
    private static final int DELETE_ID = 1;

    private ArrayList<Country> countries;
    private CountryAdapter adapter;
    private EditText filter;
    private MainActivity activity;
    private OnAddButtonClickListener parentActivity;// убрать это говно
    private DataBase dataBase;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            this.activity = (MainActivity) activity;
            parentActivity = (OnAddButtonClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement OnAddButtonClickListener");
        }
    }

    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_counries_list, container, false);
        Button addButton;
        Button changeBackground;
        Button delSelected;
        ListView lvMain;

        fillData();

        adapter = new CountryAdapter(activity, countries);
        addButton = (Button) view.findViewById(R.id.addButton);
        addButton.setOnClickListener(this);
        changeBackground = (Button) view.findViewById(R.id.btnChangeBackground);
        changeBackground.setOnClickListener(this);
        delSelected = (Button) view.findViewById(R.id.btnDelSelected);
        delSelected.setOnClickListener(this);
        filter = (EditText) view.findViewById(R.id.filter);
        filter.addTextChangedListener(this);
        lvMain = (ListView) view.findViewById(R.id.listViewMain);
        lvMain.setAdapter(adapter);
        lvMain.setOnItemClickListener(new ListViewItemDoubleClickListener(activity, adapter));
        registerForContextMenu(lvMain);

        return view;
    }

    public CountryAdapter getAdapter() {
        return adapter;
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addButton:
                parentActivity.onAddButtonClick();
                break;
            case R.id.btnDelSelected:
                ArrayList<Country> countriesSet = new ArrayList<Country>(countries);

                for ( Country c : countriesSet ) {
                    if ( c.isChecked() ) {
                        countries.remove(c);
                        dataBase.deleteCountry(c);
                    }
                }
                adapter.getFilter().filter(filter.getText().toString());
                adapter.notifyDataSetChanged();
                break;
            case R.id.btnChangeBackground:
                adapter.invertChangeBackground();
                adapter.notifyDataSetChanged();
                break;
        }
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
            int id = (int) info.id;

            dataBase.deleteCountry(countries.get(id));
            countries.remove(id);
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

    private void fillData() {
        Cursor c;
        int idColumnName;
        int idColumnArea;
        int idColumnPopulation;

        countries = new ArrayList<Country>();
        dataBase = new DataBase(activity);
        dataBase.open();
        c = dataBase.getAllData();
        idColumnName = c.getColumnIndex("country_name");
        idColumnArea = c.getColumnIndex("country_area");
        idColumnPopulation = c.getColumnIndex("country_population");

        for ( boolean i = c.moveToFirst(); i; i = c.moveToNext() ) {
            countries.add(new Country(activity, c.getString(idColumnName),
                    c.getInt(idColumnArea), c.getInt(idColumnPopulation)));
        }
    }

    public interface OnAddButtonClickListener {
        public void onAddButtonClick();
    }
}
