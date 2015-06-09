package put.iwm.android.motionrecorder.views;

import java.util.Map;

/**
 * Created by Szymon on 2015-05-27.
 */
public interface TrainingStatsView {
    public void setTrainingStatsData(Map<String, String> model);
    public void onDeleteTrainingSuccess();
}
