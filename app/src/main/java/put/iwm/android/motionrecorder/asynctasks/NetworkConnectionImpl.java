package put.iwm.android.motionrecorder.asynctasks;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Szymon on 2015-04-29.
 */
public class NetworkConnectionImpl implements NetworkConnection {

    private ConnectivityManager connectivityManager;
    private NetworkInfo activeNetworkInfo;
    private Context context;

    public NetworkConnectionImpl(Context context) {
        this.context = context;
    }

    public boolean isNetworkAvailable() {

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

        try {
            return tryCheckServerUrlConnection();
        } catch (IOException e) {
            return false;
        }
    }

    private boolean tryCheckServerUrlConnection() throws IOException {

        URL url = new URL("http://www.google.com");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(3000);

        urlConnection.connect();

        if(urlConnection.getResponseCode() == 200)
            return true;
        else
            return false;
    }
}
