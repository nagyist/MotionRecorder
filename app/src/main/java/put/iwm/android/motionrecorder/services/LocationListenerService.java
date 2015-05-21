package put.iwm.android.motionrecorder.services;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

//import android.location.LocationListener;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.sql.SQLException;

import javax.inject.Inject;

import put.iwm.android.motionrecorder.application.MotionRecorderApplication;
import put.iwm.android.motionrecorder.contracts.LocationObserver;
import put.iwm.android.motionrecorder.training.TrainingTimer;

/**
 * Created by Szymon on 2015-04-23.
 */
public class LocationListenerService extends Service implements LocationListener, GoogleApiClient.ConnectionCallbacks {

    public static final String ACTION = "put.iwm.android.motionrecorder.services.LOCATION_UPDATE";
    private static final String TAG = LocationListenerService.class.toString();
    private static final long updateTime = 1000;
    private static final float updateDistance = 0;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;

    @Inject LocationObserver locationObserver;
    @Inject TrainingTimer trainingTimer;

    @Override
    public void onCreate() {

        MotionRecorderApplication.getApplicationComponent().inject(this);

        requestLocationUpdates();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "Startujemy!");

        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    private void requestLocationUpdates() {

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
        locationRequest.setInterval(updateTime);
        locationRequest.setFastestInterval(updateTime);
        locationRequest.setSmallestDisplacement(updateDistance);
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

        processLocationUpdate(location);
    }

    private void processLocationUpdate(Location location) {

        location.setTime(trainingTimer.getDurationTime());
        locationObserver.processLocationUpdate(location);

        Log.i(TAG, "Aktualizuję lokalizację! Lat: " + location.getLatitude() + ", Lon: " + location.getLongitude());
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "onConnectionSuspended!");
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        stopSelf();

        Log.i(TAG, "onDestroy!");
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}
