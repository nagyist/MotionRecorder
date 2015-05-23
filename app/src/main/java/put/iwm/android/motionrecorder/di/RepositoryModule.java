package put.iwm.android.motionrecorder.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import put.iwm.android.motionrecorder.database.repository.ActiveAndroidTrainingRepository;
import put.iwm.android.motionrecorder.database.repository.TrainingRepository;

/**
 * Created by Szymon on 2015-05-16.
 */
@Module
public class RepositoryModule {

    @Provides
    @Singleton
    public TrainingRepository provideTrainingRepository() {
        return new ActiveAndroidTrainingRepository();
    }
}
