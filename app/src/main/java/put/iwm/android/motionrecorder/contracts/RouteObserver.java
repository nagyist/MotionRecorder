package put.iwm.android.motionrecorder.contracts;

import java.util.List;
import java.util.Map;

import put.iwm.android.motionrecorder.training.RoutePoint;

/**
 * Created by Szymon on 2015-05-15.
 */
public interface RouteObserver {

    public void processRouteUpdate(List<RoutePoint> routePoints);
}
