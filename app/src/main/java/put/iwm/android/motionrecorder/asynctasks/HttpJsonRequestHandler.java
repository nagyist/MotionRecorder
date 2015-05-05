package put.iwm.android.motionrecorder.asynctasks;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import put.iwm.android.motionrecorder.authentication.LoginRequest;
import put.iwm.android.motionrecorder.authentication.LoginRequestBuilder;
import put.iwm.android.motionrecorder.authentication.LoginRequestBuilderImpl;
import put.iwm.android.motionrecorder.authentication.LoginRequestHandler;
import put.iwm.android.motionrecorder.authentication.LoginResponse;
import put.iwm.android.motionrecorder.authentication.LoginResponseBuilder;
import put.iwm.android.motionrecorder.authentication.LoginResponseBuilderImpl;
import put.iwm.android.motionrecorder.registration.RegisterRequest;
import put.iwm.android.motionrecorder.registration.RegisterRequestBuilder;
import put.iwm.android.motionrecorder.registration.RegisterRequestBuilderImpl;
import put.iwm.android.motionrecorder.registration.RegisterRequestHandler;
import put.iwm.android.motionrecorder.registration.RegisterResponse;
import put.iwm.android.motionrecorder.registration.RegisterResponseBuilder;
import put.iwm.android.motionrecorder.registration.RegisterResponseBuilderImpl;

/**
 * Created by Szymon on 2015-04-29.
 */
public class HttpJsonRequestHandler implements LoginRequestHandler, RegisterRequestHandler {

    private String loginAddress;
    private String registerAddress;
    private HttpClient httpClient;
    private HttpPost httpPost;
    private LoginRequestBuilder loginRequestBuilder = new LoginRequestBuilderImpl();
    private LoginResponseBuilder loginResponseBuilder = new LoginResponseBuilderImpl();
    private RegisterRequestBuilder registerRequestBuilder = new RegisterRequestBuilderImpl();
    private RegisterResponseBuilder registerResponseBuilder = new RegisterResponseBuilderImpl();

    public HttpJsonRequestHandler() {

        loginAddress = "https://motion-recorder-server.herokuapp.com/login";
        registerAddress = "https://motion-recorder-server.herokuapp.com//register";
        httpClient = new DefaultHttpClient();
    }

    public LoginResponse performLoginRequest(LoginRequest request) {

        try {
            return tryPerformLoginRequest(request);
        } catch (IOException e) {
            return new LoginResponse(request.getUsername(), false, "Wystąpił błąd połączenia z serwerem");
        } catch (JSONException e) {
            return new LoginResponse(request.getUsername(), false, "Wystąpił błąd przetwarzania żądania");
        }
    }

    private LoginResponse tryPerformLoginRequest(LoginRequest request) throws IOException, JSONException {

        httpPost = new HttpPost(loginAddress);

        JSONObject jsonRequest = loginRequestBuilder.buildJsonLoginRequestFromObject(request);
        JSONObject jsonResponse = performJsonRequest(jsonRequest);
        return loginResponseBuilder.buildLoginResponseFromJsonObject(jsonResponse);
    }

    @Override
    public RegisterResponse performRegisterRequest(RegisterRequest request) {

        try {
            return tryPerformRegisterRequest(request);
        } catch (IOException e) {
            return new RegisterResponse(false, "Wystąpił błąd połączenia z serwerem");
        } catch (JSONException e) {
            return new RegisterResponse(false, "Wystąpił błąd przetwarzania żądania");
        }
    }

    private RegisterResponse tryPerformRegisterRequest(RegisterRequest request) throws IOException, JSONException {

        httpPost = new HttpPost(registerAddress);

        JSONObject jsonRequest = registerRequestBuilder.buildJsonRegisterRequestFromObject(request);
        JSONObject jsonResponse = performJsonRequest(jsonRequest);
        return registerResponseBuilder.buildRegisterResponseFromJsonObject(jsonResponse);
    }

    private JSONObject performJsonRequest(JSONObject jsonRequest) throws IOException, JSONException {

        StringEntity entity = new StringEntity(jsonRequest.toString(), HTTP.UTF_8);
        entity.setContentType("application/json");
        entity.setContentEncoding(HTTP.UTF_8);

        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        HttpResponse httpResponse = httpClient.execute(httpPost);
        JSONObject jsonResponse = buildJsonFromHttpResponse(httpResponse);

        return jsonResponse;
    }

    private JSONObject buildJsonFromHttpResponse(HttpResponse httpResponse) throws IOException, JSONException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line;

        while ((line = bufferedReader.readLine()) != null)
            result.append(line);

        JSONObject jsonObject = new JSONObject(result.toString());

        return jsonObject;
    }
}
