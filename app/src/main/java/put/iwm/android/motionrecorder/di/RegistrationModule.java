package put.iwm.android.motionrecorder.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import put.iwm.android.motionrecorder.registration.RegisterRequestValidator;
import put.iwm.android.motionrecorder.registration.RegisterRequestValidatorImpl;
import put.iwm.android.motionrecorder.registration.RegistrationService;
import put.iwm.android.motionrecorder.registration.RegistrationServiceImpl;

/**
 * Created by Szymon on 2015-05-09.
 */
@Module(includes = {ActivityModule.class})
public class RegistrationModule {

    @Provides
    @Singleton
    public RegistrationService provideRegistrationService(Context context, RegisterRequestValidator registerRequestValidator) {
        return new RegistrationServiceImpl(context, registerRequestValidator);
    }

    @Provides
    @Singleton
    public RegisterRequestValidator provideRegisterRequestValidator() {
        return new RegisterRequestValidatorImpl();
    }

}
