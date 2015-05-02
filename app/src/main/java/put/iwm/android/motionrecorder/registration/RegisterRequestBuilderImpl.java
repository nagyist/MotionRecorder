package put.iwm.android.motionrecorder.registration;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Szymon on 2015-05-01.
 */
public class RegisterRequestBuilderImpl implements RegisterRequestBuilder {

    @Override
    public JSONObject buildJsonRegisterRequestFromObject(RegisterRequest registerRequest) throws JSONException {

        JSONObject result = new JSONObject();

        result.put("username", registerRequest.getUsername());
        result.put("password", registerRequest.getPassword());
        result.put("male", registerRequest.isMale());
        result.put("dateOfBirth", registerRequest.getDateOfBirth());
        result.put("weight", registerRequest.getWeight());
        result.put("height", registerRequest.getHeight());

        return result;
    }
}
