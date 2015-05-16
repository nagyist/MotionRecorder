package put.iwm.android.motionrecorder.database.repository;

import java.util.List;

import put.iwm.android.motionrecorder.training.Training;

/**
 * Created by Szymon on 2015-05-16.
 */
public interface TrainingRepository {

    public void save(Training training);
    public Training findById(int id);
    public List<Training> findAll();
    public void deleteById(int id);


}
