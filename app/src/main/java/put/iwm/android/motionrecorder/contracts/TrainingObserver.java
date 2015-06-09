package put.iwm.android.motionrecorder.contracts;

import java.util.Map;

/**
 * Created by Szymon on 2015-05-14.
 */
public interface TrainingObserver {
    public void processTrainingUpdate(Map<String, Object> model);
}
