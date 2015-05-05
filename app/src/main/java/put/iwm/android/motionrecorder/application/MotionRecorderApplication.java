package put.iwm.android.motionrecorder.application;

import android.app.Application;

import put.iwm.android.motionrecorder.training.TrainingTimer;
import put.iwm.android.motionrecorder.training.TrainingTimerImpl;

/**
 * Created by Szymon on 2015-05-05.
 */
public class MotionRecorderApplication extends Application {

    private TrainingTimer trainingTimer = new TrainingTimerImpl(null);

    public TrainingTimer getTrainingTimer() {
        return trainingTimer;
    }
}
