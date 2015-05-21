package put.iwm.android.motionrecorder.di;

import put.iwm.android.motionrecorder.fragments.RouteMapFragment;
import put.iwm.android.motionrecorder.fragments.ActiveTrainingFragment;
import put.iwm.android.motionrecorder.fragments.TrainingListFragment;
import put.iwm.android.motionrecorder.services.LocationListenerService;

/**
 * Created by Szymon on 2015-05-06.
 */
public interface ApplicationDependencyGraph {

    public void inject(ActiveTrainingFragment fragment);
    public void inject(RouteMapFragment fragment);
    public void inject(TrainingListFragment fragment);
    public void inject(LocationListenerService service);
}
