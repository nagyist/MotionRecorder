package put.iwm.android.motionrecorder.presenters;

import java.util.HashMap;

import put.iwm.android.motionrecorder.interactors.OnGetTrainingSpeedDataFinishedListener;
import put.iwm.android.motionrecorder.interactors.SpeedGraphInteractor;
import put.iwm.android.motionrecorder.views.SpeedGraphView;

/**
 * Created by Szymon on 2015-05-26.
 */
public class SpeedGraphPresenterImpl implements SpeedGraphPresenter, OnGetTrainingSpeedDataFinishedListener {

    private SpeedGraphView view;
    private SpeedGraphInteractor interactor;

    public SpeedGraphPresenterImpl(SpeedGraphView view, SpeedGraphInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onResume(long trainingId) {
        interactor.getTrainingSpeedData(trainingId, this);
    }

    @Override
    public void onTrainingSpeedDataReady(HashMap<String, Object> model) {
        view.setTrainingSpeedData(model);
    }
}
