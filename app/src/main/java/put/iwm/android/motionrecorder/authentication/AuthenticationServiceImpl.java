package put.iwm.android.motionrecorder.authentication;

import java.util.HashMap;
import java.util.Map;

import put.iwm.android.motionrecorder.exceptions.InvalidLoginRequestException;
import put.iwm.android.motionrecorder.exceptions.NoSuchUserFoundException;

/**
 * Created by Szymon on 2015-04-04.
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    private Map<String, String> usernamesAndPasswords;
    private LoginRequestValidator loginRequestValidator = new LoginRequestValidatorImpl();

    public AuthenticationServiceImpl() {
        usernamesAndPasswords = new HashMap<String, String>() {{
            put("szymie", "czako");
            put("czako", "pies");
        }};
    }

    @Override
    public void processLoginRequest(LoginRequest loginRequest) throws InvalidLoginRequestException {

        validateLoginRequest(loginRequest);

        if(!isPasswordCorrect(loginRequest.getUsername(), loginRequest.getPassword()))
            throw new InvalidLoginRequestException("Hasło niepoprawne");
    }

    public void validateLoginRequest(LoginRequest loginRequest) throws InvalidLoginRequestException {

        loginRequestValidator.validate(loginRequest);
    }

    public boolean isPasswordCorrect(String username, String password) {

        String correctPassword = usernamesAndPasswords.get(username);

        if(correctPassword == null)
            throw new InvalidLoginRequestException(String.format("Użytkownik '%1' nie istnieje", username));

        if(correctPassword.equals(password))
            return true;
        else
            return false;
    }



}
