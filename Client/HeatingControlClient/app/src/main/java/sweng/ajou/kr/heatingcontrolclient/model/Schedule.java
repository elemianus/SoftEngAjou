package sweng.ajou.kr.heatingcontrolclient.model;

/**
 * Created by Peter on 20-11-2016.
 */

public class Schedule {
    private float targetTemp;
    private String targetTime;

    public Schedule(float targetTemp, String targetTime) {
        this.targetTemp = targetTemp;
        this.targetTime = targetTime;
    }

    public float getTargetTemp() {
        return targetTemp;
    }

    public void setTargetTemp(float targetTemp) {
        this.targetTemp = targetTemp;
    }

    public String getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(String targetTime) {
        this.targetTime = targetTime;
    }
}
