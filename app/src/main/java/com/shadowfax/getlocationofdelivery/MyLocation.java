package com.shadowfax.getlocationofdelivery;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by User on 03-11-2015.
 */
public class MyLocation extends Service {

    private LocationManager mLocationManager;
    private Location mcurLocation;
    private Boolean mLocationChanged;
    final static int RQS_1 = 1;
    private static double mDestLat, mDestLang;
    private Context mcontext;
    private int mId;


    Handler handler = new Handler();

    LocationListener gpsListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            Log.e(TAG, "Inside Location listener............");
            if (mcurLocation == null) {
                mcurLocation = location;
                mLocationChanged = true;
            }

            if (mcurLocation.getLatitude() == location.getLatitude() && mcurLocation.getLongitude() == location.getLongitude())
                mLocationChanged = false;
            else
                mLocationChanged = true;

            mcurLocation = location;

            if (mLocationChanged)
                if (ActivityCompat.checkSelfPermission(MyLocation.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MyLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
            mLocationManager.removeUpdates(gpsListener);

        }

        public void onProviderDisabled(String provider) {

        }

        public void onProviderEnabled(String provider) {
            // Log.w("GPS", "Location changed", null);
        }

        public void onStatusChanged(String provider, int status,
                                    Bundle extras) {
            if (status == 0)// UnAvailable
            {

            } else if (status == 1)// Trying to Connect
            {

            } else if (status == 2) {// Available

            }
        }

    };


    @Override
    public void onCreate() {
        Toast.makeText(getBaseContext(), "Inside onCreate of Service", Toast.LENGTH_LONG).show();

        Log.e(TAG, "Inside onCreate of Service");
        mcontext = this;
        super.onCreate();


        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, gpsListener);
           /*if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                   mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,20000, 1, gpsListener);
           } else {
                   this.startActivity(new Intent("android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS"));
           }
 */
        if (mcurLocation != null) {
            double lat = mcurLocation.getLatitude();
            double lng = mcurLocation.getLongitude();
            String latVal = String.valueOf(lat);
            String lngVal = String.valueOf(lng);
            Toast.makeText(getBaseContext(), "Lat : " + String.valueOf(lat) + "\n Long : " + String.valueOf(lng), Toast.LENGTH_LONG).show();

           /* if(latVal.contains(dest_Lat) && lngVal.contains(dest_Log)) {
                setNotification();
            }*/
        } else {
            Log.e(TAG, "Didn Get any location");
            Toast.makeText(getBaseContext(), "Didn Get any location", Toast.LENGTH_LONG).show();
        }
    }

    final String TAG = "LocationService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getBaseContext(), "Inside onStartCommand of Service", Toast.LENGTH_LONG).show();
        Log.e(TAG, "Inside onStartCommand of Service");
        mId = intent.getExtras().getInt("id");
       /* mDestLat = intent.getExtras().getDouble("lat");

        mDestLang = intent.getExtras().getDouble("lang");*/
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onLowMemory() {
        // TODO Auto-generated method stub
        super.onLowMemory();
    }


    @Override
    public void onStart(Intent i, int startId) {
        Toast.makeText(getBaseContext(), "Inside onStart of Service", Toast.LENGTH_LONG).show();
        Log.e(TAG, "Inside onStart of Service");

        handler.postDelayed(GpsFinder, 5000);// will start after 5 seconds
    }

    public IBinder onBind(Intent arg0) {
        Log.e(TAG, "Inside onBind of Service");
        return null;
    }

    public Runnable GpsFinder = new Runnable() {

        public void run() {
            // TODO Auto-generated method stub

            if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

                if (ActivityCompat.checkSelfPermission(MyLocation.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MyLocation.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, gpsListener);
            } else {
                getApplicationContext().startActivity(new Intent("android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS"));
            }

            if (mcurLocation != null) {
                double cur_lat = mcurLocation.getLatitude();
                double cur_lng = mcurLocation.getLongitude();

                //Toast.makeText(getBaseContext(),"Lat : " + String.valueOf(cur_lat) + "\n Long : "+ String.valueOf(lng), Toast.LENGTH_LONG).show();
                Log.d("LocationSErvice", "lat n lang vals are..." + String.valueOf(cur_lat) + " " + String.valueOf(cur_lng));
                JSONObject json = new JSONObject();
                try {
                    json.put("id", mId);
                    json.put("latitude", cur_lat);

                    json.put("longitude", cur_lng);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(MyLocation.this,SendDetailsService.class);
                intent.putExtra("json_str",json.toString());
                Log.d("LocationService","calling senddetails service.." + json.toString());
                startService(intent);
                // double distance = AlarmUtil.distance(cur_lat, cur_lng, mDestLat, mDestLang, "K");
                //Log.d(TAG, "diatance is..." + distance);

            }

            handler.postDelayed(GpsFinder, 30000);// register again to start after 30 seconds...


        }
    };


    private void setNotification() {


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(MyLocation.this);
        //Title for Notification
        notificationBuilder.setContentTitle("GAlarm");
        //Message in the Notification
        notificationBuilder.setContentText("you will reach your destination in 10 minutes.");
        //Alert shown when Notification is received
        notificationBuilder.setTicker("New Message Alert!");
        //Icon to be set on Notification
        notificationBuilder.setSmallIcon(R.drawable.ic_launcher);

        notificationBuilder.setAutoCancel(true);
        //Creating new Stack Builder
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(MyLocation.this);
        stackBuilder.addParentStack(MainActivity.class);

        //Intent which is opened when notification is clicked
        Intent resultIntent = new Intent(MyLocation.this, MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent pIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(pIntent);
        NotificationManager notificationManager = (NotificationManager) mcontext.getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());

        Uri notification2 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone r = RingtoneManager.getRingtone(mcontext, notification2);
        r.play();
    }

}
