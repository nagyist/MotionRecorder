package put.iwm.android.motionrecorder.training;

import put.iwm.android.motionrecorder.contracts.TimerObserver;

/**
 * Created by Szymon on 2015-05-03.
 */
public interface TrainingTimer {

    public void start();
    public void resume();
    public void pause();
    public void stop();
    public long getDurationTime();
    public void setTimerObserver(TimerObserver timerObserver);
}
