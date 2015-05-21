package put.iwm.android.motionrecorder.database.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

import put.iwm.android.motionrecorder.training.Route;

/**
 * Created by Szymon on 2015-05-16.
 */
@Table(name = "routes")
public class RouteEntity extends Model {

    private List<RoutePointEntity> routePoints;
    @Column
    private double totalDistance;
    @Column
    private double partialDistance;
    @Column
    private double currentSpeed;
    @Column
    private int routePointSerialNumber;
    @Column
    private int speedPointSerialNumber;
    private List<SpeedPointEntity> speedMeasurementPoints;

    public RouteEntity() {
        super();
    }

    public RouteEntity(Route route) {

        this();
        totalDistance = route.getTotalDistance();
        partialDistance = route.getPartialDistance();
        currentSpeed = route.getCurrentSpeed();
        routePointSerialNumber = route.getRoutePointSerialNumber();
        speedPointSerialNumber = route.getSpeedPointSerialNumber();
    }

    public List<RoutePointEntity> getRoutePoints() {

        if(routePoints == null)
            routePoints = getMany(RoutePointEntity.class, "route_id");

        return routePoints;
    }

    public void setRoutePoints(List<RoutePointEntity> routePoints) {
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

    public int getSpeedPointSerialNumber() {
        return speedPointSerialNumber;
    }

    public void setSpeedPointSerialNumber(int speedPointSerialNumber) {
        this.speedPointSerialNumber = speedPointSerialNumber;
    }

    public List<SpeedPointEntity> getSpeedMeasurementPoints() {

        if(speedMeasurementPoints == null)
            speedMeasurementPoints = getMany(SpeedPointEntity.class, "route_id");

        return speedMeasurementPoints;
    }

    public void setSpeedMeasurementPoints(List<SpeedPointEntity> speedMeasurementPoints) {
        this.speedMeasurementPoints = speedMeasurementPoints;
    }
}
