package put.iwm.android.motionrecorder.presenters;

/**
 * Created by Szymon on 2015-05-27.
 */
public interface TrainingPresenter {
    public void onResume(long trainingId);
    public void deleteTraining(long trainingId);
}
