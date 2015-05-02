package put.iwm.android.motionrecorder.authentication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Szymon on 2015-04-30.
 */
public class LoginRequestBuilderImpl implements LoginRequestBuilder {

    @Override
    public LoginRequest buildLoginRequestFromJsonObject(JSONObject jsonObject) throws JSONException {

        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");

        LoginRequest result = new LoginRequest(username, password);

        return result;
    }

    @Override
    public JSONObject buildJsonLoginRequestFromObject(LoginRequest loginRequest) throws JSONException {

        JSONObject result = new JSONObject();

        result.put("username", loginRequest.getUsername());
        result.put("password", loginRequest.getPassword());

        return result;
    }
}
