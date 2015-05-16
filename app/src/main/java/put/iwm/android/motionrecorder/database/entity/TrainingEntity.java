package put.iwm.android.motionrecorder.database.entity;

import io.realm.RealmObject;
import put.iwm.android.motionrecorder.training.Route;

/**
 * Created by Szymon on 2015-05-16.
 */
public class TrainingEntity extends RealmObject {

    private long id;
    private boolean inProgress;
    private boolean paused;
    private RouteEntity routeEntity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public RouteEntity getRouteEntity() {
        return routeEntity;
    }

    public void setRouteEntity(RouteEntity routeEntity) {
        this.routeEntity = routeEntity;
    }
}
