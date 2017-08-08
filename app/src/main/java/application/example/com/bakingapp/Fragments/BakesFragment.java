package application.example.com.bakingapp.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import application.example.com.bakingapp.Adapter.MainBakeAdapter;
import application.example.com.bakingapp.Model.Bake;
import application.example.com.bakingapp.NetworkUtils;
import application.example.com.bakingapp.R;
import application.example.com.bakingapp.StepsIngredientsActivity;

import static application.example.com.bakingapp.MainActivity.isTablet;

/**
 * Created by Dell on 05-08-2017.
 */

public class BakesFragment extends Fragment implements MainBakeAdapter.ListItemClickListener {
    private static final String TAG = BakesFragment.class.getSimpleName();
    public static  ArrayList<Bake> bakeArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MainBakeAdapter mAdapter;



    public BakesFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        new FetchBakingTask(getActivity()).execute();
        return rootView;

    }

    @Override
    public void onListItemClick(int clickItemIndex) {
        Intent intent=new Intent(getActivity(),StepsIngredientsActivity.class);
        intent.putExtra("items",clickItemIndex);
        startActivity(intent);


    }

    public void setData(ArrayList<Bake> bakeArrayList) {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_bake_list);
        RecyclerView.LayoutManager layoutManager;
        if (isTablet) {
            layoutManager = new GridLayoutManager(getActivity(), 2);

        } else {
            layoutManager = new LinearLayoutManager(getActivity());
        }
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MainBakeAdapter(this, bakeArrayList);
        recyclerView.setAdapter(mAdapter);

    }


    public class FetchBakingTask extends AsyncTask<Void, Void, ArrayList<Bake>> {
        public String bakingInfoUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
        private ProgressDialog dialog;
        public FetchBakingTask(Activity activity) {
            dialog = new ProgressDialog(activity);

        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Please wait...");
            dialog.show();
        }

        @Override
        protected ArrayList<Bake> doInBackground(Void... params) {



            try {
                URL url = new URL(bakingInfoUrl);
                String response = NetworkUtils.makeHttpRequest(url);
                ArrayList<Bake> jsonBake = extractBakeJson(response);
                return jsonBake;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d(TAG, "Error with creating URL ", e);
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Error with creating connection ", e);
                return null;
            }


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<Bake> bakes) {
            dialog.dismiss();

            setData(bakes);
            bakeArrayList=bakes;
            super.onPostExecute(bakes);




        }

        public ArrayList<Bake> extractBakeJson(String bakeDataString) {
            ArrayList<Bake> bakes = new ArrayList<>();
            try {
                JSONArray bakeArray = new JSONArray(bakeDataString);
                for (int i = 0; i < bakeArray.length(); i++) {
                    bakes.add(new Bake(bakeArray.getJSONObject(i)));
                    Log.e("name: ", bakes.get(i).getName());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return bakes;
        }


    }

}
