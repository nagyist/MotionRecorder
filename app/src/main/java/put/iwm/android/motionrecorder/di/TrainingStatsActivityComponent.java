package put.iwm.android.motionrecorder.di;

import javax.inject.Singleton;

import dagger.Component;
import put.iwm.android.motionrecorder.activities.TrainingStatsActivity;

/**
 * Created by Szymon on 2015-05-27.
 */
@Singleton
@Component(modules = {TrainingStatsActivityModule.class, TrainingPresenterModule.class})
public interface TrainingStatsActivityComponent extends TrainingStatsActivityDependencyGraph {

    static final class Initializer {

        private Initializer() {
        }

        public static TrainingStatsActivityComponent init(TrainingStatsActivity activity) {
            return DaggerTrainingStatsActivityComponent.builder()
                    .trainingStatsActivityModule(new TrainingStatsActivityModule(activity))
                    .build();
        }
    }

}
