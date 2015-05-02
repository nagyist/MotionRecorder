package put.iwm.android.motionrecorder.registration;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Szymon on 2015-05-01.
 */
public interface RegisterResponseBuilder {

    public RegisterResponse buildRegisterResponseFromJsonObject(JSONObject jsonObject) throws JSONException;
}
