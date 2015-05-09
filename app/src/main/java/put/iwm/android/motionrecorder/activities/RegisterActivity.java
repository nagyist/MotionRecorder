package put.iwm.android.motionrecorder.activities;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

import put.iwm.android.motionrecorder.R;
import put.iwm.android.motionrecorder.base.BaseActivity;
import put.iwm.android.motionrecorder.exceptions.InvalidRegisterRequestException;
import put.iwm.android.motionrecorder.fragments.RegisterFragment;
import put.iwm.android.motionrecorder.registration.RegisterRequest;
import put.iwm.android.motionrecorder.registration.RegisterResponse;
import put.iwm.android.motionrecorder.registration.RegisterResponseReceiver;
import put.iwm.android.motionrecorder.registration.RegistrationService;
import put.iwm.android.motionrecorder.registration.RegistrationServiceImpl;

public class RegisterActivity extends BaseActivity implements RegisterFragment.OnRegisterFragmentInteractionListener, RegisterResponseReceiver {

    @Inject
    RegistrationService registrationService;
    private Fragment registerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);

        setContentView(R.layout.activity_register);

        registrationService.setRegisterResponseReceiver(this);

        if (savedInstanceState == null)
            setupFragment();
    }
    private void setupFragment() {

        registerFragment = new RegisterFragment();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, registerFragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onRegisterFragmentInteraction(RegisterRequest registerRequest) {

        processRegisterRequest(registerRequest);
    }

    private void processRegisterRequest(RegisterRequest registerRequest) {

        try {
            tryProcessRegisterRequest(registerRequest);
        } catch (InvalidRegisterRequestException e) {
            Toast.makeText(RegisterActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void tryProcessRegisterRequest(RegisterRequest registerRequest) throws InvalidRegisterRequestException {

        registrationService.processRegisterRequest(registerRequest);
    }

    @Override
    public void processRegisterResponse(RegisterResponse response) {

        Toast.makeText(RegisterActivity.this, response.getMessage(), Toast.LENGTH_LONG).show();

        if(response.isRegisterSuccessful()) {
            redirectToAuthenticationActivity();
            finish();
        }
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

    public static class PlaceholderFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_register, container, false);

            return rootView;
        }
    }
}
