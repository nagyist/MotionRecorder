package put.iwm.android.motionrecorder.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import put.iwm.android.motionrecorder.R;
import put.iwm.android.motionrecorder.application.MotionRecorderApplication;
import put.iwm.android.motionrecorder.authentication.AuthenticationService;
import put.iwm.android.motionrecorder.authentication.LoginRequest;
import put.iwm.android.motionrecorder.authentication.LoginResponse;
import put.iwm.android.motionrecorder.authentication.LoginResponseReceiver;
import put.iwm.android.motionrecorder.base.BaseActivity;
import put.iwm.android.motionrecorder.exceptions.InvalidLoginRequestException;


public class AuthenticationActivity extends BaseActivity implements LoginResponseReceiver {

    @Inject AuthenticationService authenticationService;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);

        startMainActivityIfPossible();

        setContentView(R.layout.activity_authentication);

        authenticationService.setLoginResponseReceiver(this);

        setupUIReferences();
        setupEventHandlers();
    }

    private void setupUIReferences() {

        usernameEditText = (EditText) findViewById(R.id.username_edit_text);
        passwordEditText = (EditText) findViewById(R.id.password_edit_text);
        loginButton = (Button) findViewById(R.id.login_button);
        registerButton = (Button) findViewById(R.id.register_button);
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

        LoginRequest loginRequest = constructLoginRequest();

        try {
            processLoginRequest(loginRequest);
        } catch (InvalidLoginRequestException e) {
            Toast.makeText(AuthenticationActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private LoginRequest constructLoginRequest() {

        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        LoginRequest loginRequest = new LoginRequest(username, password);

        return loginRequest;
    }

    private void processLoginRequest(LoginRequest loginRequest)  throws InvalidLoginRequestException {

        authenticationService.processLoginRequest(loginRequest);
    }

    public void processLoginResponse(LoginResponse response) {

        if(response.isLoginSuccessful()) {
            updateSessionData(response.getUsername());
            startMainActivity();
            finish();
        } else {
            Toast.makeText(AuthenticationActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();
        }
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

    private void registerButtonClicked(View v) {

        startRegisterActivity();
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
