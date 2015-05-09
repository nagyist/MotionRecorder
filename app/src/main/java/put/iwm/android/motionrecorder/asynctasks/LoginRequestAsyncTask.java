package put.iwm.android.motionrecorder.asynctasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.lang.*;

import put.iwm.android.motionrecorder.authentication.LoginRequest;
import put.iwm.android.motionrecorder.authentication.LoginRequestHandler;
import put.iwm.android.motionrecorder.authentication.LoginResponse;
import put.iwm.android.motionrecorder.authentication.LoginResponseReceiver;

/**
 * Created by Szymon on 2015-04-28.
 */
public class LoginRequestAsyncTask extends AsyncTask<LoginRequest, Boolean, LoginResponse> {

    private LoginResponseReceiver loginResponseReceiver;
    private NetworkConnection networkConnection;
    private LoginRequest request;
    private LoginRequestHandler loginRequestHandler;
    private ProgressDialog progressDialog;
    private Context context;

    public LoginRequestAsyncTask(Context context) {
        this.context = context;
        networkConnection = new NetworkConnectionImpl(context);
        loginRequestHandler = new HttpJsonRequestHandler();
    }

    public LoginRequestAsyncTask(Context context, LoginResponseReceiver loginResponseReceiver) {
        this.loginResponseReceiver = loginResponseReceiver;
        this.context = context;
        networkConnection = new NetworkConnectionImpl(context);
        loginRequestHandler = new HttpJsonRequestHandler();
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        setupProgressDialog();
        progressDialog.show();
    }

    private void setupProgressDialog() {

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Ładowanie...");
        progressDialog.setTitle("Sprawdzanie połączenia z internetem");
        progressDialog.setCancelable(false);
    }

    @Override
    protected LoginResponse doInBackground(LoginRequest... requests) {

        request = requests[0];

        if(networkConnection.isNetworkAvailable())
            return performLoginRequest();
        else
            return new LoginResponse(request.getUsername(), false, "Nie udało się nawiązać połączenia z internetem");
    }

    private LoginResponse performLoginRequest() {
        publishProgress(true);
        return loginRequestHandler.performLoginRequest(request);
    }

    @Override
    protected void onProgressUpdate(Boolean... values) {
        super.onProgressUpdate(values);

        if(values[0])
            progressDialog.setTitle("Przetwarzanie żądania");
    }

    @Override
    protected void onPostExecute(LoginResponse response) {

        super.onPostExecute(response);
        progressDialog.dismiss();
        loginResponseReceiver.processLoginResponse(response);
    }

    public void setLoginResponseReceiver(LoginResponseReceiver loginResponseReceiver) {
        this.loginResponseReceiver = loginResponseReceiver;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
