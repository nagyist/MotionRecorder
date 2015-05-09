package put.iwm.android.motionrecorder.fragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import put.iwm.android.motionrecorder.R;
import put.iwm.android.motionrecorder.application.MotionRecorderApplication;
import put.iwm.android.motionrecorder.database.LocationDatabaseAdapter;
import put.iwm.android.motionrecorder.database.models.RoutePoint;
import put.iwm.android.motionrecorder.training.TimerListener;
import put.iwm.android.motionrecorder.training.TrainingManager;
import put.iwm.android.motionrecorder.services.LocationListenerService;
import put.iwm.android.motionrecorder.training.TrainingTimer;

public class StartTrainingFragment extends Fragment implements TimerListener {

    private static final String TAG = StartTrainingFragment.class.toString();
    private OnFragmentInteractionListener mListener;
    private View rootView;
    private TextView trainingTimerTextView;
    private TextView trainingDistanceTextView;
    private TextView trainingSpeedTextView;
    private Button startTrainingButton;
    private Button finishTrainingButton;
    private Button resumeTrainingButton;
    private Button pauseTrainingButton;
    private Intent locationServiceIntent;
    private BroadcastReceiver locationBroadcastReceiver;
    private LocationDatabaseAdapter locationRepository;

    @Inject
    TrainingManager trainingManager;
    @Inject
    TextGenerator textGenerator;
    @Inject
    TrainingTimer trainingTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MotionRecorderApplication.getApplicationComponent().inject(this);

        rootView = inflater.inflate(R.layout.fragment_start_training, container, false);
        locationServiceIntent = new Intent(getActivity(), LocationListenerService.class);
        locationRepository = new LocationDatabaseAdapter(getActivity());

        trainingTimer.setTimerListener(this);

        setupUIReferences();
        setupEventHandlers();
        setupBroadcastReceiver();
        updateUI();

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    private void setupUIReferences() {

        trainingTimerTextView = (TextView) rootView.findViewById(R.id.training_timer);
        trainingDistanceTextView = (TextView) rootView.findViewById(R.id.training_distance);
        trainingSpeedTextView = (TextView) rootView.findViewById(R.id.training_speed);
        startTrainingButton = (Button) rootView.findViewById(R.id.start_training_button);
        finishTrainingButton = (Button) rootView.findViewById(R.id.finish_training_button);
        resumeTrainingButton = (Button) rootView.findViewById(R.id.resume_training_button);
        pauseTrainingButton = (Button) rootView.findViewById(R.id.pause_training_button);
    }

    private void setupEventHandlers() {

        startTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Rozpoczęto trening!");
                startTrainingButtonClicked();
            }
        });

        finishTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Zakończono trening!");
                finishTrainingButton();
            }
        });

        resumeTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Wznowiono trening!");
                resumeTrainingButtonClicked();
            }
        });

        pauseTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Wstrzymano trening!");
                pauseTrainingButtonClicked();
            }
        });
    }

    private void startTrainingButtonClicked() {

        //TODO
        try {
            locationRepository.open();
            locationRepository.deleteLastLocations();
            locationRepository.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        getActivity().startService(locationServiceIntent);
        trainingManager.startTraining();
        updateUI();
    }

    private void finishTrainingButton() {

        getActivity().stopService(locationServiceIntent);
        trainingManager.finishTraining();
        updateUI();
    }

    private void resumeTrainingButtonClicked() {

        getActivity().startService(locationServiceIntent);
        trainingManager.resumeTraining();
        updateUI();
    }

    private void pauseTrainingButtonClicked() {

        getActivity().stopService(locationServiceIntent);
        trainingManager.pauseTraining();
        updateUI();
    }

    private void setupBroadcastReceiver() {

        locationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Log.i(TAG, "Otrzymano powiadomienie od usługi.");
                processLocationUpdate();
            }
        };

        IntentFilter intentFilter = new IntentFilter(LocationListenerService.ACTION);
        getActivity().registerReceiver(locationBroadcastReceiver, intentFilter);
    }

    private void updateUI() {

        if(trainingManager.isTrainingInProgress()) {
            if(trainingManager.isTrainingPaused()) {
                startTrainingButton.setVisibility(View.GONE);
                finishTrainingButton.setVisibility(View.VISIBLE);
                resumeTrainingButton.setVisibility(View.VISIBLE);
                pauseTrainingButton.setVisibility(View.GONE);
            } else {
                startTrainingButton.setVisibility(View.GONE);
                finishTrainingButton.setVisibility(View.VISIBLE);
                resumeTrainingButton.setVisibility(View.GONE);
                pauseTrainingButton.setVisibility(View.VISIBLE);
            }
        } else {
            startTrainingButton.setVisibility(View.VISIBLE);
            finishTrainingButton.setVisibility(View.GONE);
            resumeTrainingButton.setVisibility(View.GONE);
            pauseTrainingButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void processTimerUpdate(long time) {

        String timeText = textGenerator.createTimerText(time);
        trainingTimerTextView.setText(timeText);
    }

    private void processLocationUpdate() {

        try {
            tryProcessLocationUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void tryProcessLocationUpdate() throws SQLException {

        locationRepository.open();
        List<RoutePoint> lastLocations = locationRepository.getLastLocations();
        locationRepository.close();

        float totalDistance = calculateTotalDistance(lastLocations);
        String distanceText = textGenerator.createDistanceText(totalDistance);
        trainingDistanceTextView.setText(distanceText);

        float currentSpeed = calculateCurrentSpeed(lastLocations);
        String speedText = textGenerator.createSpeedText(currentSpeed);
        trainingSpeedTextView.setText(speedText);
    }

    private float calculateCurrentSpeed(List<RoutePoint> locations) {

        float currentSpeed = 0;

        if(locations.size() >= 2) {
            float partialDistance = calculateTotalDistance(locations.subList(0, 2));
            long moveTime = (locations.get(0).getMoveTime() - locations.get(1).getMoveTime()) / 1000;
            currentSpeed = partialDistance / (float) moveTime;
        }

        return currentSpeed;
    }

    private float calculateTotalDistance(List<RoutePoint> locations) {

        float totalDistance = 0;
        float []partialDistance = new float[3];

        if(locations.size() >= 2) {

            for(int i = 0; i + 1 < locations.size(); i++) {

                RoutePoint firstLocation = locations.get(i);
                RoutePoint secondLocation = locations.get(i + 1);

                Location.distanceBetween(firstLocation.getLatitude(), firstLocation.getLongitude(), secondLocation.getLatitude(), secondLocation.getLongitude(), partialDistance);

                totalDistance += partialDistance[0];
            }
        }

        return totalDistance;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(locationBroadcastReceiver);
    }

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
