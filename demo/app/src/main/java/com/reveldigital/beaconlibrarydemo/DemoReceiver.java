package com.reveldigital.beaconlibrarydemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.reveldigital.adhawk.lib.IConstants;
import com.reveldigital.adhawk.lib.Beacon;

/**
 * Receives broadcasts for actions defined in the manifest.
 */
public class DemoReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(IConstants.ACTION_BEACON_FOUND)) {
            Bundle extras = intent.getExtras();
            Bundle b = extras.getBundle(IConstants.EXTRA_BEACON_BUNDLE);
            b.setClassLoader(Beacon.class.getClassLoader());
            Beacon beacon = b.getParcelable(IConstants.EXTRA_BEACON);
            // Do something with a beacon
            if (beacon != null) {
                Toast.makeText(context, beacon.getName() + " found!", Toast.LENGTH_SHORT).show();
            }
        }
        else if (intent.getAction().equals(IConstants.ACTION_BEACON_EXPIRED)) {
            Bundle extras = intent.getExtras();
            Bundle b = extras.getBundle(IConstants.EXTRA_BEACON_BUNDLE);
            b.setClassLoader(Beacon.class.getClassLoader());
            Beacon beacon = b.getParcelable(IConstants.EXTRA_BEACON);
            if (beacon != null) {
                Toast.makeText(context, beacon.getName() + " expired!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
