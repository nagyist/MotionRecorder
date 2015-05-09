package put.iwm.android.motionrecorder.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import put.iwm.android.motionrecorder.asynctasks.LoginRequestAsyncTask;
import put.iwm.android.motionrecorder.asynctasks.NetworkConnection;
import put.iwm.android.motionrecorder.asynctasks.NetworkConnectionImpl;

/**
 * Created by Szymon on 2015-05-08.
 */
@Module(includes = {ActivityModule.class})
public class AsyncTaskModule {

    @Provides
    @Singleton
    public LoginRequestAsyncTask provideLoginRequestAsyncTask(Context context) {
        return new LoginRequestAsyncTask(context);
    }
}
