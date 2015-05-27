package put.iwm.android.motionrecorder.interactors;

/**
 * Created by Szymon on 2015-05-26.
 */
public interface SpeedGraphInteractor {

    public void getTrainingSpeedData(long trainingId, OnGetTrainingSpeedDataFinishedListener listener);
}
