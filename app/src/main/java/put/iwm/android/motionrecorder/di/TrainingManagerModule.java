package put.iwm.android.motionrecorder.di;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import put.iwm.android.motionrecorder.database.repository.TrainingRepository;
import put.iwm.android.motionrecorder.services.ServiceManager;
import put.iwm.android.motionrecorder.contracts.LocationObserver;
import put.iwm.android.motionrecorder.training.TrainingManager;
import put.iwm.android.motionrecorder.training.TrainingManagerImpl;
import put.iwm.android.motionrecorder.training.TrainingTimer;
import put.iwm.android.motionrecorder.training.TrainingTimerImpl;

/**
 * Created by Szymon on 2015-05-07.
 */
@Module(includes = {ApplicationModule.class, ServiceManagerModule.class, RepositoryModule.class})
public class TrainingManagerModule {

    @Provides
    @Singleton
    @Named("TrainingManagerImpl")
    public TrainingManagerImpl provideTrainingManagerImpl(ServiceManager serviceManager, TrainingTimer trainingTimer, TrainingRepository trainingRepository) {
        return new TrainingManagerImpl(serviceManager, trainingTimer, trainingRepository);
    }

    @Provides
    @Singleton
    public TrainingManager provideTrainingManager(@Named("TrainingManagerImpl") TrainingManagerImpl trainingManagerImpl) {
        return trainingManagerImpl;
    }

    @Provides
    @Singleton
    public LocationObserver provideLocationObserver(@Named("TrainingManagerImpl") TrainingManagerImpl trainingManagerImpl) {
        return trainingManagerImpl;
    }

    @Provides
    @Singleton
    public TrainingTimer provideTrainingTimer() {
        return new TrainingTimerImpl();
    }
}
