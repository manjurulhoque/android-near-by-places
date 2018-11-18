package com.manjurulhoque.mynearbyplaces;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.manjurulhoque.mynearbyplaces.fragment.LocationFragment;

public class Util implements LocationListener {

    public Location location;
    private Context context;
    ProgressDialog progressDialog;
    String latitude, longitude;

    public Util(Context context) {
        this.context = context;
    }

    public synchronized Location getLocation() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Getting location...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        FusedLocationProviderClient mFusedLocationClient;
        final LocationManager lm;
        final LocationManager locationManager;

        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            final LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location loc) {
                    location = loc;
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return location;
            }

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

            mFusedLocationClient.getLastLocation().addOnSuccessListener((Activity) context, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location loc) {

                    if (loc != null) {
                        location = loc;
                        progressDialog.dismiss();
                        Toast.makeText(context, "Here", Toast.LENGTH_SHORT).show();
                    } else {
                        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                return;
                            }

                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

                        } else if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

                            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, locationListener);
                        }
                    }
                }
            });
            //Toast.makeText(context, String.valueOf(location.getLongitude()), Toast.LENGTH_SHORT).show();
            return location;
        } else {
            Toast.makeText(context, "GPS off", Toast.LENGTH_SHORT).show();
        }

        return location;
    }

    public void getLongitude() {
        Toast.makeText(context, longitude, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude() + "";
        longitude = location.getLongitude() + "";
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
