package put.iwm.android.motionrecorder.application;

import android.app.Application;

import put.iwm.android.motionrecorder.di.MotionRecorderComponent;
import put.iwm.android.motionrecorder.training.TrainingTimer;
import put.iwm.android.motionrecorder.training.TrainingTimerImpl;

/**
 * Created by Szymon on 2015-05-05.
 */
public class MotionRecorderApplication extends Application {

    private static MotionRecorderComponent component;

    private TrainingTimer trainingTimer = new TrainingTimerImpl(null);

    @Override
    public void onCreate() {
        super.onCreate();
        component = MotionRecorderComponent.Initializer.init(this);
    }

    public static MotionRecorderComponent component() {
        return component;
    }

    public TrainingTimer getTrainingTimer() {
        return trainingTimer;
    }
}
