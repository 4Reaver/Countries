package com.example.reaver.countries;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Reaver on 31.07.2014.
 */
public class PeopleActivity extends Activity {
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
    }


}
