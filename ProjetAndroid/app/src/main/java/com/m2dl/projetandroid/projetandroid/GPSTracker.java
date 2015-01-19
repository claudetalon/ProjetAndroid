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
    boolean gpsActive = false;
    boolean locationUpdt = false;
    private Location location;
    private double latitude;
    private double longitude;
    protected LocationManager locationManager;

    public  GPSTracker()
    {
        this.appContext = null;
        location = null;
        latitude = 0.d;
        longitude = 0.d;
    }

    public GPSTracker(Context context) {
        this.appContext = context;
        getLocation();
    }

    public Location getLocation() {
        locationUpdt = false;

        if (appContext != null) {
            try {
                locationManager = (LocationManager) appContext.getSystemService(LOCATION_SERVICE);

                // Récupérer l'état du GPS (actif ou non)
                gpsActive = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                if (gpsActive) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);//Met à jour le locationManager
                        locationManager.removeUpdates(this); //Permet d'éviter de consommer de l'énergie inutillement
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); //Récupération de la localisation
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                locationUpdt = true;
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return location;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    //Permet de vérifier que la localisation a bien été mise à jour
    public boolean locationIsUpToDate() {
        return locationUpdt;
    }

    //Cette fonction met fin à l'utilisation du GPS dans l'application
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    //Fonction qui permet d'afficher une alerte et de rediriger vers le paramettrage du GPS dans le cas ou il n'est pas activé
    public void showSettingsAlert(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(appContext);
        alertDialog.setTitle("GPS settings");
        alertDialog.setMessage("The GPS is disabled. Would you like to go to settings menu?");

        // Rediriger vers les paramettres GPS
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                appContext.startActivity(intent);
            }
        });

        // Annuler l'activation du GPS
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