package application.example.com.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import application.example.com.bakingapp.Fragments.StepsDetailActivityFragment;
import application.example.com.bakingapp.Fragments.StepsIngredientsFragment;

import static application.example.com.bakingapp.MainActivity.isTablet;

/**
 * Created by Dell on 05-08-2017.
 */

public class StepsIngredientsActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (isTablet) {
                StepsIngredientsFragment stepsIngredientsFragment = new StepsIngredientsFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.steps_list, stepsIngredientsFragment)
                        .commit();
                StepsDetailActivityFragment stepsDetailActivityFragment=new StepsDetailActivityFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.steps_detail_frame , stepsDetailActivityFragment)
                        .commit();


            } else {
                StepsIngredientsFragment stepsIngredientsFragment = new StepsIngredientsFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.steps_list, stepsIngredientsFragment)
                        .commit();
            }
        }

    }
}
