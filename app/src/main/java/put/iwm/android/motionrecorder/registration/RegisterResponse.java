package put.iwm.android.motionrecorder.registration;

/**
 * Created by Szymon on 2015-05-01.
 */
public class RegisterResponse {

    private boolean registerSuccessful;
    private String message;

    public RegisterResponse(boolean registerSuccessful, String message) {
        this.registerSuccessful = registerSuccessful;
        this.message = message;
    }

    public boolean isRegisterSuccessful() {
        return registerSuccessful;
    }

    public void setRegisterSuccessful(boolean registerSuccessful) {
        this.registerSuccessful = registerSuccessful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
