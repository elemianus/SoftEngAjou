package sweng.ajou.kr.heatingcontrolclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sweng.ajou.kr.heatingcontrolclient.R;
import sweng.ajou.kr.heatingcontrolclient.model.Environment;

/**
 * Created by Peter on 15-11-2016.
 */
public class EnviomentAdapter extends BaseAdapter {
    List<Environment> sensorList = new ArrayList<>();
    LayoutInflater inflater;

    public EnviomentAdapter(Context context, List<Environment> sensorList){
        inflater = LayoutInflater.from(context);
        this.sensorList = sensorList;
    }

    @Override
    public int getCount() {
        return sensorList.size();
    }

    @Override
    public Object getItem(int i) {
        return sensorList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view = inflater.inflate(R.layout.item_temperature,null);
        }
        TextView currentTemp = (TextView) view.findViewById(R.id.item_temperature_current_temperature);
        TextView sensorTitle = (TextView) view.findViewById(R.id.item_temperature_sensor_title);
        TextView sensorEnvioment = (TextView) view.findViewById(R.id.item_temperature_sensor_location);

        currentTemp.setText(sensorList.get(i).getAvgTemp()+" C");
        sensorTitle.setText(sensorList.get(i).getName());
        sensorEnvioment.setText("Some envioment");

        return view;
    }
}
