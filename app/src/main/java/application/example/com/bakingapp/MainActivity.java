package application.example.com.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import application.example.com.bakingapp.Fragments.BakesFragment;

public class MainActivity extends AppCompatActivity {
    public static boolean isTablet = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState==null){
            if(findViewById(R.id.bake_grid)!=null){
                isTablet=true;
                FragmentManager fragmentManager=getSupportFragmentManager();
                BakesFragment bakesFragment=new BakesFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.bake_grid,bakesFragment)
                        .commit();
            }else {
                FragmentManager fragmentManager=getSupportFragmentManager();
                BakesFragment bakesFragment=new BakesFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.frame_bake,bakesFragment)
                        .commit();

            }
        }


    }
}
