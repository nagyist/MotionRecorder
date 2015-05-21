package put.iwm.android.motionrecorder.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import put.iwm.android.motionrecorder.R;
import put.iwm.android.motionrecorder.adapters.TrainingAdapter;
import put.iwm.android.motionrecorder.application.MotionRecorderApplication;
import put.iwm.android.motionrecorder.database.repository.TrainingRepository;
import put.iwm.android.motionrecorder.training.Training;

/**
 * Created by Szymon on 2015-05-20.
 */
public class TrainingListFragment extends Fragment {

    private static final String TAG = TrainingListFragment.class.toString();
    private View rootView;
    private ListView trainingList;
    private TrainingAdapter trainingAdapter;
    @Inject TrainingRepository trainingRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        MotionRecorderApplication.getApplicationComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        rootView = inflater.inflate(R.layout.fragment_training_list, container, false);

        setupUIReferences();
        setupEventHandlers();

        List<Training> trainings = trainingRepository.findAll();    //TODO
        trainingAdapter = new TrainingAdapter(getActivity(), trainings);
        trainingList.setAdapter(trainingAdapter);

        return rootView;
    }

    private void setupUIReferences() {
        trainingList = (ListView) rootView.findViewById(R.id.training_list);
    }

    private void setupEventHandlers() {

        trainingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, String.valueOf(id));
            }
        });
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
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
