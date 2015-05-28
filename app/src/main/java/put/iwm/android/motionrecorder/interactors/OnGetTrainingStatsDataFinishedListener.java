package put.iwm.android.motionrecorder.interactors;

import java.util.HashMap;

/**
 * Created by Szymon on 2015-05-27.
 */
public interface OnGetTrainingStatsDataFinishedListener {
    public void onTrainingStatsDataReady(HashMap<String, Object> model);
}
