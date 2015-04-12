package put.iwm.android.motionrecorder.registration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import put.iwm.android.motionrecorder.exceptions.InvalidRegisterRequestException;

/**
 * Created by Szymon on 2015-04-08.
 */
public class RegistrationServiceImpl implements RegistrationService {

    private List<String> usernames = new ArrayList<String>() {{
        add("czako");
        add("szymie");
    }};

    private RegisterRequestValidator registerRequestValidator = new RegisterRequestValidatorImpl();

    @Override
    public void processRegisterRequest(RegisterRequest registerRequest) throws InvalidRegisterRequestException {

        validateRegisterRequest(registerRequest);

        //TODO
        usernames.add(registerRequest.getUsername());
    }

    public void validateRegisterRequest(RegisterRequest registerRequest) throws InvalidRegisterRequestException {

        registerRequestValidator.validate(registerRequest);

        if(!isUsernameAvailable(registerRequest.getUsername()))
            throw new InvalidRegisterRequestException(String.format("Użytkownik '%1' już istnieje", registerRequest.getUsername()));
    }

    public boolean isUsernameAvailable(String username) {

        if(usernames.contains(username))
            return false;
        else
            return true;
    }

}
