package put.iwm.android.motionrecorder.database.repository;

import android.content.Context;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import put.iwm.android.motionrecorder.database.entity.RouteEntity;
import put.iwm.android.motionrecorder.database.entity.TrainingEntity;
import put.iwm.android.motionrecorder.training.Training;

/**
 * Created by Szymon on 2015-05-16.
 */
public class RealmTrainingRepository implements TrainingRepository {

    private Realm realmInstance;

    public RealmTrainingRepository(Realm  realmInstance) {
        this.realmInstance = realmInstance;
    }

    @Override
    public void save(Training training) {

        TrainingEntity trainingEntity = new TrainingEntity();
        trainingEntity.setId(getNextTrainingId());
        trainingEntity.setInProgress(training.isInProgress());
        trainingEntity.setPaused(training.isPaused());

        realmInstance.beginTransaction();
        TrainingEntity realmTrainingEntity = realmInstance.copyToRealm(trainingEntity);
        realmInstance.commitTransaction();
    }

    private long getNextTrainingId() {
        return 1;
    }

    @Override
    public Training findById(int id) {
        return null;
    }

    @Override
    public List<Training> findAll() {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }
}
