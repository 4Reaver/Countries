package com.example.reaver.countries;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Reaver on 19.07.2014.
 */
public class DetailActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Country country = getIntent().getExtras().getParcelable("Country");
        TextView name;
        TextView area;
        TextView population;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_info);

        name = (TextView) findViewById(R.id.detail_name);
        area = (TextView) findViewById(R.id.detail_area);
        population = (TextView) findViewById(R.id.detail_population);

        name.setText("Country: " + country.getName());
        area.setText("Area: " + country.getArea());
        population.setText("population: " + country.getPopulation());
    }
}