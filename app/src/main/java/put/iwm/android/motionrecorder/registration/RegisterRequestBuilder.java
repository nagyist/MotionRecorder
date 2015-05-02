package put.iwm.android.motionrecorder.registration;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Szymon on 2015-05-01.
 */
public interface RegisterRequestBuilder {

    public JSONObject buildJsonRegisterRequestFromObject(RegisterRequest registerRequest) throws JSONException;
}
