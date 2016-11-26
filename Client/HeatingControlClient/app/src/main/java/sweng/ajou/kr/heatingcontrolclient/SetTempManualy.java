package sweng.ajou.kr.heatingcontrolclient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SetTempManualy extends AppCompatActivity {

    private int sensorID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_temp_manualy);

        TextView envioment = (TextView) findViewById(R.id.set_temp_envioment);
        final EditText newTemp = (EditText) findViewById(R.id.set_temp_temperature);
        Button setTempButton = (Button) findViewById(R.id.set_temp_commit);

        setTempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double targetTemp = Double.parseDouble(newTemp.getText().toString());
                Toast.makeText(getApplicationContext(),"Setting temperature",Toast.LENGTH_SHORT).show();
                new ChangeTemperature().execute(targetTemp);
            }
        });

        if(getIntent()!=null){
            envioment.setText("New temperature: "+ getIntent().getStringExtra("name"));
            sensorID = getIntent().getIntExtra("sensor_id",0);
        }
    }

    private class ChangeTemperature extends AsyncTask<Double, String, String> {

        String text = "";
        @Override
        protected String doInBackground(Double... params) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {


                //Must fetch item - can be forced
                if (Util.getInternetState(getApplicationContext())) {
                    url = new URL("http://www.imakezappz.dk/AjouTempControl/changeTemperature.php");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setDoOutput(true);
                    urlConnection.setChunkedStreamingMode(0);

                    String data = URLEncoder.encode("sensor_id", "UTF-8")
                            + "=" + URLEncoder.encode(sensorID+ "", "UTF-8");
                    data += "&" + URLEncoder.encode("temperature", "UTF-8") + "="
                            + URLEncoder.encode(params[0]+"", "UTF-8");


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
                    Log.e("",text);
                }

                /*JSONArray jsonArray = new JSONArray(text);
                for (int i = 0; i < jsonArray.length(); i++) {
                }*/

            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();

            }  finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(getApplicationContext(),"Tempature set",Toast.LENGTH_LONG).show();
        }
    }

}
