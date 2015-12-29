package inducesmile.com.androidnavigation;

/**
 * Created by RayhanulMasud on 10/29/2015.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Edwin on 18/01/2015.
 */

public class ViewAdapterComments extends RecyclerView.Adapter<ViewAdapterComments.CommentHolder> {

    //List<NatureItem> mItems;
    private List<Comment> itemList;
    private Context context;

    public ViewAdapterComments(Context context, List<Comment> itemList) {
        super();
        this.itemList = itemList;
        this.context = context;
    }


    @Override
    public CommentHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.comment_recyclerview, viewGroup, false);
        CommentHolder viewHolder = new CommentHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(CommentHolder viewHolder, int i) {
        viewHolder.userName.setText(itemList.get(i).getUserName());
        viewHolder.comment.setText(itemList.get(i).getComment());
        //viewHolder.imageView.setImageResource(itemList.get(i).getImage());
        //viewHolder.imageView.setImageBitmap(itemList.get(i).getImage());


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class CommentHolder extends RecyclerView.ViewHolder{

        public TextView userName;
        public TextView comment;


        public CommentHolder(View itemView) {
            super(itemView);
            userName = (TextView)itemView.findViewById(R.id.userNameCommentTextView);
            comment = (TextView)itemView.findViewById(R.id.userComment);

        }
    }
}

