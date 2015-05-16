package put.iwm.android.motionrecorder.training;

import put.iwm.android.motionrecorder.contracts.RouteObserver;
import put.iwm.android.motionrecorder.contracts.TrainingObserver;

/**
 * Created by Szymon on 2015-04-24.
 */
public interface TrainingManager {

    public void startTraining();
    public void pauseTraining();
    public void resumeTraining();
    public void finishTraining();
    public boolean isTrainingInProgress();
    public boolean isTrainingPaused();
    public void setTrainingObserver(TrainingObserver trainingObserver);
    public void setRouteObserver(RouteObserver routeObserver);
}
