package application.example.com.bakingapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import application.example.com.bakingapp.Adapter.IngredientsAdapter;
import application.example.com.bakingapp.Adapter.StepsAdapter;
import application.example.com.bakingapp.Model.Ingredients;
import application.example.com.bakingapp.Model.Steps;
import application.example.com.bakingapp.R;
import application.example.com.bakingapp.StepsDetailActivity;

import static application.example.com.bakingapp.Fragments.BakesFragment.bakeArrayList;
import static application.example.com.bakingapp.MainActivity.isTablet;

/**
 * Created by Dell on 06-08-2017.
 */

public class StepsIngredientsFragment extends Fragment implements StepsAdapter.ListItemClickListener {
    private RecyclerView stepsRecyclerView;
    private RecyclerView ingredientsRecyclerView;
    private StepsAdapter mStepsAdapter;
    private IngredientsAdapter mIngredientsAdapter;
    public static ArrayList<Steps> stepsArrayList;
    private ArrayList<Ingredients> ingredientsArrayList;
    private int index = 0;


    public StepsIngredientsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.steps_ingredients_fragment, container, false);
        stepsRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_steps);
        ingredientsRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_ingredients);
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        index = getActivity().getIntent().getExtras().getInt("items");
        stepsArrayList = bakeArrayList.get(index).getStepsArrayList();
        ingredientsArrayList = bakeArrayList.get(index).getIngredientsArrayList();
        mStepsAdapter = new StepsAdapter(stepsArrayList, this);
        mIngredientsAdapter = new IngredientsAdapter(ingredientsArrayList);
        stepsRecyclerView.setAdapter(mStepsAdapter);
        ingredientsRecyclerView.setAdapter(mIngredientsAdapter);


        return rootView;

    }

    @Override
    public void onItemClick(int clickItemIndex) {
        if (!isTablet) {
            Intent intent = new Intent(getActivity(), StepsDetailActivity.class);
            intent.putExtra("item", clickItemIndex);
            startActivity(intent);

        } else {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            StepsDetailActivityFragment stepsDetailActivityFragment = new StepsDetailActivityFragment();
            stepsDetailActivityFragment.index=clickItemIndex;
            fragmentManager.beginTransaction()
                    .replace(R.id.steps_detail_frame, stepsDetailActivityFragment)
                    .commit();


        }

    }
}
