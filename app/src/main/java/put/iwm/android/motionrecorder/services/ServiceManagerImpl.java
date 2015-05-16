package put.iwm.android.motionrecorder.services;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Szymon on 2015-05-14.
 */
public class ServiceManagerImpl implements ServiceManager {

    private Intent locationServiceIntent;
    private Context context;

    public ServiceManagerImpl(Context context) {
        this.context = context;
        locationServiceIntent = new Intent(context, LocationListenerService.class);
    }

    @Override
    public void startLocationListenerService() {
        context.startService(locationServiceIntent);
    }

    @Override
    public void stopLocationListenerService() {
        context.stopService(locationServiceIntent);
    }

}
