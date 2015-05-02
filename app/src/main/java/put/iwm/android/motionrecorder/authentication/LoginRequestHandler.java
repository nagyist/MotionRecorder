package put.iwm.android.motionrecorder.authentication;

import put.iwm.android.motionrecorder.authentication.LoginRequest;
import put.iwm.android.motionrecorder.authentication.LoginResponse;

/**
 * Created by Szymon on 2015-04-29.
 */
public interface LoginRequestHandler {

    public LoginResponse performLoginRequest(LoginRequest request);
}
