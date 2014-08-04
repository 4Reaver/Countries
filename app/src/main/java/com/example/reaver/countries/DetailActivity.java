package com.example.reaver.countries;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Reaver on 19.07.2014.
 */
public class DetailActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Country country = getIntent().getExtras().getParcelable("Country");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_info);

        Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment_detail_info);
        ((FragmentDetailInfo) fragment).updateContent(country);
    }
}
