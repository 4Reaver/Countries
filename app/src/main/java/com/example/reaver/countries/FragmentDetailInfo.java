package com.example.reaver.countries;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Reaver on 02.08.2014.
 */
public class FragmentDetailInfo extends Fragment {
    ImageView flag;
    TextView name;
    TextView area;
    TextView population;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_info, container, false);

        flag = (ImageView) view.findViewById(R.id.detail_flag);
        name = (TextView) view.findViewById(R.id.detail_name);
        area = (TextView) view.findViewById(R.id.detail_area);
        population = (TextView) view.findViewById(R.id.detail_population);
        return view;
    }

    public void updateContent(Country country) {
        flag.setImageResource(Country.getFullFlagID(country, getActivity()));
        name.setText("Country: " + country.getName());
        area.setText("Area: " + country.getArea());
        population.setText("Population: " + country.getPopulation());
    }
}
