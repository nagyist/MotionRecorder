package put.iwm.android.motionrecorder.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import put.iwm.android.motionrecorder.services.ServiceManager;
import put.iwm.android.motionrecorder.services.ServiceManagerImpl;

/**
 * Created by Szymon on 2015-05-14.
 */
@Module(includes = {ApplicationModule.class})
public class ServiceManagerModule {

    @Provides
    @Singleton
    public ServiceManager provideServiceManager(Context context) {
        return new ServiceManagerImpl(context);
    }
}
