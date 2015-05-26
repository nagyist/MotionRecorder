package put.iwm.android.motionrecorder.presenters;

import java.util.HashMap;

import put.iwm.android.motionrecorder.interactors.OnGetTrainingSpeedDataFinishedListener;
import put.iwm.android.motionrecorder.views.SpeedGraphView;

/**
 * Created by Szymon on 2015-05-26.
 */
public class SpeedGraphPresenterImpl implements SpeedGraphPresenter, OnGetTrainingSpeedDataFinishedListener {

    private SpeedGraphView view;

    public SpeedGraphPresenterImpl(SpeedGraphView view) {
        this.view = view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onTrainingSpeedDataReady(HashMap<String, Object> model) {

    }
}
