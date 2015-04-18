package put.iwm.android.motionrecorder.exceptions;

/**
 * Created by Szymon on 2015-04-15.
 */
public class FragmentNotFoundException extends RuntimeException {

    public FragmentNotFoundException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return getMessage();
    }

}
