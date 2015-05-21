package put.iwm.android.motionrecorder.training;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by Szymon on 2015-05-20.
 */
public class CurrentDateGeneratorImpl implements CurrentDateGenerator {

    private Calendar calendar;

    public CurrentDateGeneratorImpl() {

    }

    public Date generateCurrentDate() {
        return new Date(Calendar.getInstance().getTimeInMillis());
    }
}
