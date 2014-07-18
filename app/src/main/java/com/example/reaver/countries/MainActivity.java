package com.example.reaver.countries;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends Activity implements View.OnClickListener, FragmentAdd.OnOkButtonClickListener {
    private static final int DELETE_ID = 1;

    private ArrayAdapter<String> adapter;
    private ArrayList<String> countries = new ArrayList<String>();
    private ArrayList<String> squares = new ArrayList<String>();
    private DialogFragment fragmentAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button addButton;
        ListView lvMain;
        String name;
        String square;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            InputStream is = getAssets().open("namesandsquares.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            while ( (name = br.readLine()) != null && (square = br.readLine()) != null ) {
                countries.add(name);
                squares.add(square);
            }
        } catch (IOException e) {
            Log.d("MY_LOG", "File \"namesandsquares\" not found");
        }

        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(this);
        lvMain = (ListView) findViewById(R.id.listViewMain);
        adapter = new ArrayAdapter<String>(this, R.layout.item, countries);
        lvMain.setAdapter(adapter);
        registerForContextMenu(lvMain);
        fragmentAdd = new FragmentAdd();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addButton:
                fragmentAdd.show(getFragmentManager(), "add_TAG");
                break;
        }
    }

    @Override
    public void onOkButtonClicked(String newCountry) {
        Toast.makeText(this, "Добавлена страна: ".concat(newCountry), Toast.LENGTH_SHORT).show();
        countries.add(newCountry);
        adapter.notifyDataSetChanged();
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

            countries.remove(info.position);
            adapter.notifyDataSetChanged();
        }
        return super.onContextItemSelected(item);
    }
}
