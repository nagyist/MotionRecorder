package put.iwm.android.motionrecorder.database.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import put.iwm.android.motionrecorder.training.RoutePoint;

/**
 * Created by Szymon on 2015-05-16.
 */
@Table(name = "route_points")
public class RoutePointEntity extends Model {

    @Column
    private double latitude;
    @Column
    private double longitude;
    @Column
    private double altitude;
    @Column
    private double moveDistance;
    @Column
    private long moveTime;
    @Column
    private int serialNumber;
    @Column(name = "route_id")
    private RouteEntity routeEntity;

    public RoutePointEntity() {
        super();
    }

    public RoutePointEntity(RoutePoint routePoint) {

        this();
        latitude = routePoint.getLatitude();
        longitude = routePoint.getLongitude();
        altitude = routePoint.getAltitude();
        moveDistance = routePoint.getMoveDistance();
        moveTime = routePoint.getMoveTime();
        serialNumber = routePoint.getSerialNumber();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getMoveDistance() {
        return moveDistance;
    }

    public void setMoveDistance(double moveDistance) {
        this.moveDistance = moveDistance;
    }

    public long getMoveTime() {
        return moveTime;
    }

    public void setMoveTime(long moveTime) {
        this.moveTime = moveTime;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public RouteEntity getRouteEntity() {
        return routeEntity;
    }

    public void setRouteEntity(RouteEntity routeEntity) {
        this.routeEntity = routeEntity;
    }
}
