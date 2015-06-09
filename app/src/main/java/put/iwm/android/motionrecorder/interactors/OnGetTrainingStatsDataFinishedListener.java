package put.iwm.android.motionrecorder.interactors;

import java.util.Map;

/**
 * Created by Szymon on 2015-05-27.
 */
public interface OnGetTrainingStatsDataFinishedListener {
    public void onTrainingStatsDataReady(Map<String, Object> model);
}
