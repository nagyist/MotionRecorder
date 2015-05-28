package put.iwm.android.motionrecorder.views;

import java.util.HashMap;

/**
 * Created by Szymon on 2015-05-27.
 */
public interface TrainingStatsView {
    public void setTrainingStatsData(HashMap<String, String> model);
    public void onDeleteTrainingSuccess();
}
