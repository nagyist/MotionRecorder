package put.iwm.android.motionrecorder.interactors;

/**
 * Created by Szymon on 2015-05-27.
 */
public interface TrainingInteractor {
    public void getTrainingStatsData(long trainingId, OnGetTrainingStatsDataFinishedListener listener);
    public void deleteTraining(long trainingId, OnDeleteTrainingFinishedListener listener);
}
