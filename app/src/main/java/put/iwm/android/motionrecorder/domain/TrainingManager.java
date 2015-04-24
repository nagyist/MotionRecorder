package put.iwm.android.motionrecorder.domain;

/**
 * Created by Szymon on 2015-04-24.
 */
public interface TrainingManager {

    public void startTraining();
    public void finishTraining();
    public boolean isTrainingInProgress();
}
