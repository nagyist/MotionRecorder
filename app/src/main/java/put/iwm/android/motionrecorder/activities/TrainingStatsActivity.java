package put.iwm.android.motionrecorder.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import put.iwm.android.motionrecorder.R;
import put.iwm.android.motionrecorder.base.BaseActivity;
import put.iwm.android.motionrecorder.database.repository.TrainingRepository;
import put.iwm.android.motionrecorder.fragments.TextGenerator;
import put.iwm.android.motionrecorder.training.Training;

public class TrainingStatsActivity extends BaseActivity {

    private Training training;
    private TextView startDateTextView;
    private TextView finishDateTextView;
    private TextView durationTextView;
    private TextView distanceTextView;
    private TextView maxSpeedTextView;
    private TextView avgSpeedTextView;
    private Button showRouteButton;
    @Inject TextGenerator textGenerator;
    @Inject TrainingRepository trainingRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);

        setContentView(R.layout.activity_training_stats);

        setupUIReferences();
        setupEventHandlers();
        setupTraining();
        fillUI();
    }

    private void setupTraining() {

        Bundle arguments = getIntent().getExtras();
        long trainingId = arguments.getLong("trainingId");

        training = trainingRepository.findById((int)trainingId);
    }

    private void setupUIReferences() {

        startDateTextView = (TextView) findViewById(R.id.training_start_date);
        finishDateTextView = (TextView) findViewById(R.id.training_finish_date);
        durationTextView = (TextView) findViewById(R.id.training_duration);
        distanceTextView = (TextView) findViewById(R.id.training_distance);
        maxSpeedTextView = (TextView) findViewById(R.id.training_max_speed);
        avgSpeedTextView = (TextView) findViewById(R.id.training_avg_speed);
        showRouteButton = (Button) findViewById(R.id.show_route_button);
    }

    private void setupEventHandlers() {

        showRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToRouteMapActivity(training.getId());
            }

            private void redirectToRouteMapActivity(long trainingId) {

                Intent intent = new Intent(getApplicationContext(), RouteMapActivity.class);

                Bundle arguments = new Bundle();
                arguments.putLong("trainingId", trainingId);

                intent.putExtras(arguments);
                startActivity(intent);
            }
        });
    }

    private void fillUI() {

        startDateTextView.setText(textGenerator.createDateText(training.getStartDate()));
        finishDateTextView.setText(textGenerator.createDateText(training.getFinishDate()));
        durationTextView.setText(textGenerator.createTimerText(training.getDurationTime()));
        distanceTextView.setText(textGenerator.createDistanceText(training.getTotalDistance()));
        maxSpeedTextView.setText(textGenerator.createSpeedText(training.getMaxSpeed()));
        avgSpeedTextView.setText(textGenerator.createSpeedText(training.getAvgSpeed()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_training_stats, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings)
            return true;

        if(id == R.id.action_logout)
            logout();

        if(id == R.id.action_delete)
            showDeleteConfirmDialog();

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        sessionManager.logoutUser();
        redirectToAuthenticationActivity();
    }

    private void showDeleteConfirmDialog() {
        deleteTraining();
    }

    private void deleteTraining() {
        trainingRepository.deleteById((int)training.getId());
        finish();
    }
}
