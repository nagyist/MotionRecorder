package put.iwm.android.motionrecorder.fragments;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Szymon on 2015-05-06.
 */
public class TextGenerator {

    private DateFormat dataFormat;

    public TextGenerator() {
        dataFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }

    public String createTimerText(long time) {

        long hours = extractHours(time);
        long minutes = extractMinutes(time);
        long seconds = extractSeconds(time);

        String timerText = String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);

        return timerText;
    }

    private long extractHours(long time) {
        return time / 1000 / 60 / 60 % 60;
    }

    private long extractMinutes(long time) {
        return time / 1000 / 60;
    }

    private long extractSeconds(long time) {
        return (time / 1000) % 60;
    }

    private long extractMilliseconds(long time) {
        return time % 1000;
    }

    public String createDistanceText(double distance) {
        String distanceText = String.format(Locale.ENGLISH, "%.2f", distance);
        return distanceText;
    }

    public String createSpeedText(double speed) {
        String speedText = String.format(Locale.ENGLISH, "%.2f", speed);
        return speedText;
    }

    public String createDateText(Date date) {
        return dataFormat.format(date);
    }

}
