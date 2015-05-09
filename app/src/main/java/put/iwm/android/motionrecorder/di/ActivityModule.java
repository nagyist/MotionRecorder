package put.iwm.android.motionrecorder.di;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import put.iwm.android.motionrecorder.base.BaseActivity;

/**
 * Created by Szymon on 2015-05-08.
 */
@Module
public class ActivityModule {

    private BaseActivity activity;

    public ActivityModule(BaseActivity activity) {
        this.activity = activity;
    }

    @Provides
    @Singleton
    public Context provideActivityContext() {
        return activity;
    }
}
