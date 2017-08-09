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

import application.example.com.bakingapp.Model.Steps;
import application.example.com.bakingapp.R;

/**
 * Created by Dell on 05-08-2017.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder> {
    private final ArrayList<Steps> stepsArrayList;
    private final ListItemClickListener onClickListenere;
    public interface ListItemClickListener{
        void onItemClick(int clickItemIndex);

    }

    public StepsAdapter(ArrayList<Steps> stepsArrayList, ListItemClickListener onClickListenere) {
        this.stepsArrayList = stepsArrayList;
        this.onClickListenere = onClickListenere;
    }

    @Override
    public StepsAdapter.StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context=parent.getContext();
        int layoutIdForListItem= R.layout.list_item;
        boolean shouldAttachToParentImmediately =false;
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view =layoutInflater.inflate(layoutIdForListItem , parent , shouldAttachToParentImmediately);
        return new StepViewHolder(view);



    }

    @Override
    public void onBindViewHolder(StepsAdapter.StepViewHolder holder, int position) {
        holder.onBind(position);

    }

    @Override
    public int getItemCount() {
        if(stepsArrayList==null) {
            return 0;
        }
        else {
            return stepsArrayList.size();
        }
    }
    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final ImageView icon;
        public final TextView shortDescription;
        public final TextView id;

        public StepViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.main_image);
            shortDescription= (TextView) itemView.findViewById(R.id.main_name);
            id= (TextView) itemView.findViewById(R.id.main_servings);
            itemView.setOnClickListener(this);


        }
        void onBind(int position){

            if(!stepsArrayList.isEmpty()){
                if (!stepsArrayList.get(position).getThumbnailUrl().isEmpty()){
                    Picasso.with(itemView.getContext()).load(stepsArrayList.get(position).getThumbnailUrl())
                            .placeholder(R.drawable.video).into(icon);
                }
                else {
                    icon.setImageResource(R.drawable.video);
                }
                shortDescription.setText(stepsArrayList.get(position).getShortDescription());
                id.setText(itemView.getContext().getString(R.string.step)+" "+stepsArrayList.get(position).getId());
            }
        }

        @Override
        public void onClick(View v) {
            int clickedPosition=getAdapterPosition();
            onClickListenere.onItemClick(clickedPosition);


        }
    }
}
