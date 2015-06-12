package put.iwm.android.motionrecorder.training;

import android.location.Location;

import java.util.ArrayList;
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
        speedMeasurementPoints = new ArrayList<>();
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

        for(int i = 0; i < speedMeasurementPoints.size(); i++) {
            double speed = calculatePartialAvgSpeedInWindow(i, 4);
            if(isGreater(speed, maxSpeed))
                maxSpeed = speed;
        }

        return maxSpeed;
    }

    private boolean isGreater(double firstValue, double secondValue) {
        return firstValue > secondValue && isNotInfinity(firstValue);
    }

    private boolean isNotInfinity(double value) {
        return value != Double.POSITIVE_INFINITY;
    }

    public double getAvgSpeed() {

        if(speedMeasurementPoints.size() > 0)
            return calculateAvgSpeed();
        else
            return 0;
    }

    private double calculateAvgSpeed() {

        double avgSpeed = 0;
        double numerator = 0;
        double denominator = 0;

        for(int i = 0; i < speedMeasurementPoints.size(); i++) {

            if(isNotInfinity(speedMeasurementPoints.get(i).getValue())) {
                long partialMoveTime = routePoints.get(i + 1).getMoveTime() - routePoints.get(i).getMoveTime();
                numerator += calculatePartialAvgSpeedInWindow(i, 4) * partialMoveTime;
                denominator += partialMoveTime;
            }
        }

        if(denominator > 0)
            avgSpeed = numerator / denominator;

        return avgSpeed;
    }

    private double calculatePartialAvgSpeedInWindow(int index, int windowSize) {

        int startIndex = index - windowSize;
        startIndex = startIndex >= 0 ? startIndex : 0;

        int endIndex = index + windowSize;
        endIndex = endIndex < speedMeasurementPoints.size() ? endIndex : speedMeasurementPoints.size() - 1;

        return calculatePartialAvgSpeedBetweenIndices(startIndex, index, endIndex);
    }

    private double calculatePartialAvgSpeedBetweenIndices(int startIndex, int midIndex, int endIndex) {

        double result = 0, numerator = 0, denominator = 0, pointWeight;
        double[] weights = calculateWeights(startIndex, endIndex);

        for(int i = startIndex, j = 0; i <= endIndex; i++, j++) {

            SpeedPoint point = speedMeasurementPoints.get(i);

            if(isNotInfinity(point.getValue())) {
                pointWeight = weights[j] * weights[j];
                numerator += point.getValue() * pointWeight;
                denominator += pointWeight;
            }
        }

        if(denominator > 0)
            result = numerator / denominator;

        return result;
    }

    private double[] calculateWeights(int startIndex, int endIndex) {

        int size = endIndex - startIndex + 1;
        int midIndex = size / 2;
        double[] weights = new double[size];
        double weight = 0;

        for(int i = 0; i < size; i++) {

            if(i <= midIndex)
                weight++;
            else
                weight--;

            weights[i] = weight;
        }

        return weights;
    }

    public List<SpeedPoint> getSpeedMeasurementPointsForGraph() {

        List<SpeedPoint> points = new ArrayList<>();

        for(int i = 0; i < speedMeasurementPoints.size(); i++) {

            double value = calculatePartialAvgSpeedInWindow(i, 4);
            SpeedPoint point = new SpeedPoint(value, i + 1);

            points.add(point);
        }

        return points;
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
