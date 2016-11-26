package sweng.ajou.kr.heatingcontrolclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ListView listView = (ListView)findViewById(R.id.listView);
        List<String> items = new ArrayList<>();
        items.add("Display temperatures");
        items.add("Sensors");
        items.add("Schedule");
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: //Display temp
                        startActivity(new Intent(MainMenu.this,CurrentTemperature.class));
                        break;
                    case 1://Sensors
                        startActivity(new Intent(MainMenu.this,SensorsActivity.class));
                        break;
                    case 2://Schedule
                        startActivity(new Intent(MainMenu.this,ScheduleActivity.class));
                        break;

                }
            }
        });
    }
}
