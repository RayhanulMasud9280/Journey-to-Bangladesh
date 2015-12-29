package inducesmile.com.androidnavigation;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NearestLocation extends AppCompatActivity {

    private String distanceJSON;
    private TextView txt;
    private Button buttonShow;
    private Toolbar toolbar;
    private RecyclerView rView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinearLayoutManager lLayout;
    private ProgressDialog simpleWaitDialog;
    private Spinner distanceSpinner;

    private String distanceStatus="10";
    private String latitude;
    private String longitude;
    private String myJSON;
    private ArrayList<LocationSearchInfo> locationSearchInfo;
    private ArrayList<String> locationNameArrayList;
    private int numOfLocations;
    private String sourceAddress=null;
    private String currentAddress;
    private String[] distance = { "10", "50", "100", "150",
            "200", "300", "300+"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Intent intent = getIntent();
        //sourceAddress=intent.getStringExtra("address");
        setContentView(R.layout.activity_nearest_location);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Nearest Locations");


        //locationSearchInfo=new ArrayList<LocationSearchInfo>();

        //spinner
        distanceSpinner=(Spinner) findViewById(R.id.distanceSpinner);
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, distance);
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distanceSpinner.setAdapter(adapter_state);
        distanceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                distanceStatus = (String) distanceSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonShow=(Button) findViewById(R.id.buttonShow);

        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getGPS();
                locationSearchInfo= new ArrayList<LocationSearchInfo>();
                locationNameArrayList=new ArrayList<String>();
                getData();
            }
        });

        //List<VisitedPlaces> rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(this);

        rView = (RecyclerView) findViewById(R.id.recyclerViewVisitedPlaces);
        rView.setLayoutManager(lLayout);

        //mAdapter = new VisitedPlacesCardViewAdapter(NearestLocation.this, rowListItem);
        //rView.setAdapter(mAdapter);

        rView.addOnItemTouchListener(
                new RecyclerItemClickListener(NearestLocation.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(NearestLocation.this, LocationDetails.class);
                        intent.putExtra("id", allItems.get(position).getPlaceId());
                        String string = allItems.get(position).getName();
                        String[] parts = string.split("\nDistance");
                        String part1 = parts[0]; // 004
                        intent.putExtra("name",parts[0]);
                        startActivity(intent);
                    }
                })
        );

        //txt= (TextView) findViewById(R.id.textView2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            SharedPreferences settings = getApplicationContext().getSharedPreferences("Status_Preference", Context.MODE_PRIVATE);
            settings.edit().clear().commit();
            Toast.makeText(getApplicationContext(), "Log Out", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,MainActivity.class);
            finish();
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                //Log.i("Async-Example", "onPreExecute Called");
                simpleWaitDialog = ProgressDialog.show(NearestLocation.this,
                        "Wait", "Loading....");

            }
            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("https://firstphp-unmad.rhcloud.com/alllocationfetch.php");

                // Depends on your web service
                httppost.setHeader("Content-type", "application/json");

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    // Oops
                }
                finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                myJSON=result;
                //locationNameArrayList=new ArrayList<String>();
                showList();
                //Toast.makeText(getApplicationContext(),l,Toast.LENGTH_LONG).show();
                fetchDistance(locationNameArrayList.remove(locationNameArrayList.size()-1));

            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

    protected void showList(){
        try {
            JSONObject jsonObject= new JSONObject(myJSON);
            JSONArray data= jsonObject.getJSONArray("result");
            numOfLocations=data.length();
            for(int i=0;i<data.length();i++){
                JSONObject c = data.getJSONObject(i);
                String loc_Name = c.getString("name");
                String loc_id = c.getString("id");
                String div_id= c.getString("division_id");
                String loc_address=c.getString("address");
                locationSearchInfo.add(new LocationSearchInfo(loc_id,loc_Name,div_id,0,loc_address));
                //allIComments.add(new Comment(userName,comment));
                //l+="id: "+loc_id+"name: "+loc_Name+"div_id: "+div_id+"\n";
                locationNameArrayList.add(loc_address.replaceAll(" ","+"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void fetchDistance(String place){

        class FetchDistanceJSON extends AsyncTask<String, Void, String> {


            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("https://maps.googleapis.com/maps/api/distancematrix/json?origins="+sourceAddress+"&destinations="+params[0]+"&mode=driving&language=en&key=AIzaSyCOjPDkrr1TVkNsmFBGrbst2LzLdTeARIY");

                // Depends on your web service
                httppost.setHeader("Content-type", "application/json");

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    // Oops
                }
                finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                distanceJSON=result;
                showDistance();
                for(int i=0;i<20;i++);
                if(locationNameArrayList.size()>0) {
                    fetchDistance(locationNameArrayList.remove(locationNameArrayList.size()-1));
                }
                else{

                    simpleWaitDialog.dismiss();
                    /*if(u==1) {
                        getData();
                        u++;
                    }*/

                    nearestLocationInsertInRecycleView();

                    //txt.setText(p);

                }

            }
        }
        FetchDistanceJSON g = new FetchDistanceJSON();
        g.execute(place);
    }

    void showDistance(){

        try {
            JSONObject jsonObj = new JSONObject(distanceJSON);

            JSONArray rows = jsonObj.getJSONArray("rows");
            JSONObject object = rows.getJSONObject(0);

            JSONArray element=object.getJSONArray("elements");
            JSONObject firstElement=element.getJSONObject(0);

            JSONObject distanceObject=firstElement.getJSONObject("distance");

            String dis= distanceObject.getString("value");

            dis=Double.toString (Double.parseDouble(dis)/1000);
            locationSearchInfo.get(locationNameArrayList.size()).setDistance(Double.parseDouble(dis));

        }catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void getGPS(){
        LocationManager locationManager = (LocationManager) NearestLocation.this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                latitude = Double.toString(location.getLatitude());
                longitude = Double.toString(location.getLongitude());
                sourceAddress = latitude + "," + longitude;
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(NearestLocation.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10, 25000, locationListener);
        }
    }

    public void nearestLocationInsertInRecycleView(){

        double max;
        Collections.sort(locationSearchInfo);
        if(distanceStatus.equals("300+")){
          max=2000.0;
        }
        else {
            max = Double.parseDouble(distanceStatus);
        }
        allItems=new ArrayList<VisitedPlaces>();
        for(int k=0;k<locationSearchInfo.size();k++){
            if(locationSearchInfo.get(k).getDistance()<=max){
                allItems.add(new VisitedPlaces(locationSearchInfo.get(k).getLoc_name()+
                        "\nDistance:"+locationSearchInfo.get(k).getDistance()+"KM",
                        locationSearchInfo.get(k).getLoc_id()));
            }
        }

        /*String p=""+sourceAddress;
        for(int i=0;i<locationSearchInfo.size();i++){
            p+=locationSearchInfo.get(i).getLoc_name()+locationSearchInfo.get(i).getDistance()+"km";
        }
        //txt.setText(p);*/

        mAdapter = new VisitedPlacesCardViewAdapter(NearestLocation.this, allItems);
        rView.setAdapter(mAdapter);

        //Toast.makeText(getApplicationContext(),jj,Toast.LENGTH_LONG).show();*/


    }



    private ArrayList<VisitedPlaces> allItems;
    int u=1;

}




