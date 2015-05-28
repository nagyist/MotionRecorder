package put.iwm.android.motionrecorder.di;

import javax.inject.Singleton;

import dagger.Component;
import put.iwm.android.motionrecorder.activities.SpeedGraphActivity;

/**
 * Created by Szymon on 2015-05-26.
 */
@Singleton
@Component(modules = {SpeedGraphActivityModule.class, SpeedGraphPresenterModule.class})
public interface SpeedGraphActivityComponent extends SpeedGraphActivityDependencyGraph {

    static final class Initializer {

        private Initializer() {
        }

        public static SpeedGraphActivityComponent init(SpeedGraphActivity activity) {
            return DaggerSpeedGraphActivityComponent.builder()
                    .speedGraphActivityModule(new SpeedGraphActivityModule(activity))
                    .build();
        }
    }
}
