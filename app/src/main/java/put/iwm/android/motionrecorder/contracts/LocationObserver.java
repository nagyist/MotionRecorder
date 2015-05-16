package put.iwm.android.motionrecorder.contracts;

import android.location.Location;

/**
 * Created by Szymon on 2015-05-14.
 */
public interface LocationObserver {

    public void processLocationUpdate(Location location);
}
