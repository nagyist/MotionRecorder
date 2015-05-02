package put.iwm.android.motionrecorder.authentication;

import org.json.JSONException;
import org.json.JSONObject;

import put.iwm.android.motionrecorder.authentication.LoginRequest;

/**
 * Created by Szymon on 2015-04-30.
 */
public interface LoginRequestBuilder {

    public LoginRequest buildLoginRequestFromJsonObject(JSONObject jsonObject) throws JSONException;
    public JSONObject buildJsonLoginRequestFromObject(LoginRequest loginRequest) throws JSONException;
}
