package sweng.ajou.kr.heatingcontrolclient.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 15-11-2016.
 */

public class Environment {
    private List<Sensor> sensorsInEnvironment = new ArrayList<>();
    private String name;


    public double getAvgTemp() {
        double avgTemp = 0;
        for (Sensor item : sensorsInEnvironment) {
            avgTemp += item.getTempature();
        }
        avgTemp = avgTemp / sensorsInEnvironment.size();
        return avgTemp;
    }

    public void addSensorToEnvironment(Sensor sensorToAdd) {
        sensorsInEnvironment.add(sensorToAdd);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
