package put.iwm.android.motionrecorder.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import put.iwm.android.motionrecorder.fragments.TextGenerator;

/**
 * Created by Szymon on 2015-05-06.
 */
@Module
public class TextGeneratorModule {

    @Provides
    @Singleton
    public TextGenerator provideTextGenerator() {
        return new TextGenerator();
    }
}
