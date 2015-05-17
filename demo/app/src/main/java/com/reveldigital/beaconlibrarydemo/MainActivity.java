package com.reveldigital.beaconlibrarydemo;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import com.reveldigital.adhawk.lib.Beacon;
import com.reveldigital.adhawk.lib.BeaconClient;
import com.reveldigital.adhawk.lib.IConstants;
import java.util.ArrayList;

/**
 * Main Activity, demonstrates usage of programmatically registered BroadcastReceiver
 */
public class MainActivity extends ListActivity {
    private BeaconClient beaconClient;
    private ArrayList<String> mItemList = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // set list adapter
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mItemList);
        setListAdapter(mAdapter);
        // register broadcast receiver
        Receiver receiver = new Receiver();
        IntentFilter intentFound = new IntentFilter(IConstants.ACTION_BEACON_FOUND);
        IntentFilter intentExpired = new IntentFilter(IConstants.ACTION_BEACON_EXPIRED);
        registerReceiver(receiver, intentFound);
        registerReceiver(receiver, intentExpired);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // instantiate a service
        try {
            beaconClient = new BeaconClient.Builder(this).startOnBoot(true).build();
            beaconClient.connect();
        } catch (BeaconClient.MalformedRevelBeaconException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // stop the service when user leaves the activity
        if (beaconClient != null) {
            beaconClient.disconnect();
        }
    }

    /** Simply adds item to the ListVIew. */
    public void addItems(String newItem) {
        mAdapter.add(newItem);
    }

    /**
     * Inner class that handles broadcasts
     */
    class Receiver extends BroadcastReceiver {
        // catch messages from intent
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(IConstants.ACTION_BEACON_FOUND)) {
                Bundle extras = intent.getExtras();
                Bundle b = extras.getBundle(IConstants.EXTRA_BEACON_BUNDLE);
                b.setClassLoader(Beacon.class.getClassLoader());
                Beacon beacon = b.getParcelable(IConstants.EXTRA_BEACON);
                if (beacon != null) {
                    addItems(beacon.getName() + " found!");
                }
            } else if (intent.getAction().equals(IConstants.ACTION_BEACON_EXPIRED)) {
                Bundle extras = intent.getExtras();
                Bundle b = extras.getBundle(IConstants.EXTRA_BEACON_BUNDLE);
                b.setClassLoader(Beacon.class.getClassLoader());
                Beacon beacon = b.getParcelable(IConstants.EXTRA_BEACON);
                if (beacon != null) {
                    addItems(beacon.getName() + " expired!");
                }
            }
        }
    }
}
