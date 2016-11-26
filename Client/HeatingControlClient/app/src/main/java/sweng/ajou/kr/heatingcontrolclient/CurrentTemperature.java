package sweng.ajou.kr.heatingcontrolclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

import sweng.ajou.kr.heatingcontrolclient.adapters.TemperatureAdapter;
import sweng.ajou.kr.heatingcontrolclient.model.Sensor;

public class CurrentTemperature extends AppCompatActivity {
    List<Sensor> itemSensor = new ArrayList<>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_temperature);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listView2);

        new GetSensors().execute();

        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent myIntent = new Intent(CurrentTemperature.this, EnviomentDetail.class);
                myIntent.putExtra("name",itemSensor.get(i).getName());
                myIntent.putExtra("sensor_id",itemSensor.get(i).getSensorId());
                startActivity(myIntent);
            }
        });

    }

    private class GetSensors extends AsyncTask<Boolean, String, String> {

        String text = "";
        @Override
        protected String doInBackground(Boolean... params) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                //Must fetch item - can be forced
                if (Util.getInternetState(getApplicationContext())) {
                    url = new URL("http://www.imakezappz.dk/AjouTempControl/getSensors.php");
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.listView2) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            Sensor obj = (Sensor) lv.getItemAtPosition(acmi.position);
/*
            menu.add("One");
            menu.add("Two");
            menu.add("Three");
            menu.add(obj.getName());*/

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.current_temp_context_menu, menu);
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.deleteItem:
                //editNote(info.id);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
}
