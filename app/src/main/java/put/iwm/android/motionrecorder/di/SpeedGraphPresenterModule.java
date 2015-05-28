package put.iwm.android.motionrecorder.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import put.iwm.android.motionrecorder.interactors.SpeedGraphInteractor;
import put.iwm.android.motionrecorder.presenters.SpeedGraphPresenter;
import put.iwm.android.motionrecorder.presenters.SpeedGraphPresenterImpl;
import put.iwm.android.motionrecorder.views.SpeedGraphView;

/**
 * Created by Szymon on 2015-05-26.
 */
@Module(includes = {SpeedGraphActivityModule.class, InteractorModule.class})
public class SpeedGraphPresenterModule {

    @Provides
    @Singleton
    public SpeedGraphPresenter provideSpeedGraphPresenter(SpeedGraphView view, SpeedGraphInteractor interactor) {
        return new SpeedGraphPresenterImpl(view, interactor);
    }
}
