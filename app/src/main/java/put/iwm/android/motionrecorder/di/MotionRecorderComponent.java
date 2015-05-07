package put.iwm.android.motionrecorder.di;

import javax.inject.Singleton;

import dagger.Component;
import put.iwm.android.motionrecorder.application.MotionRecorderApplication;

/**
 * Created by Szymon on 2015-05-06.
 */
@Singleton
@Component(modules = {TimerTextGeneratorModule.class})
public interface MotionRecorderComponent extends FragmentComponent {

    static final class Initializer {

        private Initializer() {

        }

        public static MotionRecorderComponent init(MotionRecorderApplication app) {
            return DaggerMotionRecorderComponent.builder().timerTextGeneratorModule(new TimerTextGeneratorModule()).build();
        }

    }

}
