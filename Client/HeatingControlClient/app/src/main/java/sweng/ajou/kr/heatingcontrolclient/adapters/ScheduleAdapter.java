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
import sweng.ajou.kr.heatingcontrolclient.model.Schedule;

/**
 * Created by Peter on 15-11-2016.
 */
public class ScheduleAdapter extends BaseAdapter {
    List<Schedule> sensorList = new ArrayList<>();
    LayoutInflater inflater;

    public ScheduleAdapter(Context context, List<Schedule> sensorList){
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
            view = inflater.inflate(R.layout.item_schedule,null);
        }
        TextView targetTemp = (TextView) view.findViewById(R.id.item_schedule_target_temp);
        TextView scheduleTime = (TextView) view.findViewById(R.id.item_schedule_time);


        targetTemp.setText(sensorList.get(i).getTargetTemp()+" C");
        scheduleTime.setText(sensorList.get(i).getTargetTime());


        return view;
    }
}
