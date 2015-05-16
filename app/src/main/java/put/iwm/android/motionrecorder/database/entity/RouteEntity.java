package put.iwm.android.motionrecorder.database.entity;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Szymon on 2015-05-16.
 */
public class RouteEntity extends RealmObject {

    private long id;
    private RealmList<RoutePointEntity> routePoints;
    private double totalDistance;
    private double partialDistance;
    private double currentSpeed;
    private int routePointSerialNumber;
    private RealmList<DoubleEntity> speedMeasurementPoints;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RealmList<RoutePointEntity> getRoutePoints() {
        return routePoints;
    }

    public void setRoutePoints(RealmList<RoutePointEntity> routePoints) {
        this.routePoints = routePoints;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public double getPartialDistance() {
        return partialDistance;
    }

    public void setPartialDistance(double partialDistance) {
        this.partialDistance = partialDistance;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public int getRoutePointSerialNumber() {
        return routePointSerialNumber;
    }

    public void setRoutePointSerialNumber(int routePointSerialNumber) {
        this.routePointSerialNumber = routePointSerialNumber;
    }

    public RealmList<DoubleEntity> getSpeedMeasurementPoints() {
        return speedMeasurementPoints;
    }

    public void setSpeedMeasurementPoints(RealmList<DoubleEntity> speedMeasurementPoints) {
        this.speedMeasurementPoints = speedMeasurementPoints;
    }
}
