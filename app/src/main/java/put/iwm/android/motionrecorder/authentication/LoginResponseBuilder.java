package put.iwm.android.motionrecorder.authentication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Szymon on 2015-04-30.
 */
public interface LoginResponseBuilder {

    public LoginResponse buildLoginResponseFromJsonObject(JSONObject jsonObject) throws JSONException;
    public JSONObject buildJsonLoginResponseFromObject(LoginResponse loginResponse) throws JSONException;
}
