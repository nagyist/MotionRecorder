package put.iwm.android.motionrecorder.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import put.iwm.android.motionrecorder.activities.TrainingStatsActivity;
import put.iwm.android.motionrecorder.views.SpeedGraphView;
import put.iwm.android.motionrecorder.views.TrainingStatsView;

/**
 * Created by Szymon on 2015-05-27.
 */
@Module
public class TrainingStatsActivityModule {

    private TrainingStatsActivity activity;

    public TrainingStatsActivityModule(TrainingStatsActivity activity) {
        this.activity = activity;
    }

    @Provides
    @Singleton
    public TrainingStatsView provideTrainingStatsView() {
        return activity;
    }

}
