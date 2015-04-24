package put.iwm.android.motionrecorder.domain;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Szymon on 2015-04-24.
 */
public class TrainingManagerImpl implements TrainingManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private Context applicationContext;

    private static final int PRIVATE_MODE = 0;
    private static final String PREFERENCES_NAME = "MotionRecorder";
    private static final String TRAINING = "is_training_in_progress";

    public TrainingManagerImpl(Context context) {

        applicationContext = context;
        sharedPreferences = applicationContext.getSharedPreferences(PREFERENCES_NAME, PRIVATE_MODE);
        sharedPreferencesEditor = sharedPreferences.edit();
    }

    public void startTraining() {

        sharedPreferencesEditor.putBoolean(TRAINING, true);
        sharedPreferencesEditor.commit();
    }

    public void finishTraining() {

        sharedPreferencesEditor.putBoolean(TRAINING, false);
        sharedPreferencesEditor.commit();
    }

    public boolean isTrainingInProgress() {

        return sharedPreferences.getBoolean(TRAINING, false);
    }

}
