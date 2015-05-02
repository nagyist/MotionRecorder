package put.iwm.android.motionrecorder.registration;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Szymon on 2015-05-01.
 */
public class RegisterResponseBuilderImpl implements RegisterResponseBuilder {

    @Override
    public RegisterResponse buildRegisterResponseFromJsonObject(JSONObject jsonObject) throws JSONException {


        boolean registerSuccessful = jsonObject.getBoolean("registerSuccessful");
        String message = jsonObject.getString("message");

        return new RegisterResponse(registerSuccessful, message);
    }
}
