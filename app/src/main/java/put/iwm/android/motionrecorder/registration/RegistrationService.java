package put.iwm.android.motionrecorder.registration;

import put.iwm.android.motionrecorder.exceptions.InvalidRegisterRequestException;

/**
 * Created by Szymon on 2015-04-08.
 */
public interface RegistrationService {

    public void processRegisterRequest(RegisterRequest registerRequest) throws InvalidRegisterRequestException;
    public void validateRegisterRequest(RegisterRequest registerRequest) throws InvalidRegisterRequestException;
}
