package put.iwm.android.motionrecorder.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import put.iwm.android.motionrecorder.training.TrainingManager;
import put.iwm.android.motionrecorder.training.TrainingManagerImpl;
import put.iwm.android.motionrecorder.training.TrainingTimer;
import put.iwm.android.motionrecorder.training.TrainingTimerImpl;

/**
 * Created by Szymon on 2015-05-07.
 */
@Module(includes = {ApplicationModule.class})
public class TrainingManagerModule {

    @Provides
    @Singleton
    public TrainingManager provideTrainingManager(Context context, TrainingTimer trainingTimer) {
        return new TrainingManagerImpl(context, trainingTimer);
    }

    @Provides
    @Singleton
    public TrainingTimer provideTrainingTimer() {
        return new TrainingTimerImpl(null);
    }
}
