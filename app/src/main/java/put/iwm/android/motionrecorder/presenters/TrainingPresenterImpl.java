package put.iwm.android.motionrecorder.presenters;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import put.iwm.android.motionrecorder.fragments.TextGenerator;
import put.iwm.android.motionrecorder.interactors.OnDeleteTrainingFinishedListener;
import put.iwm.android.motionrecorder.interactors.OnGetTrainingStatsDataFinishedListener;
import put.iwm.android.motionrecorder.interactors.TrainingInteractor;
import put.iwm.android.motionrecorder.views.TrainingStatsView;

/**
 * Created by Szymon on 2015-05-27.
 */
public class TrainingPresenterImpl implements TrainingPresenter, OnGetTrainingStatsDataFinishedListener, OnDeleteTrainingFinishedListener {

    private TrainingStatsView view;
    private TrainingInteractor interactor;
    private TextGenerator textGenerator;

    public TrainingPresenterImpl(TrainingStatsView view, TrainingInteractor interactor, TextGenerator textGenerator) {
        this.view = view;
        this.interactor = interactor;
        this.textGenerator = textGenerator;
    }

    @Override
    public void onResume(long trainingId) {
        interactor.getTrainingStatsData(trainingId, this);
    }

    @Override
    public void deleteTraining(long trainingId) {
        interactor.deleteTraining(trainingId, this);
    }

    @Override
    public void onTrainingStatsDataReady(Map<String, Object> model) {

        Map<String, String> viewModel = new HashMap<>();
        viewModel.put("startDate", textGenerator.createDateText((Date)model.get("startDate")));
        viewModel.put("finishDate", textGenerator.createDateText((Date)model.get("finishDate")));
        viewModel.put("duration", textGenerator.createTimerText((long) model.get("duration")));
        viewModel.put("distance", textGenerator.createDistanceText((double)model.get("distance")));
        viewModel.put("maxSpeed", textGenerator.createSpeedText((double) model.get("maxSpeed")));
        viewModel.put("avgSpeed", textGenerator.createSpeedText((double) model.get("avgSpeed")));

        view.setTrainingStatsData(viewModel);
    }

    @Override
    public void onDeleteTrainingSuccess() {
        view.onDeleteTrainingSuccess();
    }
}
