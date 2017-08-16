package application.example.com.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import application.example.com.bakingapp.Model.Bake;
import application.example.com.bakingapp.Model.Ingredients;

import static application.example.com.bakingapp.Fragments.BakesFragment.bakeArrayList;

/**
 * Created by Dell on 15-08-2017.
 */

 public class LisstRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    public static final String TAG = LisstRemoteViewsFactory.class.getSimpleName();
    Context mContext;

    public LisstRemoteViewsFactory(Context applicationContext,Intent intent) {
        mContext = applicationContext;

    }

    @Override
    public void onCreate() {
    }
    @Override
    public void onDestroy() {
        bakeArrayList.clear();

    }
    @Override
    public int getCount() {
        if (bakeArrayList==null){
            return 0;
        }
        return bakeArrayList.size();
    }



    @Override
    public void onDataSetChanged() {
        try {
            new LisstRemoteViewsFactory.FetchBakingTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }





    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
       Bake bakes=bakeArrayList.get(position);
        remoteViews.setTextViewText(R.id.widget_item_recipe_name,bakes.getName());
        String ingredients="";
        for (Ingredients ingredient : bakes.getIngredientsArrayList()){
            ingredients += " - " + ingredient.getIngredient() + "\n";
        }
        remoteViews.setTextViewText(R.id.widget_item_ingredients,ingredients);
        Bundle extras=new Bundle();
        extras.putParcelable(mContext.getString(R.string.extra_recipe),bakes);
        Intent fillIntent=new Intent();
        fillIntent.putExtras(extras);
        remoteViews.setOnClickFillInIntent(R.id.recipe_widget_item,fillIntent);
        return remoteViews;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public class FetchBakingTask extends AsyncTask<Void, Void, ArrayList<Bake>> {
        public String bakingInfoUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";


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


