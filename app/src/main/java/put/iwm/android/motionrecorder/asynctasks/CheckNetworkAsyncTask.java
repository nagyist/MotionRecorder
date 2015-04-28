package put.iwm.android.motionrecorder.asynctasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;
import java.lang.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Szymon on 2015-04-28.
 */
public class CheckNetworkAsyncTask extends AsyncTask<Void, Void, Boolean> {

    private ProgressDialog progressDialog;
    private Context context;
    private ConnectivityManager connectivityManager;
    private NetworkInfo activeNetworkInfo;

    public CheckNetworkAsyncTask(Context context) {
        this.context = context;
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
        //progressDialog.setIndeterminate(false);
        //progressDialog.setCancelable(true);
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        return checkNetwork();
    }

    private boolean checkNetwork() {

        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if(isConnectionPossible())
            return checkServerUrlConnection();
        else
            return false;
    }

    private boolean isConnectionPossible() {
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private boolean checkServerUrlConnection() {

        //TODO
        try {

            URL url = new URL("http://www.google.com");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3000);

            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200)
                return true;
            else
                return false;

        } catch (IOException e) {
            return false;
        }
    }

    //@Override
    //protected void onProgressUpdate(Void... values) {
    //    super.onProgressUpdate(values);
    //}

    @Override
    protected void onPostExecute(Boolean result) {

        super.onPostExecute(result);

        progressDialog.dismiss();

        //TODO
        if(result == true)
            Toast.makeText(context, "Robię coś", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, "Nie udało się nawiązać połączenia", Toast.LENGTH_LONG).show();


    }
}
