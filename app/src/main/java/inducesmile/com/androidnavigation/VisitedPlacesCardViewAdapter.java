package inducesmile.com.androidnavigation;

/**
 * Created by RayhanulMasud on 10/29/2015.
 */
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Edwin on 18/01/2015.
 */

public class VisitedPlacesCardViewAdapter extends RecyclerView.Adapter<VisitedPlacesCardViewAdapter.VisitedCardViewHolder> {

    //List<NatureItem> mItems;
    private List<VisitedPlaces> itemList;
    private Context context;

    public VisitedPlacesCardViewAdapter(Context context, List<VisitedPlaces> itemList) {
        super();
        this.itemList = itemList;
        this.context = context;
    }


    @Override
    public VisitedCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.visitedplaces, viewGroup, false);
        VisitedCardViewHolder viewHolder = new VisitedCardViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(VisitedCardViewHolder viewHolder, int i) {
        viewHolder.locName.setText(itemList.get(i).getName());

        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            Picasso.with(context)
                    .load("http://firstphp-unmad.rhcloud.com/images/"+itemList.get(i).getPlaceId()+".jpg")
                            //.placeholder(R.drawable.ic_placeholder)   // optional
                            //.error(R.drawable.ic_error_fallback)      // optional
                            //.resize(250, 200)                        // optional
                            //.rotate(90)                             // optional
                    .into(viewHolder.imageView);

        }
        else{
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class VisitedCardViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView locName;


        public VisitedCardViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.image);
            locName = (TextView)itemView.findViewById(R.id.loc_name);

        }
    }
}

