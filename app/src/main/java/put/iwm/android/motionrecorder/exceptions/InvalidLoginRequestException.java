package put.iwm.android.motionrecorder.exceptions;

/**
 * Created by Szymon on 2015-04-06.
 */
public class InvalidLoginRequestException extends RuntimeException {

    public InvalidLoginRequestException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return getMessage();
    }

}
