package put.iwm.android.motionrecorder.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import put.iwm.android.motionrecorder.session.UserSessionManager;

/**
 * Created by Szymon on 2015-04-06.
 */
public class UserSessionManagerImpl implements UserSessionManager {

    private SharedPreferences sharedPreferences;
    private Editor sharedPreferencesEditor;
    private Context applicationContext;

    private static final int PRIVATE_MODE = 0;
    private static final String PREFERENCES_NAME = "MotionRecorder";
    private static final String USERNAME = "username";
    private static final String IS_USER_LOGGED = "is_user_logged";

    public UserSessionManagerImpl(Context context) {

        applicationContext = context;
        sharedPreferences = applicationContext.getSharedPreferences(PREFERENCES_NAME, PRIVATE_MODE);
        sharedPreferencesEditor = sharedPreferences.edit();
    }

    public void createUserSession(String username) {

        sharedPreferencesEditor.putString(USERNAME, username);
        sharedPreferencesEditor.putBoolean(IS_USER_LOGGED, true);
        sharedPreferencesEditor.commit();
    }

    public void logoutUser() {

        sharedPreferencesEditor.clear();
        sharedPreferencesEditor.commit();
    }

    public boolean isUserLoggedIn(){
        return sharedPreferences.getBoolean(IS_USER_LOGGED, false);
    }


}
