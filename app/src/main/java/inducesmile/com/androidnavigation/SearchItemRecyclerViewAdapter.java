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

public class SearchItemRecyclerViewAdapter extends RecyclerView.Adapter<SearchItemRecyclerViewAdapter.SearchItemViewHolders> {

    private List<SearchItem> itemList;
    private Context context;

    public SearchItemRecyclerViewAdapter(Context context, List<SearchItem> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public SearchItemViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null);
        SearchItemViewHolders rcv = new SearchItemViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(SearchItemViewHolders holder, int position) {
        holder.searchType.setText(itemList.get(position).getName());
        //holder.personAddress.setText(itemList.get(position).getAddress());
        holder.photo.setImageResource(itemList.get(position).getPhotoId());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    class SearchItemViewHolders extends RecyclerView.ViewHolder{

        public ImageView photo;
        public TextView searchType;


        public SearchItemViewHolders(View itemView) {
            super(itemView);
            photo = (ImageView)itemView.findViewById(R.id.circleView);
            searchType = (TextView)itemView.findViewById(R.id.searchType);

        }
    }
}
