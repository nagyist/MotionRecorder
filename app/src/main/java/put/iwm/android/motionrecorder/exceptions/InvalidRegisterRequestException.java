package put.iwm.android.motionrecorder.exceptions;

/**
 * Created by Szymon on 2015-04-08.
 */
public class InvalidRegisterRequestException extends RuntimeException {

    public InvalidRegisterRequestException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
