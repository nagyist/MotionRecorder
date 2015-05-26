package put.iwm.android.motionrecorder.training;

import android.location.Location;

import java.util.LinkedList;
import java.util.List;

import put.iwm.android.motionrecorder.database.entity.RouteEntity;
import put.iwm.android.motionrecorder.database.entity.RoutePointEntity;
import put.iwm.android.motionrecorder.database.entity.SpeedPointEntity;

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

    public Route(RouteEntity routeEntity) {

        this();
        id = routeEntity.getId();
        initRoutePoints(routeEntity.getRoutePoints());
        totalDistance = routeEntity.getTotalDistance();
        partialDistance = routeEntity.getPartialDistance();
        currentSpeed = routeEntity.getCurrentSpeed();
        initSpeedPoints(routeEntity.getSpeedMeasurementPoints());
        routePointSerialNumber = routeEntity.getRoutePointSerialNumber();
        speedPointSerialNumber = routeEntity.getRoutePointSerialNumber();
    }
    
    private void initRoutePoints(List<RoutePointEntity> routePointEntities) {
        for(RoutePointEntity point : routePointEntities)
            routePoints.add(new RoutePoint(point));
    }

    private void initSpeedPoints(List<SpeedPointEntity> speedPointEntities) {
        for(SpeedPointEntity point : speedPointEntities)
            speedMeasurementPoints.add(new SpeedPoint(point));
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
        return getNumberOfRoutePoints() >= 2;
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

    public double getMaxSpeed() {

        double maxSpeed = 0;

        for(SpeedPoint point : speedMeasurementPoints)
            if(isGreater(point.getValue(), maxSpeed))
                maxSpeed = point.getValue();

        return maxSpeed;
    }

    private boolean isGreater(double firstValue, double secondValue) {
        return firstValue > secondValue && isNotInfinity(firstValue);
    }

    private boolean isNotInfinity(double value) {
        return value != Double.POSITIVE_INFINITY;
    }

    public double getAvgSpeed() {

        if(speedMeasurementPoints.size() > 0) {
            return calculateAvgSpeed();
        } else {
            return 0;
        }
    }

    private double calculateAvgSpeed() {

        double avgSpeed = 0;
        double numerator = 0;
        double denominator = 0;
        int i = 0;

        for(SpeedPoint point : speedMeasurementPoints) {

            if(isNotInfinity(point.getValue())) {
                long partialMoveTime = routePoints.get(i + 1).getMoveTime() - routePoints.get(i).getMoveTime();
                numerator += point.getValue() * partialMoveTime;
                denominator += partialMoveTime;
            }

            i++;
        }

        if(denominator > 0)
            avgSpeed = numerator / denominator;

        return avgSpeed;
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
