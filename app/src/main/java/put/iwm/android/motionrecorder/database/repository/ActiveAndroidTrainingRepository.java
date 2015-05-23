package put.iwm.android.motionrecorder.database.repository;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import put.iwm.android.motionrecorder.database.entity.RouteEntity;
import put.iwm.android.motionrecorder.database.entity.RoutePointEntity;
import put.iwm.android.motionrecorder.database.entity.SpeedPointEntity;
import put.iwm.android.motionrecorder.database.entity.TrainingEntity;
import put.iwm.android.motionrecorder.training.Route;
import put.iwm.android.motionrecorder.training.RoutePoint;
import put.iwm.android.motionrecorder.training.SpeedPoint;
import put.iwm.android.motionrecorder.training.Training;

/**
 * Created by Szymon on 2015-05-16.
 */
public class ActiveAndroidTrainingRepository implements TrainingRepository {

    public ActiveAndroidTrainingRepository() {
    }

    @Override
    public void save(Training training) {

        ActiveAndroid.beginTransaction();

        Route route = training.getRoute();
        RouteEntity routeEntity = saveRoute(route);

        saveRoutePoints(training.getRoutePoints(), routeEntity);
        saveSpeedPoints(training.getSpeedPoints(), routeEntity);
        saveTraining(training, routeEntity);
        ActiveAndroid.setTransactionSuccessful();

        ActiveAndroid.endTransaction();
    }

    private RouteEntity saveRoute(Route route) {

        RouteEntity routeEntity = new RouteEntity(route);
        routeEntity.save();

        return routeEntity;
    }

    private void saveRoutePoints(List<RoutePoint> routePoints, RouteEntity routeEntity) {

        for(RoutePoint point : routePoints) {
            RoutePointEntity pointEntity = new RoutePointEntity(point);
            pointEntity.setRouteEntity(routeEntity);
            pointEntity.save();
        }
    }

    private void saveSpeedPoints(List<SpeedPoint> speedPoints, RouteEntity routeEntity) {

        for(SpeedPoint point : speedPoints) {
            SpeedPointEntity pointEntity = new SpeedPointEntity(point);
            pointEntity.setRouteEntity(routeEntity);
            pointEntity.save();
        }
    }

    private void saveTraining(Training training, RouteEntity routeEntity) {

        TrainingEntity trainingEntity = new TrainingEntity(training);
        trainingEntity.setRouteEntity(routeEntity);
        trainingEntity.save();
    }

    @Override
    public Training findById(int id) {

        TrainingEntity trainingEntity = new Select()
                .from(TrainingEntity.class)
                .where("id = ?", id)
                .executeSingle();

        Training training = new Training(trainingEntity);

        return training;
    }

    @Override
    public List<Training> findAll() {

        List<TrainingEntity> trainingEntities = new Select().from(TrainingEntity.class).execute();
        List<Training> result = new ArrayList<>(trainingEntities.size());

        for(TrainingEntity training : trainingEntities)
            result.add(new Training(training));

        return result;
    }

    @Override
    public void deleteById(int id) {
        new Delete().from(TrainingEntity.class).where("Id = ?", id).execute();
    }
}
