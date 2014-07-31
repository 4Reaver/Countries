package com.example.reaver.countries;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Reaver on 31.07.2014.
 */
public class PeopleActivity extends Activity {
    static final String setCountries = "selected countries";

    private PersonAdapter personAdapter;
    private ArrayList<Person> persons;
    private ListView lvPersons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_list);

        persons = new ArrayList<Person>();
        persons.add(new Person("Vasya", "Ukraine"));
        persons.add(new Person("Igor", "Russia"));
        persons.add(new Person("Yakov", "Argentina"));

        lvPersons = (ListView) findViewById(R.id.people_listView);
        personAdapter = new PersonAdapter(this, persons);
        lvPersons.setAdapter(personAdapter);
        lvPersons.setOnItemClickListener(personAdapter);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        Set<String> selectedCountries = new HashSet<String>();

        for ( Person p : persons ) {
            if ( p.isChecked() ) {
                selectedCountries.add(p.getResidence());
            }
        }
        intent.putStringArrayListExtra(setCountries, new ArrayList<String>(selectedCountries));
        setResult(Activity.RESULT_OK, intent);
        finish();
        return true;
    }
}
