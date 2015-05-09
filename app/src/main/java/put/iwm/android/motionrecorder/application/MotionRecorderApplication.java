package put.iwm.android.motionrecorder.application;

import android.app.Application;

import put.iwm.android.motionrecorder.di.ApplicationComponent;

/**
 * Created by Szymon on 2015-05-05.
 */
public class MotionRecorderApplication extends Application {

    private static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = ApplicationComponent.Initializer.init(this);
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
