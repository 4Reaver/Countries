package com.example.reaver.countries;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Reaver on 23.07.2014.
 */
public class CountryAdapter extends BaseAdapter implements Filterable {
    private boolean changeBackground;
    private Context context;
    private LayoutInflater lInflater;
    private ArrayList<Country> countries;
    private ArrayList<Country> originalCountries;

    public CountryAdapter(Context context, ArrayList<Country> countries) {
        this.context = context;
        this.countries = countries;
        this.originalCountries = countries;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.changeBackground = false;
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

    public ArrayList<Country> getOriginalCountries() {
        return originalCountries;
    }

    public void invertChangeBackground() {
        changeBackground = !changeBackground;
    }

    @Override
    public int getCount() {
        return countries.size();
    }

    @Override
    public Object getItem(int i) {
        return countries.get(i);
    }

    @Override
    public long getItemId(int i) {
        return originalCountries.indexOf(getItem(i));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Country c = (Country) getItem(position);
        ImageView checkMark;
        Resources resources = context.getResources();

        if ( view == null ) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }

        ((ImageView) view.findViewById(R.id.flag)).setImageResource(Country.getIconID(c, context));
        checkMark = ((ImageView) view.findViewById(R.id.checked));
        if ( c.isChecked() ) {
            checkMark.setVisibility(View.VISIBLE);
        } else {
            checkMark.setVisibility(View.INVISIBLE);
        }
        ((TextView) view.findViewById(R.id.country_name)).setText(c.getName());
        ((TextView) view.findViewById(R.id.item_area)).setText("Area: " + c.getArea());
        ((TextView) view.findViewById(R.id.item_population)).setText("Population: " + c.getPopulation());
        if ( changeBackground && c.isChecked()) {
            view.setBackgroundColor(resources.getColor(R.color.listItem_selected_background));
        } else {
            view.setBackgroundColor(resources.getColor(R.color.list_background));
        }

        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<Country> filteredResult = getFilteredResults(constraint);
                FilterResults results = new FilterResults();

                results.values = filteredResult;
                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                countries = (ArrayList<Country>) filterResults.values;
                CountryAdapter.this.notifyDataSetChanged();
            }

            private ArrayList<Country> getFilteredResults(CharSequence constraint) {
                ArrayList<Country> result = new ArrayList<Country>();
                String cons = constraint.toString().toLowerCase();

                if ( constraint != null && constraint.length() > 0) {
                    for ( Country c : originalCountries ) {
                        if ( c.getName().toLowerCase().contains(cons) ) {
                            result.add(c);
                        }
                    }

                    return result;
                }
                return originalCountries;
            }
        };
    }

    public void showCountries(ArrayList<String> countriesList) {
        ArrayList<Country> selectedCountries = new ArrayList<Country>();

        for ( Country country : originalCountries ) {
            if ( countriesList.contains(country.getName()) ) {
                selectedCountries.add(country);
            }
        }

        setCountries(selectedCountries);
        notifyDataSetChanged();
    }
}
