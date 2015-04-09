package put.iwm.android.motionrecorder.authentication;

import put.iwm.android.motionrecorder.exceptions.InvalidLoginRequestException;

/**
 * Created by Szymon on 2015-04-06.
 */
public interface LoginRequestValidator {

    public void validate(LoginRequest loginRequest) throws InvalidLoginRequestException;
}
