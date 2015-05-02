package put.iwm.android.motionrecorder.asynctasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import put.iwm.android.motionrecorder.registration.RegisterRequest;
import put.iwm.android.motionrecorder.registration.RegisterRequestHandler;
import put.iwm.android.motionrecorder.registration.RegisterResponse;
import put.iwm.android.motionrecorder.registration.RegisterResponseReceiver;

/**
 * Created by Szymon on 2015-05-01.
 */
public class RegisterRequestAsyncTask extends AsyncTask<RegisterRequest, Boolean, RegisterResponse> {

    private RegisterResponseReceiver registerResponseReceiver;
    private NetworkConnection networkConnection;
    private RegisterRequest request;
    private RegisterRequestHandler registerRequestHandler;
    private ProgressDialog progressDialog;
    private Context context;

    public RegisterRequestAsyncTask(Context context, RegisterResponseReceiver registerResponseReceiver) {
        this.registerResponseReceiver = registerResponseReceiver;
        this.context = context;
        networkConnection = new NetworkConnectionImpl(context);
        registerRequestHandler = new HttpJsonRequestHandler();
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
    protected RegisterResponse doInBackground(RegisterRequest... requests) {

        request = requests[0];

        if(networkConnection.isNetworkAvailable())
            return performRegisterRequest();
        else
            return new RegisterResponse(false, "Nie udało się nawiązać połączenia z internetem");
    }

    private RegisterResponse performRegisterRequest() {
        publishProgress(true);
        return registerRequestHandler.performRegisterRequest(request);
    }

    @Override
    protected void onProgressUpdate(Boolean... values) {
        super.onProgressUpdate(values);

        if(values[0])
            progressDialog.setTitle("Przetwarzanie żądania");
    }

    @Override
    protected void onPostExecute(RegisterResponse response) {

        super.onPostExecute(response);
        progressDialog.dismiss();
        registerResponseReceiver.processRegisterResponse(response);
    }

    public void setRegisterResponseReceiver(RegisterResponseReceiver registerResponseReceiver) {
        this.registerResponseReceiver = registerResponseReceiver;
    }
}
