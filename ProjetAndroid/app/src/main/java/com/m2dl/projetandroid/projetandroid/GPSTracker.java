package com.m2dl.projetandroid.projetandroid;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;


    public class GPSTracker extends Service implements LocationListener {

        private final Context appContext;
        private boolean isGPSEnabled = false;
        private boolean isGpsActive = false;
        private Location location;
        private double latitude;
        private double longitude;
        protected LocationManager locationManager;

        public  GPSTracker()
        {
            locationManager = null;
            location = null;
            appContext = null;

        }
        public GPSTracker(Context context) {
            isGpsActive = false;
            isGPSEnabled = false;
            this.appContext = context;
            getLocation();
        }

        public Location getLocation() {
            try {
                locationManager = (LocationManager) appContext.getSystemService(LOCATION_SERVICE);

                // Récupérer l'état du GPS (actif ou non)
                isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                if (isGPSEnabled ) {
                    this.isGpsActive = true;

                    if (isGPSEnabled) {
                        if (location == null) {
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                            Log.d("GPS Enabled", "GPS Enabled");
                            if (locationManager != null) {
                                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (location != null) {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return location;
        }

        /**
         * Stop using GPS listener
         * Calling this function will stop using GPS in your app
         * */
        public void stopUsingGPS(){
            if(locationManager != null){
                locationManager.removeUpdates(GPSTracker.this);
            }
        }

        /**
         * Function to get latitude
         * */
        public double getLatitude(){
            if(location != null){
                latitude = location.getLatitude();
            }

            // return latitude
            return latitude;
        }

        /**
         * Function to get longitude
         * */
        public double getLongitude(){
            if(location != null){
                longitude = location.getLongitude();
            }

            // return longitude
            return longitude;
        }
        /**
         * Function to check GPS enabled
         * @return boolean
         * */
        public boolean isGpsActive() {
            return this.isGpsActive;
        }

        /**
         * Function to show settings alert dialog
         * On pressing Settings button will lauch Settings Options
         * */
        public void showSettingsAlert(){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(appContext);

            alertDialog.setTitle("GPS Settings Dialog");

            alertDialog.setMessage("The GPS is not enabled. Would you like to go to the settings menu?");

            alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    appContext.startActivity(intent);
                }
            });

            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            alertDialog.show();
        }

        @Override
        public void onLocationChanged(Location location) {
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

        @Override
        public IBinder onBind(Intent arg0) {
            return null;
        }

    }