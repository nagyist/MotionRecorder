package put.iwm.android.motionrecorder.activities;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.sql.Date;

import put.iwm.android.motionrecorder.R;
import put.iwm.android.motionrecorder.baseactivities.CustomActionBarActivity;
import put.iwm.android.motionrecorder.exceptions.InvalidRegisterRequestException;
import put.iwm.android.motionrecorder.registration.RegisterRequest;
import put.iwm.android.motionrecorder.registration.RegistrationService;
import put.iwm.android.motionrecorder.registration.RegistrationServiceImpl;

public class RegisterActivity extends CustomActionBarActivity {

    private RegistrationService registrationService = new RegistrationServiceImpl();

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText repeatPasswordEditText;
    private EditText heightEditText;
    private EditText weightEditText;
    private RadioButton maleRadioButton;
    private DatePicker dateOfBirthPicker;
    private Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText  = (EditText) findViewById(R.id.passwordEditText);
        repeatPasswordEditText = (EditText) findViewById(R.id.passwordRepeatEditText);
        heightEditText = (EditText) findViewById(R.id.height);
        weightEditText = (EditText) findViewById(R.id.weight);
        maleRadioButton = (RadioButton) findViewById(R.id.maleRadioButton);
        dateOfBirthPicker = (DatePicker) findViewById(R.id.dateOfBirthPicker);
        registerButton = (Button) findViewById(R.id.registerRequestButton);



        setupEventHandlers();

    }

    private void setupEventHandlers() {

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerButtonClicked(v);
            }
        });

    }

    private void registerButtonClicked(View v) {

        RegisterRequest registerRequest = constructRegisterRequest();

        try {
            processRegisterRequest(registerRequest);
            redirectToAuthenticationActivity();
            //TODO rejestracja przebiegła pomyślnie
        } catch (InvalidRegisterRequestException e) {
            Toast.makeText(RegisterActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    private RegisterRequest constructRegisterRequest() {

        RegisterRequest registerRequest = new RegisterRequest(
                usernameEditText.getText().toString(),
                passwordEditText.getText().toString(),
                repeatPasswordEditText.getText().toString(),
                maleRadioButton.isChecked(),
                constructDateOfBirth(),
                Integer.parseInt(heightEditText.getText().toString()),
                Integer.parseInt(weightEditText.getText().toString())
        );

        return registerRequest;
    }

    private Date constructDateOfBirth() {

        long date = dateOfBirthPicker.getCalendarView().getDate();

        Date dateOfBirth = new Date(date);

        return dateOfBirth;
    }

    private void processRegisterRequest(RegisterRequest registerRequest) throws InvalidRegisterRequestException {

        registrationService.processRegisterRequest(registerRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_register, container, false);



            return rootView;
        }
    }
}
