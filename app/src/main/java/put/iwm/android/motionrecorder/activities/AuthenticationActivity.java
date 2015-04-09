package put.iwm.android.motionrecorder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import put.iwm.android.motionrecorder.R;
import put.iwm.android.motionrecorder.authentication.AuthenticationService;
import put.iwm.android.motionrecorder.authentication.AuthenticationServiceImpl;
import put.iwm.android.motionrecorder.authentication.LoginRequest;
import put.iwm.android.motionrecorder.authentication.LoginRequestValidator;
import put.iwm.android.motionrecorder.authentication.LoginRequestValidatorImpl;
import put.iwm.android.motionrecorder.exceptions.InvalidLoginRequestException;
import put.iwm.android.motionrecorder.exceptions.NoSuchUserFoundException;
import put.iwm.android.motionrecorder.baseactivities.CustomActionBarActivity;


public class AuthenticationActivity extends CustomActionBarActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;

    private AuthenticationService authenticationService = new AuthenticationServiceImpl();
    private LoginRequestValidator loginRequestValidator = new LoginRequestValidatorImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        startMainActivityIfPossible();

        setContentView(R.layout.activity_authentication);

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);

        setupEventHandlers();
    }

    private void setupEventHandlers() {

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginButtonClicked(v);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerButtonClicked(v);
            }
        });

    }

    private void loginButtonClicked(View v) {

        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        LoginRequest loginRequest = new LoginRequest(username, password);

        try {
            validateLoginRequest(loginRequest);
            processLoginRequest(loginRequest);
        } catch (NoSuchUserFoundException e) {
            Toast.makeText(AuthenticationActivity.this, getString(R.string.user_not_found, username), Toast.LENGTH_LONG).show();
        } catch (InvalidLoginRequestException e) {
            Toast.makeText(AuthenticationActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void registerButtonClicked(View v) {

        startRegisterActivity();
    }

    private void processLoginRequest(LoginRequest loginRequest) {

        if(checkUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword())) {
            updateSessionData(loginRequest.getUsername());
            startMainActivity();
            finish();
        } else {
            Toast.makeText(AuthenticationActivity.this, getString(R.string.wrong_password, loginRequest.getUsername()), Toast.LENGTH_LONG).show();
        }
    }

    private void validateLoginRequest(LoginRequest loginRequest) {

        loginRequestValidator.validate(loginRequest);
    }

    private boolean checkUsernameAndPassword(String username, String password) {

        return authenticationService.checkUsernameAndPassword(username, password);
    }

    private void updateSessionData(String username) {

        sessionManager.createUserSession(username);
    }

    private void startMainActivityIfPossible() {

        if (sessionManager.isUserLoggedIn()) {
            startMainActivity();
            finish();
        }
    }


    private void startMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }

    private void startRegisterActivity() {

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_authentication, menu);
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
}
