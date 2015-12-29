package inducesmile.com.androidnavigation;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

/**
 * Created by RayhanulMasud on 10/30/2015.
 */
public class HotelsTab extends Fragment{


    String myJSON;
    //TextView textView;
    private ProgressDialog simpleWaitDialog;
    JSONArray data = null;
    private static final String TAG_RESULTS="result";
    private static final String TAG_HOWTOGO="howtogo";
    private List<HotelInfo> allItems;
    private RecyclerView rView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinearLayoutManager lLayout;
    private
    //private String id=LocationDetails.locationId;
    TextView textView;
    Context con;
    private String divisionId;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.hotel_new, container, false);
        divisionId=getArguments().getString("division");
        con=getActivity();
        //textView= (TextView) v.findViewById(R.id.des_how_hotelTextView);
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            getData();
        }
        else{
            Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
        }
        lLayout = new LinearLayoutManager(getActivity());

        rView = (RecyclerView)v.findViewById(R.id.recyclerViewHotel);
        rView.setLayoutManager(lLayout);

        //getData();
        return v;
    }

    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                Log.i("Async-Example", "onPreExecute Called");
                simpleWaitDialog = ProgressDialog.show(getActivity(),
                        "Wait", "Loading....");

            }
            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://firstphp-unmad.rhcloud.com/hotel.php?loc_id="+divisionId);

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
                simpleWaitDialog.dismiss();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

    protected void showList(){
        try {
            allItems=new ArrayList<HotelInfo>();
            JSONObject jsonObj = new JSONObject(myJSON);
            data = jsonObj.getJSONArray(TAG_RESULTS);
            for(int i=0;i<data.length();i++) {
                JSONObject obj = data.getJSONObject(i);
                /*String text = "Hotel Name:";
                text += obj.getString("name");
                text += "\nContact:" + obj.getString("contact");
                textView.setText(text);*/
                allItems.add(new HotelInfo(obj.getString("name"),obj.getString("contact")));
            }
            //textView.setMovementMethod(new ScrollingMovementMethod());
            //getActivity();
            mAdapter = new HotelsCardViewAdapter(con,allItems);
            rView.setAdapter(mAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



}
