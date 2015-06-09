package put.iwm.android.motionrecorder.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Szymon on 2015-06-09.
 */
@Scope
@Retention(RUNTIME)
public @interface PerActivity {
}