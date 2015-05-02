package put.iwm.android.motionrecorder.authentication;

import java.util.Map;

/**
 * Created by Szymon on 2015-04-28.
 */
public class LoginResponse {

    private String username;
    private boolean loginSuccessful;
    private String message;

    public LoginResponse(String username, boolean loginSuccessful, String message) {
        this.username = username;
        this.loginSuccessful = loginSuccessful;
        this.message = message;
    }

    public LoginResponse(Map<String, String> map) {

        username = map.get("username");
        loginSuccessful = Boolean.valueOf(map.get("loginSuccessful"));
        message = map.get("message");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }

    public void setLoginSuccessful(boolean loginSuccessful) {
        this.loginSuccessful = loginSuccessful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
