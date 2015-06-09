package put.iwm.android.motionrecorder.presenters;

import android.location.Location;

import java.util.Map;

import put.iwm.android.motionrecorder.contracts.LocationObserver;
import put.iwm.android.motionrecorder.contracts.TimerObserver;
import put.iwm.android.motionrecorder.contracts.TrainingObserver;
import put.iwm.android.motionrecorder.fragments.TextGenerator;
import put.iwm.android.motionrecorder.training.TrainingManager;
import put.iwm.android.motionrecorder.training.TrainingTimer;
import put.iwm.android.motionrecorder.views.ActiveTrainingView;

/**
 * Created by Szymon on 2015-06-09.
 */
public class ActiveTrainingPresenterImpl implements ActiveTrainingPresenter, TimerObserver, TrainingObserver {

    private ActiveTrainingView view;
    private TextGenerator textGenerator;
    private TrainingManager trainingManager;
    private TrainingTimer trainingTimer;

    public ActiveTrainingPresenterImpl(TextGenerator textGenerator, TrainingManager trainingManager, TrainingTimer trainingTimer) {
        this.textGenerator = textGenerator;
        this.trainingManager = trainingManager;
        this.trainingTimer = trainingTimer;
        this.trainingManager.setTrainingObserver(this);
        this.trainingTimer.setTimerObserver(this);
    }

    @Override
    public void processTrainingUpdate(Map<String, Object> model) {

        double speed = Double.parseDouble(model.get("speed").toString());
        String speedText = textGenerator.createSpeedText(speed);
        model.put("speed", speedText);

        double distance = Double.parseDouble(model.get("distance").toString());
        String distanceText = textGenerator.createDistanceText(distance);
        model.put("distance", distanceText);

        view.setTrainingData(model);
    }

    @Override
    public void processTimerUpdate(long time) {
        String timerText = textGenerator.createTimerText(time);
        view.setTrainingTimerText(timerText);
    }

    @Override
    public void onResume() {

        long time = trainingTimer.getDurationTime();
        String timerText = textGenerator.createTimerText(time);
        view.setTrainingTimerText(timerText);

        trainingManager.requestTrainingUpdate();
    }

    @Override
    public void startTraining() {
        trainingManager.startTraining();
    }

    @Override
    public void finishTraining(boolean saveTraining) {
        trainingManager.finishTraining(saveTraining);
    }

    @Override
    public void resumeTraining() {
        trainingManager.resumeTraining();
    }

    @Override
    public void pauseTraining() {
        trainingManager.pauseTraining();
    }

    @Override
    public boolean isTrainingInProgress() {
        return trainingManager.isTrainingInProgress();
    }

    @Override
    public boolean isTrainingPaused() {
        return trainingManager.isTrainingPaused();
    }

    public void setView(ActiveTrainingView view) {
        this.view = view;
    }
}
