package inducesmile.com.androidnavigation;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * Created by RayhanulMasud on 10/30/2015.
 */
public class CommentTab extends Fragment{
    String locationId;
    private LinearLayoutManager lLayout;
    TextView textView;
    String myJSON;
    ArrayList allIComments;
    String TAG_RESULTS="result";
    RecyclerView rView;
    View myView;
    String commentProblem=null;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.comment, container, false);
        final EditText commentEditText= (EditText) v.findViewById(R.id.comment);
        lLayout=new LinearLayoutManager(getActivity());
        locationId=getArguments().getString("locationId");
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            getData();
        }
        else{
            Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
        }

        rView=(RecyclerView) v.findViewById(R.id.recyclerViewComments);
        rView.setLayoutManager(lLayout);
        Button buttonComment= (Button) v.findViewById(R.id.buttonComment);
        buttonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String userName;
                int y;
                commentProblem= commentEditText.getText().toString();
                //commentProblem.replace(' ','+');
                //commentProblem.replaceAll(" ","+");
                SharedPreferences sp = getActivity().getSharedPreferences("Status_Preference", Activity.MODE_PRIVATE);
                String loginStatus=sp.getString("loginStatus", "error");
                if(loginStatus.equals("Successful")) {
                    ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        userName=sp.getString("userName", "1");
                        userName=userName.toUpperCase();
                        sendComment(userName, commentProblem, locationId);

                        commentEditText.setText("");
                        //getData();
                    }
                    else{
                        Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return v;
    }



    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://firstphp-unmad.rhcloud.com/commentRetrieve.php?loc_id="+locationId);

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
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

    protected void showList(){
        try {
            allIComments = new ArrayList<Comment>();
            JSONObject jsonObj = new JSONObject(myJSON);
            JSONArray data = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<data.length();i++){
                JSONObject c = data.getJSONObject(i);
                String userName = c.getString("name");
                String comment = c.getString("comment");
                allIComments.add(new Comment(userName,comment));

            }
            //ViewAdapterComments adpater= new ViewAdapterComments
            ViewAdapterComments mAdapter = new ViewAdapterComments(getActivity(), allIComments);
            rView.setAdapter(mAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void sendComment(String uName, String usercomment, String lid){
        class SendComment extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://firstphp-unmad.rhcloud.com/sendComment.php?userName="+params[0]+"&comment="+commentProblem.replaceAll(" ","+")+
                                                    "&locationid="+params[2]);

                // Depends on your web service
                //httppost.setHeader("Content-type", "application/json");

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    result =  EntityUtils.toString(response.getEntity());
                } catch (Exception e) {
                    // Oops
                }
                finally {
                    try{if(inputStream != null)
                        inputStream.close();}
                    catch(Exception squish){}
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){

                if(result.compareTo("true")==0){
                    Toast.makeText(getActivity() , "Comment Sent", Toast.LENGTH_LONG).show();
                    //EditText et=(EditText) myView.findViewById(R.id.comment);
                    //et.setText("");
                    //if (this.getStatus() == AsyncTask.Status.FINISHED) {
                        getData();
                    commentProblem="";

                    //}

                }
                else{

                }
            }
        }
        SendComment g = new SendComment();
        g.execute(uName, usercomment, lid);


    }

}
