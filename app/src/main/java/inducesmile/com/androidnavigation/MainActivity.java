package inducesmile.com.androidnavigation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
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
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    String[]titles = {"Journey to Bangladesh", "Visited Places", "About App", "Give Feedback"};
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar topToolBar;
    static int check=1;
    private boolean problematic_status=false;
    private Dialog signUpDialog;
    //Bundle save;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinearLayoutManager lLayout;

    boolean status=false;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment fragmentMain;
    private String myJSON;
    private Dialog myDialog;
    private String userName;
    private String password;
    private String TAG_RESULTS="result";
    Bundle bundle;

    private String signUpSuccess="false";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle=savedInstanceState;
        //save=savedInstanceState;
        setContentView(R.layout.activity_main);
        SharedPreferences sp = getSharedPreferences("Status_Preference", Activity.MODE_PRIVATE);
        String loginStatus=sp.getString("loginStatus","error");
        if(!loginStatus.equals("Successful")) {
            callLoginDialog();
        }
        fragmentMain= new DefaultFragment();
        fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragmentMain).commit();
        //mDrawerLayout.setVisibility(View.INVISIBLE);
        mTitle = mDrawerTitle = getTitle();

        topToolBar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(topToolBar);
        //topToolBar.setLogo(R.drawable.logo);
        //topToolBar.setLogoDescription(getResources().getString(R.string.logo_desc));



        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        List<ItemObject> listViewItems = new ArrayList<ItemObject>();
        listViewItems.add(new ItemObject("Home", R.drawable.imageone));
        //listViewItems.add(new ItemObject("Visited Places", R.drawable.imageone));
        listViewItems.add(new ItemObject("About App", R.drawable.imageone));
        //listViewItems.add(new ItemObject("Give Feedback", R.drawable.imageone));

        mDrawerList.setAdapter(new CustomAdapter(this, listViewItems));

        mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                //rView.setVisibility(View.INVISIBLE);
                //mDrawerLayout.setVisibility(View.GONE);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // make Toast when click
                Toast.makeText(getApplicationContext(), "Position " + position, Toast.LENGTH_LONG).show();
                selectItemFragment(position);
            }
        });



    }

    private void selectItemFragment(int position){

        Fragment fragment = null;
        //FragmentManager fragmentManager = getSupportFragmentManager();
        switch(position) {
            default:
            case 0:
                check=1;
                fragment= new DefaultFragment();
                fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragmentMain).commit();

                break;
            /*case 1:
                fragment = new VisitedPlacesFragment();
                check=1;
                fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).commit();
                break;*/
            case 1:
                fragment = new AboutAppFragment();
                check=1;
                fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).commit();
                break;
            /*case 3:
                fragment = new GiveFeedbackFragment();
                check=1;
                fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment).commit();
                break;
                */
        }


        mDrawerList.setItemChecked(position, true);
        setTitle(titles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
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
            Toast.makeText(getApplicationContext(),"Log Out",Toast.LENGTH_LONG).show();
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            return true;
        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickSend(View v){
        Toast.makeText(getApplicationContext(), "Feedback Sent", Toast.LENGTH_SHORT).show();

    }

    public void onBackPressed() {
        //moveTaskToBack(true);
        check++;
        if(check==2){

            Toast.makeText(getApplicationContext(),"To quit press back button again",Toast.LENGTH_LONG).show();
        }
        if(check==3)
            finish();
        //setContentView(R.layout.activity_main);
    }

    private void callLoginDialog()
    {
        myDialog = new Dialog(this);
        myDialog.setTitle("Log In");
        myDialog.setContentView(R.layout.login);
        myDialog.setCancelable(false);
        Button login = (Button) myDialog.findViewById(R.id.loginButton);
        Button signup= (Button) myDialog.findViewById(R.id.signupButton);
        Button quit= (Button) myDialog.findViewById(R.id.quitButton);

        final EditText userNameText = (EditText) myDialog.findViewById(R.id.userNameEditText);
        final EditText passwordText = (EditText) myDialog.findViewById(R.id.passWordEditText);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userNameText.getText().toString().trim().length()>0 && passwordText.getText().toString().trim().length()>0) {
                    userName = userNameText.getText().toString();
                    password = passwordText.getText().toString();
                    ConnectivityManager connMgr = (ConnectivityManager)
                            getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        // fetch data
                        getLogin();
                    }
                    else {
                        // display error
                        TextView s=(TextView) myDialog.findViewById(R.id.loginStatusTextView);
                        s.setVisibility(View.VISIBLE);
                        s.setText("No Network Connection");
                        Toast.makeText(getApplicationContext(),"No Network Connection",Toast.LENGTH_LONG).show();


                    }

                }
                else{
                    Toast.makeText(getApplicationContext(),"Fill all the fields",Toast.LENGTH_LONG).show();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               myDialog.dismiss();

                callSignUpDialog();
                Toast.makeText(getApplicationContext(),"Sign Up",Toast.LENGTH_LONG).show();
            }
        });


        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                finish();

            }
        });
        myDialog.show();



    }


    private void callSignUpDialog() {
        signUpDialog = new Dialog(this);
        signUpDialog.setTitle("Sign Up");
        signUpDialog.setContentView(R.layout.signup);
        signUpDialog.setCancelable(false);
        //Button login = (Button) myDialog.findViewById(R.id.loginButton);
        Button signup = (Button) signUpDialog.findViewById(R.id.signupButton);
        Button quit = (Button) signUpDialog.findViewById(R.id.quitButton);

        final EditText userNameText = (EditText) signUpDialog.findViewById(R.id.userNameEditText);
        final EditText passwordText = (EditText) signUpDialog.findViewById(R.id.passWordEditText);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userNameText.getText().toString().trim().length()>0 && passwordText.getText().toString().trim().length()>0) {
                    if (passwordText.getText().toString().trim().length() < 6) {
                        Toast.makeText(getApplicationContext(), "Password length should be greater than 5 characters", Toast.LENGTH_LONG).show();
                        TextView u = (TextView) signUpDialog.findViewById(R.id.signUpStatusTextView);
                        u.setText("Password length should be greater than 5 characters");
                        return;
                    }

                    //EditText userName= (EditText) signUpDialog.findViewById(R.id.userNameEditText);
                    //getSignUp(userNameText.getText().toString().trim());
                    /*if ( problematic_status == true) {
                        Toast.makeText(getApplicationContext(), "User Name is already used", Toast.LENGTH_LONG).show();
                        TextView u = (TextView) signUpDialog.findViewById(R.id.signUpStatusTextView);
                        u.setText("UserName is already used");
                        problematic_status=false;
                        return ;
                    } else {*/

                        ConnectivityManager connMgr = (ConnectivityManager)
                                getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                        if (networkInfo != null && networkInfo.isConnected()) {
                            // fetch data
                            signUpSuccessCheck(userNameText.getText().toString(), passwordText.getText().toString());
                        }
                        else {
                            // display error
                            Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
                        }
                }
                else{
                    TextView u=(TextView) signUpDialog.findViewById(R.id.signUpStatusTextView);
                    u.setText("Fill the fields");
                    Toast.makeText(getApplicationContext(),"Fill the fields",Toast.LENGTH_LONG).show();
                }
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                finish();

            }
        });
        signUpDialog.show();

    }

    public void getSignUp(String usernameEntered){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                //EditText
                HttpPost httppost = new HttpPost("http://firstphp-unmad.rhcloud.com/checksignup.php?username="+params[0]);

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
                    try{if(inputStream != null)inputStream.close();}
                    catch(Exception squish){}
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                myJSON=result;
                checkBoolean();
                //status=!status;
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(usernameEntered);
        //boolean ret=checkBoolean();
        //status=false;
        //return status;
        //return false;
    }

    public void signUpSuccessCheck(String uName, String pass){
        class SignUp extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://firstphp-unmad.rhcloud.com/signup.php?username="+params[0]+"&password="+params[1]);

                // Depends on your web service
                httppost.setHeader("Content-type", "application/json");

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    result =  EntityUtils.toString(response.getEntity());
                    /*HttpClient httpclient=new DefaultHttpClient();
                    HttpPost httppost=new  HttpPost("http://192.168.10.104/android/sample.php");
                    HttpResponse response = httpclient.execute(httppost);
                    String str =  EntityUtils.toString(response.getEntity());*/
                    /*inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();*/
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

                //String success=result;


                if(result.compareTo("true")==0){
                    //signUpSuccess="false";
                    //return true;
                    signUpDialog.dismiss();
                    callLoginDialog();
                    //signUpSuccess="true";
                }
                else{
                    Toast.makeText(getApplicationContext(), "User Name is already used", Toast.LENGTH_LONG).show();
                    TextView u = (TextView) signUpDialog.findViewById(R.id.signUpStatusTextView);
                    u.setText("UserName is already used");
                   // Toast.makeText(getApplicationContext(),"Row creation error"+result,Toast.LENGTH_LONG).show();
                }
            }
        }
        SignUp g = new SignUp();
        g.execute(uName,pass);

        //signUpSuccess="false";
        //return false;
    }

    public void getLogin(){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://firstphp-unmad.rhcloud.com/login.php?username="+userName+"&password="+password);

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
                    try{if(inputStream != null)inputStream.close();}
                    catch(Exception squish){}
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                myJSON=result;
                if(checkBoolean()==false){
                    problematic_status=false;
                    userName="";
                    password="";
                    EditText u=(EditText)myDialog.findViewById(R.id.userNameEditText);
                    u.setText("");
                    EditText p=(EditText)myDialog.findViewById(R.id.passWordEditText);
                    p.setText("");
                    TextView s=(TextView) myDialog.findViewById(R.id.loginStatusTextView);
                    s.setVisibility(View.VISIBLE);
                    s.setText("Username and Password not matched");
                    Toast.makeText(getApplicationContext(),"Username and Password not matched",Toast.LENGTH_LONG).show();
                }
                else{
                    problematic_status=false;
                    myDialog.dismiss();
                    SharedPreferences sp = getSharedPreferences("Status_Preference", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("loginStatus","Successful");
                    //editor.putInt("loginStatus", 713);
                    editor.putString("userName",userName);
                    editor.commit();
                    userName="";
                    password="";

                }
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }

    protected boolean checkBoolean(){

        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            JSONArray array = jsonObj.getJSONArray(TAG_RESULTS);

            if(array.length()>0) {
                problematic_status=true;
                Toast.makeText(getApplicationContext(),"Found",Toast.LENGTH_LONG).show();
                return true;
            }
            else {
                problematic_status = false;
                Toast.makeText(getApplicationContext(),"Not Found",Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }




}
