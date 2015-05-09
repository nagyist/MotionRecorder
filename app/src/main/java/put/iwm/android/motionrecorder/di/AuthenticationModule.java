package put.iwm.android.motionrecorder.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import put.iwm.android.motionrecorder.asynctasks.LoginRequestAsyncTask;
import put.iwm.android.motionrecorder.authentication.AuthenticationService;
import put.iwm.android.motionrecorder.authentication.AuthenticationServiceImpl;
import put.iwm.android.motionrecorder.authentication.LoginRequestValidator;
import put.iwm.android.motionrecorder.authentication.LoginRequestValidatorImpl;

/**
 * Created by Szymon on 2015-05-08.
 */
@Module(includes = {ActivityModule.class})
public class AuthenticationModule {

    @Provides
    @Singleton
    public AuthenticationService provideAuthenticationService(Context context, LoginRequestValidator validator) {
        return new AuthenticationServiceImpl(context, validator);
    }

    @Provides
    @Singleton
    public LoginRequestValidator provideLoginRequestValidator() {
        return new LoginRequestValidatorImpl();
    }
}
