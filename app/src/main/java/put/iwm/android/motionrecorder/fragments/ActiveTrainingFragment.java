package put.iwm.android.motionrecorder.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
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
import put.iwm.android.motionrecorder.presenters.ActiveTrainingPresenter;
import put.iwm.android.motionrecorder.views.ActiveTrainingView;

public class ActiveTrainingFragment extends Fragment implements ActiveTrainingView, ConfirmDialogFragment.NoticeDialogListener {

    private static final String TAG = ActiveTrainingFragment.class.toString();
    private View rootView;
    private TextView trainingTimerTextView;
    private TextView trainingDistanceTextView;
    private TextView trainingSpeedTextView;
    private Button startTrainingButton;
    private Button finishTrainingButton;
    private Button resumeTrainingButton;
    private Button pauseTrainingButton;
    private ConfirmDialogFragment confirmDialog;
    @Inject ActiveTrainingPresenter activeTrainingPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MotionRecorderApplication.getApplicationComponent().inject(this);
        activeTrainingPresenter.setView(this);

        rootView = inflater.inflate(R.layout.fragment_active_training, container, false);

        setupUIReferences();
        setupEventHandlers();
        updateUI();

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        activeTrainingPresenter.onResume();
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
                finishTrainingButtonClicked();
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

        activeTrainingPresenter.startTraining();
        resetUIFields();
        updateUI();
    }

    private void resetUIFields() {

        trainingTimerTextView.setText(R.string.timer_init_value);
        trainingDistanceTextView.setText(R.string.distance_init_value);
        trainingSpeedTextView.setText(R.string.speed_init_value);
    }

    private void finishTrainingButtonClicked() {
        confirmDialog = new ConfirmDialogFragment("Czy chcesz zachować zakończony trening?", "Tak", "Nie", this);
        confirmDialog.show(getFragmentManager(), "Save training confirm dialog");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        finishTraining(true);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        finishTraining(false);
    }

    private void finishTraining(boolean saveTraining) {
        activeTrainingPresenter.finishTraining(saveTraining);
        updateUI();
    }

    private void resumeTrainingButtonClicked() {
        activeTrainingPresenter.resumeTraining();
        updateUI();
    }

    private void pauseTrainingButtonClicked() {
        activeTrainingPresenter.pauseTraining();
        updateUI();
    }

    private void updateUI() {

        int[] buttonsStates;

        if(activeTrainingPresenter.isTrainingInProgress())
            if (activeTrainingPresenter.isTrainingPaused())
                buttonsStates = new int[] { View.GONE, View.VISIBLE, View.VISIBLE, View.GONE };
            else
                buttonsStates = new int[] { View.GONE, View.VISIBLE, View.GONE, View.VISIBLE };
        else
            buttonsStates = new int[] { View.VISIBLE, View.GONE, View.GONE, View.GONE };

        startTrainingButton.setVisibility(buttonsStates[0]);
        finishTrainingButton.setVisibility(buttonsStates[1]);
        resumeTrainingButton.setVisibility(buttonsStates[2]);
        pauseTrainingButton.setVisibility(buttonsStates[3]);
    }

    @Override
    public void setTrainingTimerText(String time) {
        trainingTimerTextView.setText(time);
    }

    @Override
    public void setTrainingData(Map<String, Object> model) {

        String speedText = (String) model.get("speed");
        trainingSpeedTextView.setText(speedText);

        String distanceText = (String) model.get("distance");
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
