package put.iwm.android.motionrecorder.training;

/**
 * Created by Szymon on 2015-05-03.
 */
public interface TrainingTimer {

    public void start();
    public void resume();
    public void pause();
    public void stop();
    public long getDurationTime();
    public void setTimerListener(TimerListener timerListener);
}
