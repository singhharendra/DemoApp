package com.example.harendra.mydemoapp;

import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TransportActivity extends Activity {
    String URL = "http://express-it.optusnet.com.au/sample.json";
    static final String TAG="TransportActivity.Java";
    static final LatLng BLUEMOUNTAINS = new LatLng(-33.8433,151.241);
    String id, name, car, train, latitude, longitude,mLastUpdateTime;
    TextView textViewId, textViewName, textViewCar, textViewTrain, textViewLatitude, textViewLongitude;
    Button buttonNavigate;
    Spinner spinnerName;
    ArrayList<String> arrayListNames;
    GoogleMap googleMap;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);
        new AsyncTaskParseJson().execute();



    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonNavigate:
                try {
                    if (googleMap == null) {
                        googleMap  = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
                    }
                    googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    Marker TP = googleMap.addMarker(new MarkerOptions().position(BLUEMOUNTAINS).title("BLUEMOUNTAINS"));


                } catch (Exception ex) {

                }
                break;

        }
    }
    private class AsyncTaskParseJson extends AsyncTask<String, String, String> {

        final String TAG = "AsyncTaskParseJson.java";
        JSONArray dataJsonArr = null;
        ArrayList<ItemsClass> transportItems = new ArrayList<>();
       ArrayList<String> arrayListNames = new ArrayList<>();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                JsonParser jParser = new JsonParser();

                dataJsonArr = jParser.getJSONFromUrl(URL);

                for (int i = 0; i < dataJsonArr.length(); i++) {
                    JSONObject jobjfirst = dataJsonArr.getJSONObject(i);
                    ItemsClass itemsClass = new ItemsClass();
                    itemsClass.setId(Integer.parseInt(jobjfirst.optString("id")));
                    itemsClass.setName(jobjfirst.optString("name"));
                    Log.e(TAG, ",id: " + id + ", name: " + name);
                    JSONObject jsonObject = jobjfirst.getJSONObject("fromcentral");
                    itemsClass.setCar(jsonObject.optString("car"));
                    itemsClass.setTrain(jsonObject.optString("train"));
                    Log.e("TAG", "car:" + car + ",train" + train);
                    JSONObject jsonObjectsecond = jobjfirst.getJSONObject("location");
                    itemsClass.setLatitude(jsonObjectsecond.optString("latitude"));
                    itemsClass.setLongitude(jsonObjectsecond.optString("longitude"));
                    Log.e(TAG, ",latitude: " + latitude + ", longitude: " + longitude);
                    transportItems.add(itemsClass);
                     arrayListNames.add(jobjfirst.optString("name"));


                }

            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String strFromDoInBg) {
           Spinner spinnerName = (Spinner) findViewById(R.id.spinnerNames);
            ArrayAdapter<String>  adapter=(new ArrayAdapter<String>(TransportActivity.this,
                    android.R.layout.simple_spinner_dropdown_item,
                    arrayListNames));
            spinnerName
                    .setAdapter(adapter);
            adapter.notifyDataSetChanged();

            spinnerName
                    .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> arg0,
                                                   View arg1, int position, long arg3) {
                            // TODO Auto-generated method stub
                            textViewCar = (TextView) findViewById(R.id.textcar);
                            textViewTrain = (TextView) findViewById(R.id.texttrain);
                            textViewLatitude = (TextView) findViewById(R.id.textlatitude);
                            textViewLongitude = (TextView) findViewById(R.id.textlongi);
                            buttonNavigate= (Button)findViewById(R.id.buttonNavigate);
                            textViewCar.setText("Car: "
                                    + transportItems.get(position).getCar());
                            textViewTrain.setText("Train: "
                                    + transportItems.get(position).getTrain());
                            textViewLatitude.setText("Latitude: "
                                    + transportItems.get(position).getLatitude());
                            textViewLongitude.setText("Longitude: "
                                    + transportItems.get(position).getLongitude());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }
                    });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart fired ..............");
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop fired ..............");
        mGoogleApiClient.disconnect();
        Log.d(TAG, "isConnected ...............: " + mGoogleApiClient.isConnected());
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }


}

