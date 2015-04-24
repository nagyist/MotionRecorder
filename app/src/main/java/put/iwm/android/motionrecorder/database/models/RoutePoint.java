package put.iwm.android.motionrecorder.database.models;

import java.sql.Timestamp;

/**
 * Created by Szymon on 2015-04-23.
 */
public class RoutePoint {

    private int id;
    private double latitude;
    private double longitude;
    private double altitude;
    private double moveDistance;
    private Timestamp moveTime;
    private int seriaNumber;
    private RoutePoint previousPoint;

    public RoutePoint(int id, double latitude, double longitude, double altitude, double moveDistance,
                      Timestamp moveTime, int seriaNumber, RoutePoint previousPoint) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.moveDistance = moveDistance;
        this.moveTime = moveTime;
        this.seriaNumber = seriaNumber;
        this.previousPoint = previousPoint;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Timestamp getMoveTime() {
        return moveTime;
    }

    public void setMoveTime(Timestamp moveTime) {
        this.moveTime = moveTime;
    }

    public int getSeriaNumber() {
        return seriaNumber;
    }

    public void setSeriaNumber(int seriaNumber) {
        this.seriaNumber = seriaNumber;
    }

    public RoutePoint getPreviousPoint() {
        return previousPoint;
    }

    public void setPreviousPoint(RoutePoint previousPoint) {
        this.previousPoint = previousPoint;
    }

}
