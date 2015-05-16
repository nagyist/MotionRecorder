package put.iwm.android.motionrecorder.training;

import java.util.List;

/**
 * Created by Szymon on 2015-05-14.
 */
public class Training {

    private long id;
    private boolean inProgress;
    private boolean paused;
    private Route route;

    public Training() {
        route = new Route();
    }

    public void start() {
        route.removeAllRoutePoints();
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
        inProgress = false;
        paused = false;
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
}
