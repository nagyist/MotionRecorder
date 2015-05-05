package put.iwm.android.motionrecorder.training;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Szymon on 2015-04-24.
 */
public class TrainingManagerImpl implements TrainingManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private Context context;
    private TrainingTimer trainingTimer;

    private static final int PRIVATE_MODE = 0;
    private static final String PREFERENCES_NAME = "MotionRecorder";
    private static final String RUNNING = "is_training_in_progress";
    private static final String PAUSED = "is_training_paused";

    public TrainingManagerImpl(Context context, TrainingTimer trainingTimer) {

        this.context = context;
        this.trainingTimer = trainingTimer;
        sharedPreferences = this.context.getSharedPreferences(PREFERENCES_NAME, PRIVATE_MODE);
        sharedPreferencesEditor = sharedPreferences.edit();
    }

    public void startTraining() {

        sharedPreferencesEditor.putBoolean(RUNNING, true);
        sharedPreferencesEditor.putBoolean(PAUSED, false);
        sharedPreferencesEditor.commit();

        trainingTimer.start();
    }

    @Override
    public void pauseTraining() {

        sharedPreferencesEditor.putBoolean(PAUSED, true);
        sharedPreferencesEditor.commit();

        trainingTimer.pause();
    }

    @Override
    public void resumeTraining() {

        sharedPreferencesEditor.putBoolean(PAUSED, false);
        sharedPreferencesEditor.commit();

        trainingTimer.resume();
    }

    public void finishTraining() {

        sharedPreferencesEditor.putBoolean(RUNNING, false);
        sharedPreferencesEditor.putBoolean(PAUSED, false);
        sharedPreferencesEditor.commit();

        trainingTimer.stop();
    }

    public boolean isTrainingInProgress() {

        return sharedPreferences.getBoolean(RUNNING, false);
    }

    @Override
    public boolean isTrainingPaused() {
        return sharedPreferences.getBoolean(PAUSED, false);
    }

}
