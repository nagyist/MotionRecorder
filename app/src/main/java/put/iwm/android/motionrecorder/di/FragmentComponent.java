package put.iwm.android.motionrecorder.di;

import javax.inject.Singleton;

import dagger.Component;
import put.iwm.android.motionrecorder.fragments.StartTrainingFragment;
import put.iwm.android.motionrecorder.fragments.TimerTextGenerator;

/**
 * Created by Szymon on 2015-05-06.
 */
public interface FragmentComponent {

    //public TimerTextGenerator provideTimerTextGenerator();
    public void inject(StartTrainingFragment fragment);
}
