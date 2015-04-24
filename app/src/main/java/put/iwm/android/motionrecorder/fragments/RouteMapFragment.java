package put.iwm.android.motionrecorder.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;

import java.sql.SQLException;

import put.iwm.android.motionrecorder.R;
import put.iwm.android.motionrecorder.database.LocationDatabaseAdapter;
import put.iwm.android.motionrecorder.services.LocationListenerService;

public class RouteMapFragment extends Fragment  {

    private static final String TAG = LocationListenerService.class.toString();
    private OnFragmentInteractionListener mListener;
    private View view;
    private GoogleMap map;
    private MapView mapView;

    private Intent locationServiceIntent;
    private BroadcastReceiver locationBroadcastReceiver;

    private LocationDatabaseAdapter locationRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        MapsInitializer.initialize(getActivity());

        locationServiceIntent = new Intent(getActivity(), LocationListenerService.class);

        locationRepository = new LocationDatabaseAdapter(getActivity());

        setupBroadcastReceiver();
    }

    private void setupBroadcastReceiver() {

        locationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                locationUpdateReceived(intent);
            }
        };

        IntentFilter intentFilter = new IntentFilter(LocationListenerService.ACTION);
        getActivity().registerReceiver(locationBroadcastReceiver, intentFilter);
    }

    private void locationUpdateReceived(Intent intent) {

        Log.i(TAG, "Otrzymano powiadomienie od usługi.");

        try {
            locationRepository.open();
            LatLng latitudeLongitude = locationRepository.getLastLocation();
            locationRepository.close();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latitudeLongitude, 30);
            map.animateCamera(cameraUpdate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_route_map, container, false);

        setupMap(savedInstanceState);

        return view;
    }

    private void setupMap(Bundle savedInstanceState) {

        switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity())) {
            case ConnectionResult.SUCCESS:
                mapView = (MapView) view.findViewById(R.id.map);
                mapView.onCreate(savedInstanceState);
                mapView.onResume();
                if(mapView != null) {
                    map = mapView.getMap();
                    map.setMyLocationEnabled(true);
                    map.getUiSettings().setMyLocationButtonEnabled(true);
                } else {
                    Toast.makeText(getActivity(), "MAPVIEW_NULL", Toast.LENGTH_SHORT).show();
                }
                break;
            case ConnectionResult.SERVICE_MISSING:
                Toast.makeText(getActivity(), "SERVICE MISSING", Toast.LENGTH_SHORT).show();
                break;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                Toast.makeText(getActivity(), "UPDATE REQUIRED", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getActivity(), GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onResume() {

        super.onResume();
        getActivity().startService(locationServiceIntent);
    }

    @Override
    public void onPause() {

        super.onPause();
        getActivity().stopService(locationServiceIntent);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(locationBroadcastReceiver);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //mListener = (OnFragmentInteractionListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}