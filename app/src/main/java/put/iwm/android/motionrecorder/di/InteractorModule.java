package put.iwm.android.motionrecorder.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import put.iwm.android.motionrecorder.database.repository.TrainingRepository;
import put.iwm.android.motionrecorder.interactors.SpeedGraphInteractor;
import put.iwm.android.motionrecorder.interactors.SpeedGraphInteractorImpl;
import put.iwm.android.motionrecorder.interactors.TrainingInteractor;
import put.iwm.android.motionrecorder.interactors.TrainingInteractorImpl;

/**
 * Created by Szymon on 2015-05-26.
 */
@Module(includes = {RepositoryModule.class})
public class InteractorModule {

    @Provides
    @Singleton
    public SpeedGraphInteractor provideSpeedGraphInteractor(TrainingRepository repository) {
        return new SpeedGraphInteractorImpl(repository);
    }

    @Provides
    @Singleton
    public TrainingInteractor provideTrainingInteractor(TrainingRepository repository) {
        return new TrainingInteractorImpl(repository);
    }
}
