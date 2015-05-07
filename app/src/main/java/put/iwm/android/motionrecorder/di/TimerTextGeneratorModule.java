package put.iwm.android.motionrecorder.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import put.iwm.android.motionrecorder.application.MotionRecorderApplication;
import put.iwm.android.motionrecorder.fragments.TimerTextGenerator;

/**
 * Created by Szymon on 2015-05-06.
 */
@Module
public class TimerTextGeneratorModule {

    @Provides
    @Singleton
    TimerTextGenerator provideTimerTextGenerator() {

        return new TimerTextGenerator();
    }

}
