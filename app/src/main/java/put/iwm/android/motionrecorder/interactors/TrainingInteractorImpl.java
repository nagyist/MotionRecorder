package put.iwm.android.motionrecorder.interactors;

import android.os.Handler;

import java.util.HashMap;
import java.util.Map;

import put.iwm.android.motionrecorder.database.repository.TrainingRepository;
import put.iwm.android.motionrecorder.training.Training;

/**
 * Created by Szymon on 2015-05-27.
 */
public class TrainingInteractorImpl implements TrainingInteractor {

    private Training training;
    private TrainingRepository trainingRepository;

    public TrainingInteractorImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public void getTrainingStatsData(final long trainingId, final OnGetTrainingStatsDataFinishedListener listener) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                getTrainingStatsDataInBackground(trainingId, listener);
            }
        });
    }

    private void getTrainingStatsDataInBackground(long trainingId, OnGetTrainingStatsDataFinishedListener listener) {

        training = trainingRepository.findById(trainingId);

        Map<String, Object> model = new HashMap<>();
        model.put("startDate", training.getStartDate());
        model.put("finishDate", training.getFinishDate());
        model.put("duration", training.getDurationTime());
        model.put("distance", training.getTotalDistance());
        model.put("maxSpeed", training.getMaxSpeed());
        model.put("avgSpeed", training.getAvgSpeed());

        listener.onTrainingStatsDataReady(model);
    }

    @Override
    public void deleteTraining(final long trainingId, final OnDeleteTrainingFinishedListener listener) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                deleteTrainingInBackground(trainingId, listener);
            }
        });
    }

    private void deleteTrainingInBackground(long trainingId, OnDeleteTrainingFinishedListener listener) {
        trainingRepository.deleteById(trainingId);
        listener.onDeleteTrainingSuccess();
    }
}
