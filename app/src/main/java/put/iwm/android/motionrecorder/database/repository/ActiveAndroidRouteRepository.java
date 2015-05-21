package put.iwm.android.motionrecorder.database.repository;

import put.iwm.android.motionrecorder.database.entity.RouteEntity;
import put.iwm.android.motionrecorder.database.entity.RoutePointEntity;
import put.iwm.android.motionrecorder.training.Route;

/**
 * Created by Szymon on 2015-05-17.
 */
public class ActiveAndroidRouteRepository implements RouteRepository {

    @Override
    public void save(Route route) {

        RouteEntity routeEntity = new RouteEntity();
        routeEntity.setTotalDistance(route.getTotalDistance());
        routeEntity.setPartialDistance(route.getPartialDistance());
        routeEntity.setCurrentSpeed(route.getCurrentSpeed());
        routeEntity.setRoutePointSerialNumber(route.getRoutePointSerialNumber());

        routeEntity.save();
    }
}
