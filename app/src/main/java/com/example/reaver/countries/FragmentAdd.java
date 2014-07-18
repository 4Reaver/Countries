package com.example.reaver.countries;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

/**
 * Created by Reaver on 17.07.2014.
 */
public class FragmentAdd extends DialogFragment implements View.OnClickListener{
    public interface OnOkButtonClickListener {
        public void onOkButtonClicked(String newCountry);
    }

    private EditText editText;
    private OnOkButtonClickListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnOkButtonClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString().concat(" must implement OnOkButtonClickListener"));
        }
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Button okButton;
        View v = inflater.inflate(R.layout.add_dialog, container, false);

        getDialog().getWindow().requestFeature(STYLE_NO_TITLE);
        okButton = (Button) v.findViewById(R.id.OKbutton);
        editText = (EditText) v.findViewById(R.id.editText);
        okButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        if ( getDialog() == null ) {
            return;
        }

        int dialogWidth = ViewGroup.LayoutParams.MATCH_PARENT;
        int dialogHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.OKbutton:
                listener.onOkButtonClicked(editText.getText().toString());
                editText.setText("");
                this.dismiss();
                break;
        }
    }
}
