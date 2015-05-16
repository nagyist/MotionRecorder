package put.iwm.android.motionrecorder.database.entity;

import io.realm.RealmObject;

/**
 * Created by Szymon on 2015-05-16.
 */
public class DoubleEntity extends RealmObject {

    private double value;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
