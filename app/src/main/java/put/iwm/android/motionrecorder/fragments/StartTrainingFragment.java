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

import put.iwm.android.motionrecorder.R;
import put.iwm.android.motionrecorder.domain.TrainingManager;
import put.iwm.android.motionrecorder.domain.TrainingManagerImpl;
import put.iwm.android.motionrecorder.services.LocationListenerService;

public class StartTrainingFragment extends Fragment {

    private static final String TAG = StartTrainingFragment.class.toString();
    private OnFragmentInteractionListener mListener;
    private View rootView;
    private Button startTrainingButton;
    private Button finishTrainingButton;

    private Intent locationServiceIntent;
    private TrainingManager trainingManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_start_training, container, false);
        locationServiceIntent = new Intent(getActivity(), LocationListenerService.class);
        trainingManager = new TrainingManagerImpl(getActivity());
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

        startTrainingButton = (Button) rootView.findViewById(R.id.start_training_button);
        finishTrainingButton = (Button) rootView.findViewById(R.id.finish_training_button);
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
    }

    private void updateUI() {

        if(trainingManager.isTrainingInProgress()) {
            startTrainingButton.setVisibility(View.GONE);
            finishTrainingButton.setVisibility(View.VISIBLE);
        } else {
            startTrainingButton.setVisibility(View.VISIBLE);
            finishTrainingButton.setVisibility(View.GONE);
        }
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
