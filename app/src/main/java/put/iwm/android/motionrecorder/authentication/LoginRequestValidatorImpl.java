package put.iwm.android.motionrecorder.authentication;

import put.iwm.android.motionrecorder.exceptions.InvalidLoginRequestException;

/**
 * Created by Szymon on 2015-04-06.
 */
public class LoginRequestValidatorImpl implements LoginRequestValidator {

    public LoginRequestValidatorImpl() {

    }

    public void validate(LoginRequest loginRequest) throws InvalidLoginRequestException {

       if(loginRequest.getUsername().trim().isEmpty() && loginRequest.getPassword().trim().isEmpty())
            throw new InvalidLoginRequestException("Formularz jest pusty");

        if(loginRequest.getUsername().trim().isEmpty())
            throw new InvalidLoginRequestException("Pole 'nazwa użytkownika' jest puste");

        if(loginRequest.getPassword().trim().isEmpty() && loginRequest.getPassword().trim().isEmpty())
            throw new InvalidLoginRequestException("Pole 'hasło' jest puste");
    }



}
