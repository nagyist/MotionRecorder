package put.iwm.android.motionrecorder.registration;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import put.iwm.android.motionrecorder.asynctasks.RegisterRequestAsyncTask;
import put.iwm.android.motionrecorder.exceptions.InvalidRegisterRequestException;

/**
 * Created by Szymon on 2015-04-08.
 */
public class RegistrationServiceImpl implements RegistrationService, RegisterResponseReceiver {

    private RegisterRequestValidator registerRequestValidator = new RegisterRequestValidatorImpl();
    private RegisterResponseReceiver registerResponseReceiver;
    private RegisterRequestAsyncTask registerRequestAsyncTask;
    private Context context;

    public RegistrationServiceImpl(Context context, RegisterResponseReceiver registerResponseReceiver) {

        //TODO
        this.context = context;
        this.registerResponseReceiver = registerResponseReceiver;
    }

    @Override
    public void processRegisterRequest(RegisterRequest registerRequest) throws InvalidRegisterRequestException {

        validateRegisterRequest(registerRequest);

        executeSendRegisterRequest(registerRequest);
    }


    @Override
    public void validateRegisterRequest(RegisterRequest registerRequest) throws InvalidRegisterRequestException {

        registerRequestValidator.validate(registerRequest);
    }

    public void executeSendRegisterRequest(RegisterRequest registerRequest) {

        registerRequestAsyncTask = new RegisterRequestAsyncTask(context, this);
        registerRequestAsyncTask.execute(registerRequest);
    }

    @Override
    public void processRegisterResponse(RegisterResponse response) {
        registerResponseReceiver.processRegisterResponse(response);
    }
}
