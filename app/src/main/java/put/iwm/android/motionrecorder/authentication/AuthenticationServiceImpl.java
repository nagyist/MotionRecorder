package put.iwm.android.motionrecorder.authentication;

import java.util.HashMap;
import java.util.Map;

import put.iwm.android.motionrecorder.exceptions.NoSuchUserFoundException;

/**
 * Created by Szymon on 2015-04-04.
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    private Map<String, String> usernamesAndPasswords;

    public AuthenticationServiceImpl() {
        usernamesAndPasswords = new HashMap<String, String>() {{
            put("szymie", "czako");
            put("czako", "pies");
        }};
    }

    @Override
    public boolean checkUsernameAndPassword(String username, String password) {

        String correctPassword = usernamesAndPasswords.get(username);

        if(correctPassword == null)
            throw new NoSuchUserFoundException();

        return correctPassword.equals(password);
    }

}
