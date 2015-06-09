package put.iwm.android.motionrecorder.presenters;

import put.iwm.android.motionrecorder.views.ActiveTrainingView;

/**
 * Created by Szymon on 2015-06-09.
 */
public interface ActiveTrainingPresenter {

    public void onResume();
    public void startTraining();
    public void finishTraining(boolean saveTraining);
    public void resumeTraining();
    public void pauseTraining();
    public boolean isTrainingInProgress();
    public boolean isTrainingPaused();
    public void setView(ActiveTrainingView view);
}
