package put.iwm.android.motionrecorder.training;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import put.iwm.android.motionrecorder.database.entity.TrainingEntity;

/**
 * Created by Szymon on 2015-05-14.
 */
public class Training {

    private long id;
    private boolean inProgress;
    private boolean paused;
    private Date startDate;
    private Date finishDate;
    private Route route;
    private CurrentDateGenerator currentDateGenerator;

    public Training() {
        currentDateGenerator = new CurrentDateGeneratorImpl();
        route = new Route();
    }

    public Training(TrainingEntity trainingEntity) {

        this();
        id = trainingEntity.getId();
        inProgress = trainingEntity.isInProgress();
        paused = trainingEntity.isPaused();
        startDate = trainingEntity.getStartDate();
        finishDate = trainingEntity.getFinishDate();
        route = new Route(trainingEntity.getRouteEntity());
    }

    public void start() {
        route.removeAllRoutePoints();
        startDate = currentDateGenerator.generateCurrentDate();
        inProgress = true;
        paused = false;
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }

    public void finish() {
        finishDate = currentDateGenerator.generateCurrentDate();
        inProgress = false;
        paused = false;
    }

    public double getMaxSpeed() {
        return route.getMaxSpeed();
    }

    public double getAvgSpeed() {
        return route.getAvgSpeed();
    }

    //TODO
    public long getDurationTime() {

        List<RoutePoint> routePoints = route.getRoutePoints();
        int lastPointIndex = routePoints.size() - 1;

        if(lastPointIndex > 0)
            return routePoints.get(lastPointIndex).getMoveTime();
        else
            return 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Route getRoute() {
        return route;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void appendRoutePointToRoute(RoutePoint routePoint) {
        route.appendRoutePoint(routePoint);
    }

    public double getTotalDistance() {
        return route.getTotalDistance();
    }

    public double getCurrentSpeed() {
        return route.getCurrentSpeed();
    }

    public List<RoutePoint> getRoutePoints() {
        return route.getRoutePoints();
    }

    public List<SpeedPoint> getSpeedPoints() {
        return route.getSpeedMeasurementPoints();
    }
}
