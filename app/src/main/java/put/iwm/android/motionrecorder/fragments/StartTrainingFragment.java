package put.iwm.android.motionrecorder.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import put.iwm.android.motionrecorder.R;
import put.iwm.android.motionrecorder.application.MotionRecorderApplication;
import put.iwm.android.motionrecorder.training.TimerListener;
import put.iwm.android.motionrecorder.training.TrainingManager;
import put.iwm.android.motionrecorder.training.TrainingManagerImpl;
import put.iwm.android.motionrecorder.services.LocationListenerService;
import put.iwm.android.motionrecorder.training.TrainingTimer;

public class StartTrainingFragment extends Fragment implements TimerListener {

    private static final String TAG = StartTrainingFragment.class.toString();
    private OnFragmentInteractionListener mListener;
    private View rootView;
    private TextView trainingTimerTextView;
    private Button startTrainingButton;
    private Button finishTrainingButton;
    private Button resumeTrainingButton;
    private Button pauseTrainingButton;

    private Intent locationServiceIntent;
    private TrainingManager trainingManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG, "Tworzymy fragment");

        rootView = inflater.inflate(R.layout.fragment_start_training, container, false);
        locationServiceIntent = new Intent(getActivity(), LocationListenerService.class);

        MotionRecorderApplication app = (MotionRecorderApplication) getActivity().getApplication();
        TrainingTimer trainingTimer = app.getTrainingTimer();
        trainingTimer.setTimerListener(this);

        trainingManager = new TrainingManagerImpl(getActivity(), trainingTimer);
        setupUIReferences();
        setupEventHandlers();
        updateUI();

        processTimerUpdate(trainingTimer.getDurationTime());

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    private void setupUIReferences() {

        trainingTimerTextView = (TextView) rootView.findViewById(R.id.training_timer);
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
                getActivity().startService(locationServiceIntent);
                trainingManager.startTraining();
                updateUI();
            }
        });

        finishTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Zakończono trening!");
                getActivity().stopService(locationServiceIntent);
                trainingManager.finishTraining();
                updateUI();
            }
        });

        resumeTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Wznowiono trening!");
                getActivity().startService(locationServiceIntent);
                trainingManager.resumeTraining();
                updateUI();
            }
        });

        pauseTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Wstrzymano trening!");
                getActivity().stopService(locationServiceIntent);
                trainingManager.pauseTraining();
                updateUI();
            }
        });
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

        long hours = extractHours(time);
        long minutes = extractMinutes(time);
        long seconds = extractSeconds(time);

        String timeString = String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);

        trainingTimerTextView.setText(timeString);
    }

    private long extractHours(long time) {
        return time / 1000 / 60 % 60;
    }

    private long extractMinutes(long time) {
        return time / 1000 / 60;
    }

    private long extractSeconds(long time) {
        return (time / 1000) % 60;
    }

    private long extractMilliseconds(long time) {
        return time % 1000;
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
