package put.iwm.android.motionrecorder.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import put.iwm.android.motionrecorder.activities.AuthenticationActivity;
import put.iwm.android.motionrecorder.di.ActivityComponent;
import put.iwm.android.motionrecorder.di.ActivityModule;
import put.iwm.android.motionrecorder.di.DaggerActivityComponent;
import put.iwm.android.motionrecorder.session.UserSessionManager;
import put.iwm.android.motionrecorder.session.UserSessionManagerImpl;

/**
 * Created by Szymon on 2015-04-06.
 */
public abstract class BaseActivity extends Activity {

    protected UserSessionManager sessionManager;
    protected ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setupInjector();

        sessionManager = new UserSessionManagerImpl(getApplicationContext());
    }

    private void setupInjector() {
        activityComponent = ActivityComponent.Initializer.init(this);
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

    protected ActivityComponent getActivityComponent() {
        return activityComponent;
    }
}
