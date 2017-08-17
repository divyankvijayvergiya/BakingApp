package application.example.com.bakingapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import application.example.com.bakingapp.Model.Ingredients;
import application.example.com.bakingapp.R;

/**
 * Created by Dell on 05-08-2017.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {
    private final ArrayList<Ingredients> ingredientsArrayList;


    public IngredientsAdapter(ArrayList<Ingredients> ingredientsArrayList) {

        this.ingredientsArrayList = ingredientsArrayList;
    }

    @Override
    public IngredientsAdapter.IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.activity_ingredient_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = layoutInflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsAdapter.IngredientsViewHolder holder, int position) {
        holder.onBind(position);

    }

    @Override
    public int getItemCount() {
        if (ingredientsArrayList == null) {
            return 0;
        } else {
            return ingredientsArrayList.size();
        }
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder {
        public final TextView ingredientsTextView;
        public final TextView quantityTextView;
        public final TextView measure;

        public IngredientsViewHolder(View itemView) {
            super(itemView);
            ingredientsTextView = (TextView) itemView.findViewById(R.id.ingredients);
            quantityTextView = (TextView) itemView.findViewById(R.id.quantity);
            measure = (TextView) itemView.findViewById(R.id.measure);

        }

        void onBind(int position) {
            if (!ingredientsArrayList.isEmpty()) {
                ingredientsTextView.setText(ingredientsArrayList.get(position).getIngredient());
                quantityTextView.setText(ingredientsArrayList.get(position).getQuantity() + "");
                measure.setText(ingredientsArrayList.get(position).getMeasure());
            }
        }


    }
}
