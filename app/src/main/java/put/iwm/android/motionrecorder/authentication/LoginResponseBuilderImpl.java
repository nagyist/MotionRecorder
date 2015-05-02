package put.iwm.android.motionrecorder.authentication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Szymon on 2015-04-30.
 */
public class LoginResponseBuilderImpl implements LoginResponseBuilder {

    @Override
    public LoginResponse buildLoginResponseFromJsonObject(JSONObject jsonObject) throws JSONException {

        String username = jsonObject.getString("username");
        boolean loginSuccessful = jsonObject.getBoolean("loginSuccessful");
        String message = jsonObject.getString("message");

        return new LoginResponse(username, loginSuccessful, message);
    }

    @Override
    public JSONObject buildJsonLoginResponseFromObject(LoginResponse loginResponse) throws JSONException {

        JSONObject result = new JSONObject();

        result.put("username", loginResponse.getUsername());
        result.put("loginSuccessful", loginResponse.isLoginSuccessful());
        result.put("message", loginResponse.getMessage());

        return result;
    }
}
