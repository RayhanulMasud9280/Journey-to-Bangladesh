package inducesmile.com.androidnavigation;

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
 * Created by RayhanulMasud on 12/12/2015.
 */
public class HotelsCardViewAdapter extends RecyclerView.Adapter<HotelsCardViewAdapter.VisitedCardViewHolder> {

    //List<NatureItem> mItems;
    private List<HotelInfo> itemList;
    private Context context;

    public HotelsCardViewAdapter(Context context, List<HotelInfo> itemList) {
        super();
        this.itemList = itemList;
        this.context = context;
    }


    @Override
    public VisitedCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.hotellist, viewGroup, false);
        VisitedCardViewHolder viewHolder = new VisitedCardViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(VisitedCardViewHolder viewHolder, int i) {
        viewHolder.hotelName.setText(itemList.get(i).getName());

        viewHolder.hotelContact.setText(itemList.get(i).getContact());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class VisitedCardViewHolder extends RecyclerView.ViewHolder{

        ///public ImageView imageView;
        public TextView hotelName;
        public TextView hotelContact;


        public VisitedCardViewHolder(View itemView) {
            super(itemView);
            hotelName = (TextView)itemView.findViewById(R.id.hotel_name);
            hotelContact = (TextView)itemView.findViewById(R.id.hotel_contact);

        }
    }
}
