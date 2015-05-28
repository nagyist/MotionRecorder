package put.iwm.android.motionrecorder.di;

import put.iwm.android.motionrecorder.activities.AuthenticationActivity;
import put.iwm.android.motionrecorder.activities.RegisterActivity;
import put.iwm.android.motionrecorder.activities.RouteMapActivity;
import put.iwm.android.motionrecorder.activities.SpeedGraphActivity;
import put.iwm.android.motionrecorder.activities.TrainingStatsActivity;

/**
 * Created by Szymon on 2015-05-09.
 */
public interface ActivityDependencyGraph {

    public void inject(AuthenticationActivity activity);
    public void inject(RegisterActivity activity);
    public void inject(RouteMapActivity activity);
}
