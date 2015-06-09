package put.iwm.android.motionrecorder.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import javax.inject.Inject;

import put.iwm.android.motionrecorder.R;
import put.iwm.android.motionrecorder.base.BaseActivity;
import put.iwm.android.motionrecorder.di.TrainingStatsActivityComponent;
import put.iwm.android.motionrecorder.fragments.ConfirmDialogFragment;
import put.iwm.android.motionrecorder.presenters.TrainingPresenter;
import put.iwm.android.motionrecorder.views.TrainingStatsView;

public class TrainingStatsActivity extends BaseActivity implements TrainingStatsView, ConfirmDialogFragment.NoticeDialogListener {

    private TextView startDateTextView;
    private TextView finishDateTextView;
    private TextView durationTextView;
    private TextView distanceTextView;
    private TextView maxSpeedTextView;
    private TextView avgSpeedTextView;
    private Button showRouteButton;
    private Button showSpeedGraphButton;
    private ConfirmDialogFragment confirmDialog;
    @Inject TrainingPresenter trainingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getTrainingStatsActivityComponent().inject(this);

        setContentView(R.layout.activity_training_stats);

        setupUIReferences();
        setupEventHandlers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        trainingPresenter.onResume(getTrainingId());
    }

    private long getTrainingId() {
        return getIntent().getExtras().getLong("trainingId");
    }

    @Override
    public void setTrainingStatsData(Map<String, String> model) {

        startDateTextView.setText(model.get("startDate"));
        finishDateTextView.setText(model.get("finishDate"));
        durationTextView.setText(model.get("duration"));
        distanceTextView.setText(model.get("distance"));
        maxSpeedTextView.setText(model.get("maxSpeed"));
        avgSpeedTextView.setText(model.get("avgSpeed"));
    }

    private void setupUIReferences() {

        startDateTextView = (TextView) findViewById(R.id.training_start_date);
        finishDateTextView = (TextView) findViewById(R.id.training_finish_date);
        durationTextView = (TextView) findViewById(R.id.training_duration);
        distanceTextView = (TextView) findViewById(R.id.training_distance);
        maxSpeedTextView = (TextView) findViewById(R.id.training_max_speed);
        avgSpeedTextView = (TextView) findViewById(R.id.training_avg_speed);
        showRouteButton = (Button) findViewById(R.id.show_route_button);
        showSpeedGraphButton = (Button) findViewById(R.id.show_speed_graph_button);
    }

    private void setupEventHandlers() {

        showRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToRouteMapActivity(getTrainingId());
            }

            private void redirectToRouteMapActivity(long trainingId) {

                Intent intent = new Intent(getApplicationContext(), RouteMapActivity.class);

                Bundle arguments = new Bundle();
                arguments.putLong("trainingId", trainingId);

                intent.putExtras(arguments);
                startActivity(intent);
            }
        });

        showSpeedGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToSpeedGraphActivity(getTrainingId());
            }

            private void redirectToSpeedGraphActivity(long trainingId) {

                Intent intent = new Intent(getApplicationContext(), SpeedGraphActivity.class);

                Bundle arguments = new Bundle();
                arguments.putLong("trainingId", trainingId);

                intent.putExtras(arguments);
                startActivity(intent);
            }
        });
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
        confirmDialog = new ConfirmDialogFragment("Czy chcesz usunąć ten trening?", "Tak", "Nie");
        confirmDialog.show(getFragmentManager(), "Delete training confirm dialog");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        deleteTraining();
    }

    private void deleteTraining() {
        trainingPresenter.deleteTraining(getTrainingId());
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onDeleteTrainingSuccess() {
        Toast.makeText(this, "Trening został pomyślnie usunięty", Toast.LENGTH_LONG).show();
        finish();
    }

    private TrainingStatsActivityComponent getTrainingStatsActivityComponent() {
        return TrainingStatsActivityComponent.Initializer.init(this);
    }
}
