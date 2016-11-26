package sweng.ajou.kr.heatingcontrolclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import sweng.ajou.kr.heatingcontrolclient.adapters.ScheduleAdapter;
import sweng.ajou.kr.heatingcontrolclient.model.Schedule;

public class EnviomentDetail extends AppCompatActivity {
    List<Schedule> schedules;
    ListView listView;
    TextView currentTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envioment_detail);

        currentTime = (TextView) findViewById(R.id.envioment_detail_current_temp);
        listView = (ListView) findViewById(R.id.envioment_detail_schedule_temp);
        Button manualTemperatureButton = (Button) findViewById(R.id.activity_envioment_detail_set_temp_manualy);

        if(getIntent()!=null){
            getSupportActionBar().setTitle(getIntent().getStringExtra("name"));
            new GetCurrentTemperature().execute(getIntent().getIntExtra("sensor_id",0));
        }
        manualTemperatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(EnviomentDetail.this,SetTempManualy.class);
                myIntent.putExtra("name",getIntent().getStringExtra("name"));
                myIntent.putExtra("sensor_id",getIntent().getIntExtra("sensor_id",0));
                startActivity(myIntent);
            }
        });

        schedules = new ArrayList<>();
        schedules.add(new Schedule(22,"16:00"));
        schedules.add(new Schedule(17,"23:00"));
        schedules.add(new Schedule(20,"07:00"));
        schedules.add(new Schedule(16,"09:00"));


        ScheduleAdapter adapter = new ScheduleAdapter(getApplicationContext(),schedules);
        listView.setAdapter(adapter);

    }

    private class GetCurrentTemperature extends AsyncTask<Integer, Double, Double> {


        String text = "";
        @Override
        protected Double doInBackground(Integer... params) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                //Must fetch item - can be forced
                if (Util.getInternetState(getApplicationContext())) {
                    url = new URL("http://www.imakezappz.dk/AjouTempControl/getCurrentTemperature.php?sensor_id="+params[0]);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setDoOutput(true);
                    urlConnection.setChunkedStreamingMode(0);

                    String data = URLEncoder.encode("username", "UTF-8")
                            + "=" + URLEncoder.encode("herp", "UTF-8");
                    data += "&" + URLEncoder.encode("password", "UTF-8") + "="
                            + URLEncoder.encode("derp", "UTF-8");


                    BufferedReader reader = null;

                    // Send POST data request
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();

                    // Get the server response
                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        // Append server response in string
                        sb.append(line + "\n");
                    }

                    text = sb.toString();
                }

                JSONArray jsonArray = new JSONArray(text);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    double currentTemp = object.getDouble("temperature");
                    return currentTemp;

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return -1.0;
        }

        @Override
        protected void onPostExecute(Double result) {
            super.onPostExecute(result);
            currentTime.setText("Current: "+result+" C");
        }
    }

/*
    private class GetHistory extends AsyncTask<Integer, String, String> {

        String text = "";
        @Override
        protected String doInBackground(Integer... params) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                //Must fetch item - can be forced
                if (Util.getInternetState(getApplicationContext())) {
                    url = new URL("http://www.imakezappz.dk/AjouTempControl/getSensors.php?sensor_id="+params[0]);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setDoOutput(true);
                    urlConnection.setChunkedStreamingMode(0);

                    String data = URLEncoder.encode("username", "UTF-8")
                            + "=" + URLEncoder.encode("herp", "UTF-8");
                    data += "&" + URLEncoder.encode("password", "UTF-8") + "="
                            + URLEncoder.encode("derp", "UTF-8");


                    BufferedReader reader = null;

                    // Send POST data request
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();

                    // Get the server response
                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        // Append server response in string
                        sb.append(line + "\n");
                    }

                    text = sb.toString();
                }

                JSONArray jsonArray = new JSONArray(text);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    Sensor item = new Sensor(object.getString("sensor_name"),20.0,object.getInt("sensor_id"));

                    itemSensor.add(item);

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            TemperatureAdapter adapter = new TemperatureAdapter(getApplicationContext(),itemSensor);

            listView.setAdapter(adapter);
        }
    }
    */
}
