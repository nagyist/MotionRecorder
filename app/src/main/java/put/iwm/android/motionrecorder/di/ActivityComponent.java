package put.iwm.android.motionrecorder.di;

import javax.inject.Singleton;

import dagger.Component;
import put.iwm.android.motionrecorder.activities.AuthenticationActivity;
import put.iwm.android.motionrecorder.base.BaseActivity;

/**
 * Created by Szymon on 2015-05-09.
 */
@Singleton
@Component(modules = {AuthenticationModule.class, RegistrationModule.class})
public interface ActivityComponent extends ActivityDependencyGraph {

    static final class Initializer {

        private Initializer() {
        }

        public static ActivityComponent init(BaseActivity activity) {
            return DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(activity))
                    .build();
        }
    }
}
