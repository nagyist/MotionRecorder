package put.iwm.android.motionrecorder.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import put.iwm.android.motionrecorder.database.repository.RealmTrainingRepository;
import put.iwm.android.motionrecorder.database.repository.TrainingRepository;

/**
 * Created by Szymon on 2015-05-16.
 */
@Module(includes = {ApplicationModule.class})
public class RepositoryModule {

    @Provides
    @Singleton
    public Realm provideRealm(Context context) {
        return Realm.getInstance(context);
    }

    @Provides
    @Singleton
    public TrainingRepository provideTrainingRepository(Realm realmInstance) {
        return new RealmTrainingRepository(realmInstance);
    }

}
