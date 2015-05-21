package put.iwm.android.motionrecorder.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import put.iwm.android.motionrecorder.R;
import put.iwm.android.motionrecorder.application.MotionRecorderApplication;
import put.iwm.android.motionrecorder.contracts.RouteObserver;
import put.iwm.android.motionrecorder.training.RoutePoint;
import put.iwm.android.motionrecorder.training.TrainingManager;

public class RouteMapFragment extends Fragment implements RouteObserver {

    private static final String TAG = RouteMapFragment.class.toString();
    private OnFragmentInteractionListener mListener;
    private View rootView;
    private GoogleMap map;
    private MapView mapView;
    @Inject TrainingManager trainingManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        MotionRecorderApplication.getApplicationComponent().inject(this);

        trainingManager.setRouteObserver(this);

        MapsInitializer.initialize(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_route_map, container, false);

        setupMapView(savedInstanceState);

        trainingManager.requestRouteUpdate();

        return rootView;
    }

    private void setupMapView(Bundle savedInstanceState) {

        if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()) == ConnectionResult.SUCCESS) {

            mapView = (MapView) rootView.findViewById(R.id.map);
            mapView.onCreate(savedInstanceState);
            mapView.onResume();

            if(mapView != null)
                setupMap();
            else
                Log.i(TAG, "MAPVIEW_NULL");
        }

        Log.i(TAG, String.valueOf(GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity())));
    }

    private void setupMap() {

        map = mapView.getMap();
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(false);
        map.getUiSettings().setMyLocationButtonEnabled(true);
    }

    @Override
    public void processRouteUpdate(List<RoutePoint> routePoints) {

        if(!routePoints.isEmpty()) {

            List<LatLng> coordinates = new LinkedList<>();
            for (RoutePoint routePoint : routePoints)
                coordinates.add(new LatLng(routePoint.getLatitude(), routePoint.getLongitude()));

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(coordinates.get(coordinates.size() - 1), 16.2f);
            map.animateCamera(cameraUpdate);

            PolylineOptions polyline = new PolylineOptions();
            polyline.addAll(coordinates);
            polyline.width(4.2f);
            polyline.color(Color.rgb(0x42, 0x85, 0xF2));
            polyline.geodesic(true);

            map.addPolyline(polyline);
        }
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
