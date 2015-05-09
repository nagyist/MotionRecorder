package put.iwm.android.motionrecorder.di;

import put.iwm.android.motionrecorder.activities.AuthenticationActivity;
import put.iwm.android.motionrecorder.activities.RegisterActivity;

/**
 * Created by Szymon on 2015-05-09.
 */
public interface ActivityDependencyGraph {

    public void inject(AuthenticationActivity activity);
    public void inject(RegisterActivity activity);
}
