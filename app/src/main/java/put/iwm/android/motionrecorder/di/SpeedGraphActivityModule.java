package put.iwm.android.motionrecorder.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import put.iwm.android.motionrecorder.activities.SpeedGraphActivity;
import put.iwm.android.motionrecorder.views.SpeedGraphView;

/**
 * Created by Szymon on 2015-05-26.
 */
@Module
public class SpeedGraphActivityModule {

    private SpeedGraphActivity activity;

    public SpeedGraphActivityModule(SpeedGraphActivity activity) {
        this.activity = activity;
    }

    @Provides
    @Singleton
    public Context provideSpeedGraphActivityContext() {
        return activity;
    }

    @Provides
    @Singleton
    public SpeedGraphView provideSpeedGraphView() {
        return activity;
    }
}
