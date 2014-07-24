package com.example.reaver.countries;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Reaver on 19.07.2014.
 */
public class DetailActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Country country = getIntent().getExtras().getParcelable("Country");
        ImageView flag;
        TextView name;
        TextView area;
        TextView population;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_info);

        flag = (ImageView) findViewById(R.id.detail_flag);
        name = (TextView) findViewById(R.id.detail_name);
        area = (TextView) findViewById(R.id.detail_area);
        population = (TextView) findViewById(R.id.detail_population);

        flag.setImageResource(Country.getFullFlagID(country, this));
        name.setText("Country: " + country.getName());
        area.setText("Area: " + country.getArea());
        population.setText("Population: " + country.getPopulation());
    }
}
