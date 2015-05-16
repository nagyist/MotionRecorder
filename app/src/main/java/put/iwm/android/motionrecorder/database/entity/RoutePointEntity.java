package put.iwm.android.motionrecorder.database.entity;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Szymon on 2015-05-16.
 */
public class RoutePointEntity extends RealmObject {

    private long id;
    private double latitude;
    private double longitude;
    private double altitude;
    private double moveDistance;
    private long moveTime;
    private int serialNumber;
    private RealmList<RouteEntity> routeEntity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public RealmList<RouteEntity> getRouteEntity() {
        return routeEntity;
    }

    public void setRouteEntity(RealmList<RouteEntity> routeEntity) {
        this.routeEntity = routeEntity;
    }
}
