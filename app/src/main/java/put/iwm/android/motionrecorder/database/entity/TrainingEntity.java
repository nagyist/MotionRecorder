package put.iwm.android.motionrecorder.database.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.sql.Date;

import put.iwm.android.motionrecorder.database.MotionRecorderDatabase;
import put.iwm.android.motionrecorder.training.Route;
import put.iwm.android.motionrecorder.training.Training;

/**
 * Created by Szymon on 2015-05-16.
 */
@Table(name = "trainings")
public class TrainingEntity extends Model {

    @Column
    private boolean inProgress;
    @Column
    private boolean paused;
    @Column
    private Date startDate;
    @Column
    private Date finishDate;
    @Column(name = "route_id")
    private RouteEntity routeEntity;

    public TrainingEntity() {
        super();
    }

    public TrainingEntity(Training training) {

        this();
        startDate = training.getStartDate();
        finishDate = training.getFinishDate();
        inProgress = training.isInProgress();
        paused = training.isPaused();
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public RouteEntity getRouteEntity() {
        return routeEntity;
    }

    public void setRouteEntity(RouteEntity routeEntity) {
        this.routeEntity = routeEntity;
    }

}
