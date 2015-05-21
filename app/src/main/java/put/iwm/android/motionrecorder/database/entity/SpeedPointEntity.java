package put.iwm.android.motionrecorder.database.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import put.iwm.android.motionrecorder.training.SpeedPoint;

/**
 * Created by Szymon on 2015-05-17.
 */
@Table(name = "speed_values")
public class SpeedPointEntity extends Model {

    @Column
    private double value;
    @Column
    private int serialNumber;
    @Column(name = "route_id")
    private RouteEntity routeEntity;

    public SpeedPointEntity() {
        super();
    }

    public SpeedPointEntity(double value, int serialNumber, RouteEntity routeEntity) {

        this();
        this.value = value;
        this.serialNumber = serialNumber;
        this.routeEntity = routeEntity;
    }

    public SpeedPointEntity(SpeedPoint speedPoint) {

        this();
        value = speedPoint.getValue();
        serialNumber = speedPoint.getSerialNumber();
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public RouteEntity getRouteEntity() {
        return routeEntity;
    }

    public void setRouteEntity(RouteEntity routeEntity) {
        this.routeEntity = routeEntity;
    }
}
