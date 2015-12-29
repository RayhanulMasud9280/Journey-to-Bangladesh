package inducesmile.com.androidnavigation;

/**
 * Created by RayhanulMasud on 10/30/2015.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TourItemRecyclerViewAdapter extends RecyclerView.Adapter<TourItemRecyclerViewAdapter.TourItemViewHolders> {

    private List<TourOperatorItem> itemList;
    private Context context;

    public TourItemRecyclerViewAdapter(Context context, List<TourOperatorItem> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public TourItemViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.touritemlist, null);
        TourItemViewHolders rcv = new TourItemViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(TourItemViewHolders holder, int position) {
        holder.operatorName.setText(itemList.get(position).getName());
        //holder.personAddress.setText(itemList.get(position).getAddress());
        //holder.photo.setImageResource(itemList.get(position).getPhotoId());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    class TourItemViewHolders extends RecyclerView.ViewHolder{

        //public ImageView photo;
        public TextView operatorName;


        public TourItemViewHolders(View itemView) {
            super(itemView);
            //photo = (ImageView)itemView.findViewById(R.id.circleView);
            operatorName = (TextView)itemView.findViewById(R.id.operatorName);

        }
    }
}
