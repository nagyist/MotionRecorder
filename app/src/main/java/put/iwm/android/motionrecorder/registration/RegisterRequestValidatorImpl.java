package put.iwm.android.motionrecorder.registration;

import put.iwm.android.motionrecorder.exceptions.InvalidRegisterRequestException;

/**
 * Created by Szymon on 2015-04-08.
 */
public class RegisterRequestValidatorImpl implements RegisterRequestValidator {

    @Override
    public void validate(RegisterRequest registerRequest) throws InvalidRegisterRequestException {

        if(registerRequest.getUsername().trim().isEmpty())
            throw new InvalidRegisterRequestException("Pole 'nazwa użytkownika' jest puste");

        if(registerRequest.getPassword().isEmpty() || registerRequest.getPasswordRepeat().isEmpty())
            throw new InvalidRegisterRequestException("Pole 'hasło' jest puste");

        if(!registerRequest.getPassword().equals(registerRequest.getPasswordRepeat()))
            throw new InvalidRegisterRequestException("Powtórzone hasło nie jest identyczne");

        if(registerRequest.getWeight() <= 0)
            throw new InvalidRegisterRequestException("Pole 'waga' zawiera niepoprawną wartość");

        if(registerRequest.getWeight() <= 0)
            throw new InvalidRegisterRequestException("Pole 'wzrost' zawiera niepoprawną wartość");
    }
}
