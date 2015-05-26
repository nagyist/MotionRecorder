package put.iwm.android.motionrecorder.interactors;

import javax.inject.Inject;

import put.iwm.android.motionrecorder.database.repository.TrainingRepository;

/**
 * Created by Szymon on 2015-05-26.
 */
public class SpeedGraphInteractorImpl implements SpeedGraphInteractor {


    private TrainingRepository trainingRepository;

    public SpeedGraphInteractorImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public void getTrainingSpeedData(int trainingId, OnGetTrainingSpeedDataFinishedListener listener) {

    }
}
