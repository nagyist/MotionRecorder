package put.iwm.android.motionrecorder.training;

import android.location.Location;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Szymon on 2015-05-14.
 */
public class Route {

    private long id;
    private List<RoutePoint> routePoints;
    private double totalDistance;
    private double partialDistance;
    private double currentSpeed;
    private List<SpeedPoint> speedMeasurementPoints;
    private int routePointSerialNumber;
    private int speedPointSerialNumber;

    public Route() {
        routePoints = new LinkedList<>();
        speedMeasurementPoints = new LinkedList<>();
    }

    public void appendRoutePoint(RoutePoint routePoint) {

        routePointSerialNumber++;
        routePoint.setSerialNumber(routePointSerialNumber);
        routePoints.add(routePoint);

        updateDistance();
        updateSpeed();
        updateRoutePointDistance(routePoint);
    }

    private void updateDistance() {
        updatePartialDistance();
        totalDistance += partialDistance;
    }

    private void updatePartialDistance() {

        if(canUpdate())
            partialDistance = calculatePartialDistance();
    }

    private void updateRoutePointDistance(RoutePoint routePoint) {
        int index = routePoints.indexOf(routePoint);
        if(index >= 0)
            routePoints.get(index).setMoveDistance(totalDistance);
    }

    private boolean canUpdate() {
        return getNumberOfRoutePoints() > 2;
    }

    private double calculatePartialDistance() {

        float []result = new float[3];
        int numberOfRoutePoints = getNumberOfRoutePoints();

        RoutePoint firstLocation = routePoints.get(numberOfRoutePoints - 2);
        RoutePoint secondLocation = routePoints.get(numberOfRoutePoints - 1);

        Location.distanceBetween(firstLocation.getLatitude(), firstLocation.getLongitude(),
                secondLocation.getLatitude(), secondLocation.getLongitude(), result);

        return result[0];
    }

    private void updateSpeed() {

        if(canUpdate()) {
            currentSpeed = calculateSpeed();
            speedPointSerialNumber++;
            SpeedPoint speedPoint = new SpeedPoint(currentSpeed, speedPointSerialNumber);
            speedMeasurementPoints.add(speedPoint);
        }
    }

    private double calculateSpeed() {

        int numberOfRoutePoints = getNumberOfRoutePoints();

        RoutePoint firstLocation = routePoints.get(numberOfRoutePoints - 2);
        RoutePoint secondLocation = routePoints.get(numberOfRoutePoints - 1);

        long moveTime = (secondLocation.getMoveTime() - firstLocation.getMoveTime()) / 1000;

        return partialDistance / (double) moveTime;
    }

    public int getNumberOfRoutePoints() {
        return routePoints.size();
    }

    public void removeAllRoutePoints() {
        routePoints.clear();
        totalDistance = 0;
        routePointSerialNumber = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<RoutePoint> getRoutePoints() {
        return routePoints;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public double getPartialDistance() {
        return partialDistance;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public List<SpeedPoint> getSpeedMeasurementPoints() {
        return speedMeasurementPoints;
    }

    public int getRoutePointSerialNumber() {
        return routePointSerialNumber;
    }

    public int getSpeedPointSerialNumber() {
        return speedPointSerialNumber;
    }
}
