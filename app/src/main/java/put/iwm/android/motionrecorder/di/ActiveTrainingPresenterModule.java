package put.iwm.android.motionrecorder.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import put.iwm.android.motionrecorder.fragments.TextGenerator;
import put.iwm.android.motionrecorder.presenters.ActiveTrainingPresenter;
import put.iwm.android.motionrecorder.presenters.ActiveTrainingPresenterImpl;
import put.iwm.android.motionrecorder.training.TrainingManager;
import put.iwm.android.motionrecorder.training.TrainingTimer;
import put.iwm.android.motionrecorder.views.ActiveTrainingView;

/**
 * Created by Szymon on 2015-06-09.
 */
@Module(includes = {TextGeneratorModule.class, TrainingManagerModule.class})
public class ActiveTrainingPresenterModule {

    @Provides
    @PerActivity
    public ActiveTrainingPresenter provideActiveTrainingPresenter(TextGenerator textGenerator,
                                                                  TrainingManager trainingManager, TrainingTimer trainingTimer) {
        return new ActiveTrainingPresenterImpl(textGenerator, trainingManager, trainingTimer);
    }

}
