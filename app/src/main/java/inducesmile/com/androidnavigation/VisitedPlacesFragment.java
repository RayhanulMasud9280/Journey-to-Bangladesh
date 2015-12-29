package inducesmile.com.androidnavigation;

/**
 * Created by RayhanulMasud on 10/29/2015.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp1 on 21-01-2015.
 */
public class VisitedPlacesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinearLayoutManager lLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.myvisitedplaces,container,false);

        List<VisitedPlaces> rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(getActivity());

        RecyclerView rView = (RecyclerView)v.findViewById(R.id.recyclerViewVisitedPlaces);
        rView.setLayoutManager(lLayout);

        mAdapter = new VisitedPlacesCardViewAdapter(getActivity(), rowListItem);
        rView.setAdapter(mAdapter);

        /*rView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), Details.class);
                        startActivity(intent);
                    }
                })
        );*/
        return v;
    }

    private List<VisitedPlaces> getAllItemList(){

        List<VisitedPlaces> allItems = new ArrayList<VisitedPlaces>();
        /*allItems.add(new VisitedPlaces("Cox'Bazar", R.drawable.coxbazar));
        allItems.add(new VisitedPlaces("Sajek Valley", R.drawable.sajek));
        allItems.add(new VisitedPlaces("Lalbagh Kella", R.drawable.lalbag));
        allItems.add(new VisitedPlaces("Ahsan Manjil", R.drawable.ahsan));*/

        return allItems;
    }
}
