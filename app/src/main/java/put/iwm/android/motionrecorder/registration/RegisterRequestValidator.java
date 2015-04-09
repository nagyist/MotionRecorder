package put.iwm.android.motionrecorder.registration;

import put.iwm.android.motionrecorder.exceptions.InvalidRegisterRequestException;

/**
 * Created by Szymon on 2015-04-08.
 */
public interface RegisterRequestValidator {

    public void validate(RegisterRequest registerRequest) throws InvalidRegisterRequestException;
}
