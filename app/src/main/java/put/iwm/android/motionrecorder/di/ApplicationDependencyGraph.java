package put.iwm.android.motionrecorder.di;

import android.app.Activity;
import android.app.Fragment;

import put.iwm.android.motionrecorder.activities.AuthenticationActivity;
import put.iwm.android.motionrecorder.fragments.RouteMapFragment;
import put.iwm.android.motionrecorder.fragments.StartTrainingFragment;
import put.iwm.android.motionrecorder.services.LocationListenerService;

/**
 * Created by Szymon on 2015-05-06.
 */
public interface ApplicationDependencyGraph {

    public void inject(StartTrainingFragment fragment);
    public void inject(RouteMapFragment fragment);
    public void inject(LocationListenerService service);
}
