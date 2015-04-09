package put.iwm.android.motionrecorder.session;

/**
 * Created by Szymon on 2015-04-06.
 */
public interface UserSessionManager {

    public void createUserSession(String username);
    public void logoutUser();
    public boolean isUserLoggedIn();
}
