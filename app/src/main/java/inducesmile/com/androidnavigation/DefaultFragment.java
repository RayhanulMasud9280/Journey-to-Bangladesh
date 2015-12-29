package inducesmile.com.androidnavigation;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DefaultFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinearLayoutManager lLayout;
    private String latitude;
    private String longitude;
    private String address;

    public DefaultFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.defaultview, container, false);

        List<SearchItem> rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(getActivity());

        final RecyclerView rView = (RecyclerView)view.findViewById(R.id.recycler_view);
        rView.setLayoutManager(lLayout);

        SearchItemRecyclerViewAdapter rcAdapter = new SearchItemRecyclerViewAdapter(getActivity(), rowListItem);
        rView.setAdapter(rcAdapter);

        rView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        MainActivity.check=1;
                        if(position==0) {
                            Intent intent = new Intent(getActivity(), SearchDivisionActivity.class);
                            startActivity(intent);
                        }
                        else if(position==1){
                            Intent intent = new Intent(getActivity(), SearchTypeActivity.class);
                            startActivity(intent);
                        }
                        else if(position==2){
                            //getGPS();
                            Intent intent = new Intent(getActivity(), NearestLocation.class);
                            //intent.putExtra("address",address);
                            startActivity(intent);
                        }

                        else if(position==3){
                            Intent intent = new Intent(getActivity(), TourOperator.class);
                            startActivity(intent);
                        }
                        else if(position==4){
                            Intent intent = new Intent(getActivity(), SearchHotelActivity.class);
                            startActivity(intent);
                        }


                    }
                })
        );
        return view;


    }

    private List<SearchItem> getAllItemList(){

        List<SearchItem> allItems = new ArrayList<SearchItem>();
        allItems.add(new SearchItem("Browse by Division", R.drawable.map));
        allItems.add(new SearchItem("Browse by type", R.drawable.browsebytype));
        allItems.add(new SearchItem("Browse for nearest locations", R.drawable.hotel));
        allItems.add(new SearchItem("Tour Operators", R.drawable.travelagency));
        allItems.add(new SearchItem("Hotels",R.drawable.hotelsnew));

        return allItems;
    }



}
