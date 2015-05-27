package put.iwm.android.motionrecorder.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ValueFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import put.iwm.android.motionrecorder.R;
import put.iwm.android.motionrecorder.base.BaseActivity;
import put.iwm.android.motionrecorder.di.SpeedGraphActivityComponent;
import put.iwm.android.motionrecorder.presenters.SpeedGraphPresenter;
import put.iwm.android.motionrecorder.training.SpeedPoint;
import put.iwm.android.motionrecorder.views.SpeedGraphView;

public class SpeedGraphActivity extends BaseActivity implements SpeedGraphView {

    private LineChart speedGraph;
    @Inject SpeedGraphPresenter speedGraphPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_graph);

        getSpeedGraphActivityComponent().inject(this);

        setupUIReferences();
        setupSpeedGraph();
    }

    @Override
    protected void onResume() {
        super.onResume();
        long trainingId = getTrainingId();
        speedGraphPresenter.onResume(trainingId);
    }

    private long getTrainingId() {
        return getIntent().getExtras().getLong("trainingId");
    }

    private void setupUIReferences() {
        speedGraph = (LineChart) findViewById(R.id.speed_graph);
    }

    @Override
    public void setTrainingSpeedData(HashMap<String, Object> model) {
        List<SpeedPoint> speedPoints = (List<SpeedPoint>) model.get("speedPoints");
        double maxSpeed = (double) model.get("maxSpeed");
        double avgSpeed = (double) model.get("avgSpeed");
        setSpeedGraphData(speedPoints, (float) maxSpeed, (float) avgSpeed);
    }

    private void setupSpeedGraph() {

        speedGraph.setDrawGridBackground(false);
        speedGraph.setDescription("");
        speedGraph.setNoDataText("Brak danych do wyświetlenia");
        speedGraph.setHighlightEnabled(true);
        speedGraph.setTouchEnabled(false);
        speedGraph.setDragEnabled(true);
        speedGraph.setScaleEnabled(true);
        speedGraph.setPinchZoom(true);
        speedGraph.setHighlightIndicatorEnabled(false);

        YAxis leftAxis = speedGraph.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.setStartAtZero(false);
        leftAxis.enableGridDashedLine(10, 10, 0);
        leftAxis.setDrawLimitLinesBehindData(true);
        leftAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                String result = String.format("%.2f m/s", value);
                return result;
            }
        });

        speedGraph.getAxisRight().setEnabled(false);
    }

    private void setSpeedGraphData(List<SpeedPoint> speedPoints, float maxSpeed, float avgSpeed) {

        if(!speedPoints.isEmpty()) {

            LimitLine topLimit = new LimitLine(maxSpeed, "Prędkość maksymalna");
            topLimit.setLineWidth(2);
            topLimit.enableDashedLine(10, 10, 0);
            topLimit.setLabelPosition(LimitLine.LimitLabelPosition.POS_RIGHT);
            topLimit.setTextSize(10);

            LimitLine bottomLimit = new LimitLine(0);
            bottomLimit.setLineWidth(2);
            bottomLimit.enableDashedLine(10, 10, 0);
            bottomLimit.setLabelPosition(LimitLine.LimitLabelPosition.POS_RIGHT);
            bottomLimit.setTextSize(10);

            LimitLine avgLimit = new LimitLine(avgSpeed);
            avgLimit.setLineWidth(1);
            avgLimit.setTextSize(10);
            avgLimit.setLabelPosition(LimitLine.LimitLabelPosition.POS_LEFT);
            avgLimit.setLineColor(Color.DKGRAY);

            YAxis leftAxis = speedGraph.getAxisLeft();
            leftAxis.setAxisMaxValue(maxSpeed + 0.1f * maxSpeed);
            leftAxis.setAxisMinValue(-maxSpeed * 0.1f);
            leftAxis.removeAllLimitLines();
            leftAxis.addLimitLine(avgLimit);
            leftAxis.addLimitLine(topLimit);
            leftAxis.addLimitLine(bottomLimit);
        }

        ArrayList<String> xValues = new ArrayList<>();
        ArrayList<Entry> yValues = new ArrayList<>();

        for(SpeedPoint point : speedPoints) {
            xValues.add(String.valueOf(point.getSerialNumber()));
            yValues.add(new Entry((float) point.getValue(), point.getSerialNumber() - 1));
        }

        LineDataSet speedDataSet = new LineDataSet(yValues, "Prędkość");
        speedDataSet.setFillAlpha(110);
        speedDataSet.setFillColor(Color.RED);
        speedDataSet.setColor(Color.BLACK);
        speedDataSet.setCircleColor(Color.BLACK);
        speedDataSet.setLineWidth(1);
        speedDataSet.setCircleSize(2);
        speedDataSet.setDrawCircleHole(true);
        speedDataSet.setValueTextSize(0);
        speedDataSet.setFillAlpha(65);
        speedDataSet.setFillColor(Color.BLACK);
        speedDataSet.setDrawFilled(true);

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(speedDataSet);

        LineData data = new LineData(xValues, dataSets);

        speedGraph.setData(data);
        speedGraph.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_speed_graph, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
            return true;

        return super.onOptionsItemSelected(item);
    }

    private SpeedGraphActivityComponent getSpeedGraphActivityComponent() {
        return SpeedGraphActivityComponent.Initializer.init(this);
    }
}
