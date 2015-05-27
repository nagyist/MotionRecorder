package put.iwm.android.motionrecorder.interactors;

import android.os.Handler;

import java.util.HashMap;

import put.iwm.android.motionrecorder.database.repository.TrainingRepository;
import put.iwm.android.motionrecorder.training.Training;

/**
 * Created by Szymon on 2015-05-26.
 */
public class SpeedGraphInteractorImpl implements SpeedGraphInteractor {

    private Training training;
    private TrainingRepository trainingRepository;

    public SpeedGraphInteractorImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public void getTrainingSpeedData(final long trainingId, final OnGetTrainingSpeedDataFinishedListener listener) {

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                getTrainingSpeedDataInBackground(trainingId, listener);
            }
        });
    }

    public void getTrainingSpeedDataInBackground(long trainingId, OnGetTrainingSpeedDataFinishedListener listener) {

        training = trainingRepository.findById(trainingId);

        HashMap<String, Object> model = new HashMap<>();
        model.put("speedPoints", training.getSpeedPoints());
        model.put("maxSpeed", training.getMaxSpeed());
        model.put("avgSpeed", training.getAvgSpeed());

        listener.onTrainingSpeedDataReady(model);
    }

}
