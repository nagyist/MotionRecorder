package put.iwm.android.motionrecorder.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;

import javax.inject.Inject;

import put.iwm.android.motionrecorder.R;
import put.iwm.android.motionrecorder.application.MotionRecorderApplication;
import put.iwm.android.motionrecorder.contracts.TimerObserver;
import put.iwm.android.motionrecorder.training.TrainingManager;
import put.iwm.android.motionrecorder.contracts.TrainingObserver;
import put.iwm.android.motionrecorder.training.TrainingTimer;

public class StartTrainingFragment extends Fragment implements TimerObserver, TrainingObserver {

    private static final String TAG = StartTrainingFragment.class.toString();
    private View rootView;
    private TextView trainingTimerTextView;
    private TextView trainingDistanceTextView;
    private TextView trainingSpeedTextView;
    private Button startTrainingButton;
    private Button finishTrainingButton;
    private Button resumeTrainingButton;
    private Button pauseTrainingButton;

    @Inject TextGenerator textGenerator;
    @Inject TrainingManager trainingManager;
    @Inject TrainingTimer trainingTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MotionRecorderApplication.getApplicationComponent().inject(this);

        rootView = inflater.inflate(R.layout.fragment_start_training, container, false);

        trainingTimer.setTimerObserver(this);
        trainingManager.setTrainingObserver(this);

        setupUIReferences();
        setupEventHandlers();
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

        trainingManager.startTraining();
        resetUIFields();
        updateUI();
    }

    private void resetUIFields() {

        trainingTimerTextView.setText(R.string.timer_init_value);
        trainingDistanceTextView.setText(R.string.distance_init_value);
        trainingSpeedTextView.setText(R.string.speed_init_value);
    }

    private void finishTrainingButton() {

        trainingManager.finishTraining();
        updateUI();
    }

    private void resumeTrainingButtonClicked() {

        trainingManager.resumeTraining();
        updateUI();
    }

    private void pauseTrainingButtonClicked() {

        trainingManager.pauseTraining();
        updateUI();
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

    @Override
    public void processTrainingUpdate(Map<String, String> responseModel) {

        String speedText = textGenerator.createSpeedText(Float.valueOf(responseModel.get("speed")));
        trainingSpeedTextView.setText(speedText);

        String distanceText = textGenerator.createDistanceText(Float.valueOf(responseModel.get("distance")));
        trainingDistanceTextView.setText(distanceText);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
