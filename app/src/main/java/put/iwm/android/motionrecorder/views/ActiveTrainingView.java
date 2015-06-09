package put.iwm.android.motionrecorder.views;

import java.util.Map;

/**
 * Created by Szymon on 2015-06-09.
 */
public interface ActiveTrainingView {
    public void setTrainingTimerText(String time);
    public void setTrainingData(Map<String, Object> model);
}
