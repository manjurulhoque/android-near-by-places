package com.manjurulhoque.mynearbyplaces.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.manjurulhoque.mynearbyplaces.R;
import com.manjurulhoque.mynearbyplaces.Util;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationFragment extends Fragment {

    private FusedLocationProviderClient mFusedLocationClient;
    LocationManager lm;
    LocationManager locationManager;

    double lat, lng;

    String longitude, latitude;

    ProgressDialog progressDialog;
    private TextView textViewAddress;
    private TextView textViewCity;
    private TextView textViewPostalCode;
    private TextView textViewLongitude;
    private TextView textViewLatitude;
    private TextView textViewCountryCode;
    private TextView textViewCountry;
    private TextView textViewDivision;

    public LocationFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        textViewAddress = view.findViewById(R.id.address);
        textViewCity = view.findViewById(R.id.city);
        textViewLongitude = view.findViewById(R.id.textViewLongitude);
        textViewLatitude = view.findViewById(R.id.textViewLatitude);
        textViewCountryCode = view.findViewById(R.id.textViewCountryCode);
        textViewCountry = view.findViewById(R.id.textViewCountry);
        textViewDivision = view.findViewById(R.id.textViewDivision);
        textViewPostalCode = view.findViewById(R.id.postalCode);

        locationService();

        if (lat != 0 && lng != 0) {
            getAddress();
        }

        return view;
    }

    private void locationService() {

        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Please wait while fetching data from GPS .......");
            progressDialog.setCancelable(false);
            progressDialog.show();


            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            final LocationListener locationListener = new MyLocationListener();
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                progressDialog.dismiss();

                return;
            }

            progressDialog.dismiss();

            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

            mFusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    if (location != null) {

                        lat = location.getLatitude();
                        lng = location.getLongitude();
                        textViewLatitude.setText(String.valueOf(lat));
                        textViewLongitude.setText(String.valueOf(lng));

                        getAddress();

                    } else {
                        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }

                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
                        } else if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

                            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, locationListener);
                        }
                    }
                }
            });
        } else {
            //checkGpsStatus();
            Toast.makeText(getContext(), "GPS off", Toast.LENGTH_SHORT).show();
        }
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {

            longitude = String.valueOf(loc.getLongitude());
            latitude = String.valueOf(+loc.getLatitude());

            lat = loc.getLatitude();
            lng = loc.getLongitude();
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

    private void getAddress() {

        String city = null;
        String address = null;
        String subCity = null;
        String postalCode = null;
        String division = null;
        String country = null;
        String countryCode = null;

        Geocoder gcd = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = gcd.getFromLocation(lat, lng, 1);
            if (addresses.size() > 0) {

                city = addresses.get(0).getLocality();
                address = addresses.get(0).getFeatureName();
                subCity = addresses.get(0).getSubLocality();
                postalCode = addresses.get(0).getPostalCode();
                division = addresses.get(0).getAdminArea();
                country = addresses.get(0).getCountryName();
                countryCode = addresses.get(0).getCountryCode();

                textViewAddress.setText(addresses.get(0).getAddressLine(0));
                textViewCountryCode.setText(countryCode);
                textViewCountry.setText(country);
                textViewDivision.setText(division);
                textViewPostalCode.setText(postalCode);
                textViewCity.setText(city);

                //Toast.makeText(getContext(), , Toast.LENGTH_SHORT).show();
                Log.d("LocationFragment", addresses.get(0).toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
