package edu.cohen.benjamin.weather_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeGetRequest();
    }

    private void makeGetRequest() {
        // url
        final String url = "http://api.aerisapi.com/forecasts/11101?client_id=4Jz2M6B5Rkjrx4QyJid1D&client_secret=jgoJrhiAJ9LrkUEb66mDal0HXqwU2znjjqnWuwnd";

        // request queue
        RequestQueue queue = Volley.newRequestQueue(this);

        // request object
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // parse response
                        try {
                            JSONArray periods = response.getJSONArray("response")
                                    .getJSONObject(0)
                                    .getJSONArray("periods");

                            paintGUI(periods);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );

        // add it to the RequestQueue
        queue.add(getRequest);
    }

    // only called once periods has a value
    private void paintGUI(JSONArray periods) throws JSONException {
        JSONObject period = periods.getJSONObject(0);
        String datetime = period.getString("dateTimeISO");
        int maxTempF = period.getInt("maxTempF");
        int minTempF = period.getInt("minTempF");
        String icon = period.getString("icon");

        TextView tv_date = findViewById(R.id.date0);
        TextView tv_high = findViewById(R.id.high0);
        TextView tv_low = findViewById(R.id.low0);
        ImageView img = findViewById(R.id.img0);

        tv_date.setText(datetime);
        tv_high.setText("High: " + maxTempF + "F˚");
        tv_low.setText("Low: " + minTempF + "F˚");
        img.setImageResource(R.drawable.showersw);
    }
}
