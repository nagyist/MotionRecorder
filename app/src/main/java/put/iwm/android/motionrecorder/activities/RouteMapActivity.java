package put.iwm.android.motionrecorder.activities;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import put.iwm.android.motionrecorder.R;
import put.iwm.android.motionrecorder.base.BaseActivity;
import put.iwm.android.motionrecorder.database.repository.TrainingRepository;
import put.iwm.android.motionrecorder.training.RoutePoint;
import put.iwm.android.motionrecorder.training.Training;

public class RouteMapActivity extends BaseActivity {

    private static final String TAG = RouteMapActivity.class.toString();
    private GoogleMap map;
    private MapView mapView;
    private Training training;
    @Inject TrainingRepository trainingRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);

        setContentView(R.layout.activity_route_map);

        MapsInitializer.initialize(this);
        setupMapView(savedInstanceState);

        setupTraining();
        processRoutePoints(training.getRoutePoints());
    }

    private void setupMapView(Bundle savedInstanceState) {

        if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {

            mapView = (MapView) findViewById(R.id.map);
            mapView.onCreate(savedInstanceState);
            mapView.onResume();

            if(mapView != null)
                setupMap();
            else
                Log.i(TAG, "MAPVIEW_NULL");
        }
    }

    private void setupMap() {

        map = mapView.getMap();
        map.setMyLocationEnabled(false);
        map.getUiSettings().setMapToolbarEnabled(false);
        map.getUiSettings().setMyLocationButtonEnabled(false);
    }

    private void setupTraining() {

        Bundle arguments = getIntent().getExtras();
        long trainingId = arguments.getLong("trainingId");

        training = trainingRepository.findById((int)trainingId);
    }

    public void processRoutePoints(List<RoutePoint> routePoints) {

        if(!routePoints.isEmpty()) {

            List<LatLng> coordinates = new LinkedList<>();
            for (RoutePoint routePoint : routePoints)
                coordinates.add(new LatLng(routePoint.getLatitude(), routePoint.getLongitude()));

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(coordinates.get(coordinates.size() - 1), 18.2f);
            map.animateCamera(cameraUpdate);

            PolylineOptions polyline = new PolylineOptions();
            polyline.addAll(coordinates);
            polyline.width(4.2f);
            polyline.color(Color.rgb(0x42, 0x85, 0xF2));
            polyline.geodesic(true);

            map.addPolyline(polyline);

            LatLng firstCoordinate = new LatLng(
                    coordinates.get(0).latitude,
                    coordinates.get(0).longitude);
            LatLng lastCoordinate = new LatLng(
                    coordinates.get(coordinates.size() - 1).latitude,
                    coordinates.get(coordinates.size() - 1).longitude);

            map.addMarker(new MarkerOptions().position(firstCoordinate).title("Początek"));
            map.addMarker(new MarkerOptions().position(lastCoordinate).title("Koniec"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_route_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings)
            return true;

        if(id == R.id.action_logout)
            logout();

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        sessionManager.logoutUser();
        redirectToAuthenticationActivity();
    }
}
