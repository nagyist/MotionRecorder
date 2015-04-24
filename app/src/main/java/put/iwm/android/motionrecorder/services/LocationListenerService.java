package put.iwm.android.motionrecorder.services;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.sql.SQLException;

import put.iwm.android.motionrecorder.database.LocationDatabaseAdapter;

/**
 * Created by Szymon on 2015-04-23.
 */
public class LocationListenerService extends Service implements LocationListener, GoogleApiClient.ConnectionCallbacks {

    public static final String ACTION = "put.iwm.android.motionrecorder.services.LOCATION_UPDATE";
    private static final String TAG = LocationListenerService.class.toString();
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private LocationDatabaseAdapter locationRepository;

    @Override
    public void onCreate() {

        requestLocationUpdates();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "Startujemy!");

        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    private void requestLocationUpdates() {

        locationRepository = new LocationDatabaseAdapter(getBaseContext());

        createGoogleApiClient();
        createLocationRequest();
        connectGoogleApiClient();
    }

    private synchronized void createGoogleApiClient() {

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void createLocationRequest() {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void connectGoogleApiClient() {

        googleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        Log.i(TAG, "Połączono!");

        startLocationUpdates();

        Log.i(TAG, "startLocationUpdates wykonano!");
    }

    private void startLocationUpdates() {

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }


    @Override
    public void onLocationChanged(Location location) {

        try {
            locationRepository.open();
            locationRepository.addLocation(location);
            locationRepository.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(ACTION);
        //intent.putExtra("lat", location.getLatitude());
        //intent.putExtra("lng", location.getLongitude());

        sendBroadcast(intent);

        Log.i(TAG, "Wysłano powiadomienie do mapy!");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}