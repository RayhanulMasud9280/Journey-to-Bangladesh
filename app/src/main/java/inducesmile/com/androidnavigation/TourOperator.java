package inducesmile.com.androidnavigation;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class TourOperator extends ActionBarActivity {

    private Toolbar topToolBar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinearLayoutManager lLayout;
    List<TourOperatorItem> allItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_operator);

        allItems = new ArrayList<TourOperatorItem>();
        topToolBar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(topToolBar);

        List<TourOperatorItem> rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(this);
        //LinearLayoutManager l= new LinearLayoutManager(this);

        final RecyclerView rView = (RecyclerView)findViewById(R.id.recyclerViewTourOperator);
        rView.setLayoutManager(lLayout);

        TourItemRecyclerViewAdapter rcAdapter = new TourItemRecyclerViewAdapter(getApplicationContext(),rowListItem);
        TourItemRecyclerViewAdapter av= new TourItemRecyclerViewAdapter(this,rowListItem);
        rView.setAdapter(rcAdapter);

        rView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        MainActivity.check = 1;
                        /*if (position == 0) {
                            Intent intent = new Intent(TourOperator.this, SearchDivisionActivity.class);
                            startActivity(intent);
                        } else if (position == 1) {
                            Intent intent = new Intent(TourOperator.this, SearchTypeActivity.class);
                            startActivity(intent);
                        } else if (position == 2) {
                            Intent intent = new Intent(TourOperator.this, NearestLocation.class);
                            startActivity(intent);
                        }*/
                        Intent intent = new Intent(TourOperator.this, TourDetailsActivity.class);
                        intent.putExtra("id",position);
                        intent.putExtra("name",allItems.get(position).getName());
                        startActivity(intent);
                    }
                })
        );

        rView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));


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


    private List<TourOperatorItem> getAllItemList(){



        allItems.add(new TourOperatorItem("The Guide Tours Ltd"));
        allItems.add(new TourOperatorItem("Bangla Holidays"));
        allItems.add(new TourOperatorItem("Pugmark Tour & Travels"));
        allItems.add(new TourOperatorItem("Bangladesh Travels Home Ltd"));
        allItems.add(new TourOperatorItem("Intraco Tours & Travels Ltd"));
        allItems.add(new TourOperatorItem("Evergreen Tourism Network"));
        allItems.add(new TourOperatorItem("Nijhoom Tours"));
        allItems.add(new TourOperatorItem("Pip Tours"));


        return allItems;
    }

}
