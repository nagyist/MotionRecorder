package put.iwm.android.motionrecorder.training;

import put.iwm.android.motionrecorder.database.entity.RoutePointEntity;

/**
 * Created by Szymon on 2015-04-23.
 */
public class RoutePoint {

    private long id;
    private double latitude;
    private double longitude;
    private double altitude;
    private double moveDistance;
    private long moveTime;
    private int serialNumber;
    private boolean pauseMarker;
    private boolean resumeMarker;

    public RoutePoint() {
    }

    public RoutePoint(double latitude, double longitude, double altitude, double moveDistance,
                      long moveTime, int serialNumber, boolean pauseMarker, boolean resumeMarker) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.moveDistance = moveDistance;
        this.moveTime = moveTime;
        this.serialNumber = serialNumber;
        this.pauseMarker = pauseMarker;
        this.resumeMarker = resumeMarker;
    }

    public RoutePoint(RoutePointEntity routePointEntity) {

        id = routePointEntity.getId();
        latitude = routePointEntity.getLatitude();
        longitude = routePointEntity.getLongitude();
        altitude = routePointEntity.getAltitude();
        moveDistance = routePointEntity.getMoveDistance();
        moveTime = routePointEntity.getMoveTime();
        serialNumber = routePointEntity.getSerialNumber();
        pauseMarker = routePointEntity.isPauseMarker();
        resumeMarker = routePointEntity.isResumeMarker();
    }

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

    public boolean isPauseMarker() {
        return pauseMarker;
    }

    public void setPauseMarker(boolean pauseMarker) {
        this.pauseMarker = pauseMarker;
    }

    public boolean isResumeMarker() {
        return resumeMarker;
    }

    public void setResumeMarker(boolean resumeMarker) {
        this.resumeMarker = resumeMarker;
    }
}
