# beacon-android-sdk
Beacon library for adHawk implementations on Android.

This purpose of this library is to effeciently monitor and relay beacon information to apps wishing to integrate with the RevelDigital platform. Beacon information is delivered via broadcast to any listening ```BroadcastReceiver``` registered to an application. Once the libary is initialized the app will begin to receive various broadcasts when in the vicinity of a beacon registered on the RevelDigital platform.

# Requirements

  * bson4jackson-2.4.0 ```compile 'de.undercouch:bson4jackson:2.4.0'```
  * gson2.3.1 ```compile 'com.google.code.gson:gson:2.3.1'```
  * okio-1.3.0 ```compile 'com.squareup.okio:okio:1.3.0'```
  * okhttp-urlconnection-2.3.0 ```compile 'com.squareup.okhttp:okhttp-urlconnection:2.3.0'```
  * okhttp-2.3.0 ```compile 'com.squareup.okhttp:okhttp:2.3.0'```
  * retrofit-1.9.0 ```compile 'com.squareup.retrofit:retrofit:1.9.0'```
  * support-v4-22.0.0 ```compile 'com.android.support:support-v4:22.1.1'```
  * joda-time-2.7 ```compile 'joda-time:joda-time:2.7'```
  * play-services-base-7.0.0 ```compile 'com.google.android.gms:play-services-base:7.0.0'```
  * play-services-maps-7.0.0 ```compile 'com.google.android.gms:play-services-maps:7.0.0'```
  * play-services-location-7.0.0 ```compile 'com.google.android.gms:play-services-location:7.0.0'```
  * reveldigital-api-1.4.4 ```compile 'com.reveldigital:reveldigital-api:1.4.4'```
  * playerapi-1.0-SNAPSHOT ```request access by emailing support@reveldigital.com```

# Usage

## To start

```
try {
  RevelBeacon revelBeacon = new RevelBeacon.Builder(this).startOnBoot(true).build();
  revelBeacon.connect();
} catch (RevelBeacon.MalformedRevelBeaconException e) {
  e.printStackTrace();
}
```

## To stop

```
revelBeacon.disconnect();
```

## How to receive beacon data

Register a ```BroadcastReceiver``` either manually or via an ```intent-filter``` in your ```AndroidManifest.xml```

An example of intent-filter:

```
<intent-filter>
  <action android:name="com.reveldigital.adhawk.lib.action.BEACON_FOUND"/>
</intent-filter>
<intent-filter>
  <action android:name="com.reveldigital.adhawk.lib.action.BEACON_EXPIRED"/>
</intent-filter>
```

Extract the beacon data from the broadcast intent:

```
if (intent.getAction().equals(BEACON_FOUND_ACTION)) {
  Bundle extras = intent.getExtras();
  Bundle b = extras.getBundle(BEACON_BUNDLE);
  b.setClassLoader(Beacon.class.getClassLoader()); // for cross process applications
  Beacon beacon = b.getParcelable(BEACON_DETECTED_BEACON);
}
```

The two actions containing beacon data include
  * ```BEACON_EXPIRED_ACTION```
  * ```BEACON_FOUND_ACTION```
  
