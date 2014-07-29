package com.example.reaver.countries;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by Reaver on 29.07.2014.
 */
public class ListViewItemDoubleClickListener implements AdapterView.OnItemClickListener{
    final int DOUBLE_TAP = 2;
    final int SINGLE_TAP = 1;
    final long DELAY = 1000L;

    boolean isFirstTouchOccured = false;
    int handlerPosition;
    int position;
    long id;
    AdapterView adapterView;
    Context context;
    CountryAdapter adapter;
    Handler handler;
    Message message;
    View view;

    public ListViewItemDoubleClickListener(Context context, CountryAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {
                    case SINGLE_TAP:
                        onSingleTap(adapterView, view, position, id);
                        break;
                    case DOUBLE_TAP:
                        onDoubleTap(adapterView, view, position, id);
                        break;
                }
                isFirstTouchOccured = false;
            }
        };
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        this.adapterView = adapterView;
        this.view = view;
        this.position = i;
        this.id = l;

        if ( !isFirstTouchOccured ) {
            handlerPosition = i;
            isFirstTouchOccured = true;

            message = new Message();
            message.what = SINGLE_TAP;
            handler.sendMessageDelayed(message, DELAY);
        } else if ( i == handlerPosition ) {
            handler.removeMessages(SINGLE_TAP);

            message = new Message();
            message.what = DOUBLE_TAP;
            handler.sendMessage(message);
        } else {
            handlerPosition = i;
            handler.removeMessages(SINGLE_TAP);

            message = new Message();
            message.what = SINGLE_TAP;
            handler.sendMessageDelayed(message, DELAY);
        }
    }

    public void onSingleTap(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("Country", adapter.getCountries().get(i));
        context.startActivity(intent);
    }

    public void onDoubleTap(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(context, "Doubletap!!" + adapter.getCountries().get(i).getName(), Toast.LENGTH_SHORT).show();
        adapter.getCountries().get(i).invertIsChecked();
        adapter.notifyDataSetChanged();
    }
}
