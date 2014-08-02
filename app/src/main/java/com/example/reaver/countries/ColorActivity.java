package com.example.reaver.countries;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Reaver on 01.08.2014.
 */
public class ColorActivity extends Activity implements AdapterView.OnItemClickListener {
    static final String COLOR_TAG = "color";
    ArrayList<String> colors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ListView lvColors;
        ColorAdapter colorsAdapter;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.colors_activity);

        colors = new ArrayList<String>();
        colors.add("#FFFF0000");
        colors.add("#FF00FFFF");
        colors.add("#FF0000FF");

        lvColors = (ListView) findViewById(R.id.lvColors);
        colorsAdapter = new ColorAdapter(this, R.layout.color_list_item, colors);
        lvColors.setAdapter(colorsAdapter);
        lvColors.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();
        String color = colors.get(i);

        intent.putExtra(COLOR_TAG, color);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
