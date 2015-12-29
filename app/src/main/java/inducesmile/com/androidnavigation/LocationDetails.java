package inducesmile.com.androidnavigation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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

public class LocationDetails extends ActionBarActivity {

    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapterLocation adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Description","How to Go","Comments"};
    int Numboftabs =Titles.length;
    public static String locationId=null;
    ImageView img;
    TextView text;
    String locationName;
    private RatingBar ratingBar;
    private Dialog ratingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        setContentView(R.layout.activity_location_details);
        img = (ImageView) findViewById(R.id.locationPic);
        text= (TextView) findViewById(R.id.locationName);


        addListenerOnRatingBar();
        locationId= getIntent().getExtras().getString("id");
        locationName= getIntent().getExtras().getString("name");
        text.setText(locationName);
        setTitle(locationName);
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
           // getData();

                Picasso.with(this)
                .load("http://firstphp-unmad.rhcloud.com/images/"+locationId+".jpg")
                        //.placeholder(R.drawable.ic_placeholder)   // optional
                        //.error(R.drawable.ic_error_fallback)      // optional
                        //.resize(250, 200)                        // optional
                        //.rotate(90)                             // optional
                .into(img);
        }
        else{
            Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
        }

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        //toolbar.setTitle(locationName);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapterLocation(getSupportFragmentManager(),Titles,Numboftabs,locationId);


        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.locationpager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabslocationdetails);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width


        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

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



    public void onClickVisited(View v){

        //Toast.makeText(getApplicationContext(),"Visited place has beejlkjjkhkjn added",Toast.LENGTH_SHORT).show();
    }

    public void onClickComment(View v){

        Toast.makeText(getApplicationContext(),"Comment Received",Toast.LENGTH_SHORT).show();
    }

    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        //txtRatingValue = (TextView) findViewById(R.id.txtRatingValue);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                //txtRatingValue.setText(String.valueOf(rating));

            }

        });
        ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // TODO perform your action here
                    callRatingDialog();
                    Toast.makeText(getApplicationContext(), "Rating: " + ratingBar.getRating(), Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
    }

    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://firstphp-unmad.rhcloud.com/rating.php?loc_id="+locationId);

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

                showList();
                ratingBar.setRating(Float.parseFloat(rating));
                getData();

            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

    protected void showList(){
        try {
            JSONArray data= new JSONArray();
            //allItems = new ArrayList<VisitedPlaces>();
            JSONObject jsonObj = new JSONObject(myJSON);
            data = jsonObj.getJSONArray("result");

            //for(int i=0;i<data.length();i++){
                JSONObject c = data.getJSONObject(0);
                rating = c.getString("rating");
                //String name = c.getString(TAG_NAME);
                //allItems.add(new VisitedPlaces(name,id));

            //mAdapter = new VisitedPlacesCardViewAdapter(getActivity(), allItems);
            //rView.setAdapter(mAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void callRatingDialog()
    {

        ratingDialog = new Dialog(this);
        ratingDialog.setTitle("Rating");
        ratingDialog.setContentView(R.layout.ratinglayout);
        //ratingDialog.setCancelable(false);


        ratingBarUser= (RatingBar) ratingDialog.findViewById(R.id.ratingBarUpload);


        ratingBarUser.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                rate=ratingBarUser.getRating();
            }
        });
        uprate=(Button) ratingDialog.findViewById(R.id.uprate);
        //Button cancel=(Button) findViewById(R.id.cancel);

        /*cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingDialog.dismiss();
            }
        });*/

        uprate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("Status_Preference", Activity.MODE_PRIVATE);
                //SharedPreferences.Editor editor = sp.edit();
                //editor.putString("loginStatus","Successful");
                //editor.putInt("loginStatus", 713);
                userName=sp.getString("userName","-1-2");

                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    // fetch data
                    //getLogin();
                    upLoadRating();
                    ratingDialog.dismiss();
                }
                else {
                    // display error
                    //TextView s=(TextView) myDialog.findViewById(R.id.loginStatusTextView);
                    //s.setVisibility(View.VISIBLE);
                    //s.setText("No Network Connection");
                    Toast.makeText(getApplicationContext(),"No Network Connection",Toast.LENGTH_LONG).show();

                }
            }
        });
        ratingDialog.show();
    }

    public void upLoadRating(){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://firstphp-unmad.rhcloud.com/ratinginsert.php?loc_id="+locationId+
                        "&user_name="+userName+"&rating="+rate);

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

                showList();
                ratingBar.setRating(Float.parseFloat(rating));

            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }



    String myJSON;
    String rating;
    float rate= 0;
    String userName;
    RatingBar ratingBarUser;
    Button uprate;
}
