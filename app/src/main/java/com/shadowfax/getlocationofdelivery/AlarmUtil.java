package com.shadowfax.getlocationofdelivery;



import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by karthik on 29-01-2016.
 */
public class AlarmUtil {

    public static double distance(double curLat, double curLang, double destLat, double destLang, String unit) {
        double theta = curLang - destLang;
        double dist = Math.sin(deg2rad(curLat)) * Math.sin(deg2rad(destLat)) + Math.cos(deg2rad(curLat)) * Math.cos(deg2rad(destLat)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    /**
     *
     * @param curLatLong
     * @param destLat
     * @param destLang
     * @param unit
     * @return
     */
    public static double distance(LatLng curLatLong, double destLat, double destLang, String unit) {
        double theta = curLatLong.longitude - destLang;
        double dist = Math.sin(deg2rad(curLatLong.latitude)) * Math.sin(deg2rad(destLat)) + Math.cos(deg2rad(curLatLong.latitude)) * Math.cos(deg2rad(destLat)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }



    /**
     * get the current time in the specified format
     * @return
     */
    public static String getCurrentTime()
    {
        SimpleDateFormat s = new SimpleDateFormat("dd-MM hh:mm:ss");
        String format = s.format(new Date());

        return (format != null)?format:"";
    }
}
