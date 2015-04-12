package put.iwm.android.motionrecorder.baseactivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import put.iwm.android.motionrecorder.activities.AuthenticationActivity;
import put.iwm.android.motionrecorder.session.UserSessionManager;
import put.iwm.android.motionrecorder.session.UserSessionManagerImpl;

/**
 * Created by Szymon on 2015-04-06.
 */
public abstract class BaseActivity extends ActionBarActivity {

    protected UserSessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        sessionManager = new UserSessionManagerImpl(getApplicationContext());
    }

    protected void redirectToAuthenticationActivity() {

        Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

        finish();
    }

    protected void redirectToAuthenticationActivityIfNeeded() {
        if(!sessionManager.isUserLoggedIn())
            redirectToAuthenticationActivity();
    }



}
