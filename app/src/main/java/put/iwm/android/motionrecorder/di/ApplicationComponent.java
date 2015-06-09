package put.iwm.android.motionrecorder.di;

import javax.inject.Singleton;

import dagger.Component;
import put.iwm.android.motionrecorder.application.MotionRecorderApplication;

/**
 * Created by Szymon on 2015-05-06.
 */
@Singleton
@Component(modules = {ActiveTrainingPresenterModule.class, TextGeneratorModule.class, TrainingManagerModule.class, ApplicationModule.class})
public interface ApplicationComponent extends ApplicationDependencyGraph {

    static final class Initializer {

        private Initializer() {
        }

        public static ApplicationComponent init(MotionRecorderApplication app) {
            return DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(app))
                    .build();
        }

    }

}
