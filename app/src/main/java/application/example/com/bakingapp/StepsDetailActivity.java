package application.example.com.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import application.example.com.bakingapp.Fragments.StepsDetailActivityFragment;

/**
 * Created by Dell on 08-08-2017.
 */

public class StepsDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            StepsDetailActivityFragment stepsDetailActivityFragment = new StepsDetailActivityFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.steps_detail_frame, stepsDetailActivityFragment)
                    .commit();

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
