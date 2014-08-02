package com.example.reaver.countries;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Reaver on 31.07.2014.
 */
public class ColorAdapter extends ArrayAdapter<String> {
    private ArrayList<String> colors;

    public ColorAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        this.colors = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView tv =(TextView) view.findViewById(R.id.tvColor);

        tv.setTextColor(Color.parseColor(tv.getText().toString()));

        return view;
    }
}
