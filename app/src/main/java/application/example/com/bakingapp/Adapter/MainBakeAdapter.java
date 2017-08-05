package application.example.com.bakingapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import application.example.com.bakingapp.Model.Bake;
import application.example.com.bakingapp.R;

/**
 * Created by Dell on 04-08-2017.
 */

public class MainBakeAdapter extends RecyclerView.Adapter<MainBakeAdapter.BakeViewHolder> {
    private final ListItemClickListener mListItemClickListener;
     private final ArrayList<Bake> bakeArrayList;
    public interface ListItemClickListener{
        void onListItemClick(int clickItemIndex);

    }
    public MainBakeAdapter(ListItemClickListener mListItemClickListener, ArrayList<Bake> bakeArrayList) {
        this.mListItemClickListener = mListItemClickListener;
        this.bakeArrayList = bakeArrayList;
    }

    @Override
    public MainBakeAdapter.BakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        int layoutIdForListItem=R.layout.list_item;
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        boolean shouldAttachToBooleanImmediately=false;
        View view=layoutInflater.inflate(layoutIdForListItem , parent , shouldAttachToBooleanImmediately);
        return new BakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainBakeAdapter.BakeViewHolder holder, int position) {
        holder.onBind(position);


    }



    @Override
    public int getItemCount() {
        if (bakeArrayList==null){
            return 0;
        }
        return bakeArrayList.size();
    }
    class BakeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final ImageView imageIcon;
        public final TextView name;
        public final TextView servings;

        public BakeViewHolder(View itemView) {
            super(itemView);
            imageIcon= (ImageView) itemView.findViewById(R.id.main_image);
            name= (TextView) itemView.findViewById(R.id.main_name);
            servings= (TextView) itemView.findViewById(R.id.main_servings);
            itemView.setOnClickListener(this);

        }
        void onBind(int position) {

            if (!bakeArrayList.isEmpty()) {
                Picasso.with(itemView.getContext()).load(bakeArrayList.get(position).getImages()).into(imageIcon);
                name.setText(bakeArrayList.get(position).getName());
                servings.setText(itemView.getContext().getString(R.string.servings) + " " + bakeArrayList.get(position).getServings());
            }
        }

        @Override
        public void onClick(View v) {
            int clickedPosition=getAdapterPosition();
            mListItemClickListener.onListItemClick(clickedPosition);






        }
    }


}
