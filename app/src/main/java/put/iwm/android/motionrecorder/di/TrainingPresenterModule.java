package put.iwm.android.motionrecorder.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import put.iwm.android.motionrecorder.fragments.TextGenerator;
import put.iwm.android.motionrecorder.interactors.TrainingInteractor;
import put.iwm.android.motionrecorder.presenters.TrainingPresenter;
import put.iwm.android.motionrecorder.presenters.TrainingPresenterImpl;
import put.iwm.android.motionrecorder.views.TrainingStatsView;

/**
 * Created by Szymon on 2015-05-27.
 */
@Module(includes = {TrainingStatsActivityModule.class, InteractorModule.class, TextGeneratorModule.class})
public class TrainingPresenterModule {

    @Provides
    @Singleton
    public TrainingPresenter provideTrainingPresenter(TrainingStatsView view, TrainingInteractor interactor, TextGenerator textGenerator) {
        return new TrainingPresenterImpl(view, interactor, textGenerator);
    }
}
