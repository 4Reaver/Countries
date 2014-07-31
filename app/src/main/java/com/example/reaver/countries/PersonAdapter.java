package com.example.reaver.countries;

import android.content.Context;
import android.provider.Contacts;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Reaver on 31.07.2014.
 */
public class PersonAdapter extends BaseAdapter implements View.OnClickListener,
        AdapterView.OnItemClickListener {
    private Context context;
    private LayoutInflater inflatter;
    ArrayList<Person> persons;

    public PersonAdapter(Context context, ArrayList<Person> persons) {
        this.context = context;
        this.inflatter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.persons = persons;
    }

    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Object getItem(int i) {
        return persons.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CheckBox checkmark;
        View view = convertView;
        Person person = persons.get(position);

        if ( view == null ) {
            view = inflatter.inflate(R.layout.person, parent, false);
        }

        checkmark = ((CheckBox) view.findViewById(R.id.people_checkbox));
        checkmark.setChecked(person.isChecked());
        checkmark.setTag(person);
        checkmark.setOnClickListener(this);
        ((TextView) view.findViewById(R.id.person_name)).setText(person.getName());
        ((TextView) view.findViewById(R.id.person_residence)).setText(person.getResidence());

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        persons.get(i).invertIsChecked();
        this.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
       switch (view.getId()) {
           case R.id.people_checkbox:
               ((Person) view.getTag()).invertIsChecked();
               notifyDataSetChanged();
       }
    }
}
