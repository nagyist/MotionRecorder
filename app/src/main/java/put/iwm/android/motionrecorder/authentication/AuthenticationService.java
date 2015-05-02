package put.iwm.android.motionrecorder.authentication;

import put.iwm.android.motionrecorder.exceptions.InvalidLoginRequestException;

/**
 * Created by Szymon on 2015-04-04.
 */
public interface AuthenticationService {

    public void processLoginRequest(LoginRequest loginRequest) throws InvalidLoginRequestException;
    public void setLoginResponseReceiver(LoginResponseReceiver loginResponseReceiver);
}
