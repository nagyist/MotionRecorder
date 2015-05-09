package put.iwm.android.motionrecorder.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import put.iwm.android.motionrecorder.application.MotionRecorderApplication;

/**
 * Created by Szymon on 2015-05-07.
 */
@Module
public class ApplicationModule {

    private MotionRecorderApplication application;

    public ApplicationModule(MotionRecorderApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return application;
    }
}
