package sweng.ajou.kr.heatingcontrolclient.model;

/**
 * Created by Peter on 15-11-2016.
 */
public class Sensor {
    String name;
    Double tempature;
    int sensorId;

    public Sensor(String name, Double tempature, int sensorId) {
        this.name = name;
        this.tempature = tempature;
        this.sensorId = sensorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTempature() {
        return tempature;
    }

    public void setTempature(Double tempature) {
        this.tempature = tempature;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

}
