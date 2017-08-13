package application.example.com.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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

import static application.example.com.bakingapp.Fragments.BakesFragment.bakeArrayList;

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    public static final String TAG = ListRemoteViewsFactory.class.getSimpleName();
    Context mContext;

    public ListRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        try {
            new FetchBakingTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return bakeArrayList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_view);
        try {
            remoteViews.setImageViewBitmap(R.id.icon, BitmapFactory.decodeStream(new URL(bakeArrayList.get(position).getImages()).openConnection().getInputStream()));
        } catch (IOException e) {
        }
        remoteViews.setTextViewText(R.id.name, bakeArrayList.get(position).getName());
        remoteViews.setTextViewText(R.id.servings, mContext.getString(R.string.servings) + " " + bakeArrayList.get(position).getServings());
        for (int i = 0; i < bakeArrayList.get(position).getIngredientsArrayList().size(); i++) {
            RemoteViews ing = new RemoteViews(mContext.getPackageName(), R.layout.activity_ingredient_item);
            ing.setTextViewText(R.id.ingredients, bakeArrayList.get(position).getIngredientsArrayList().get(i).getIngredient());
            ing.setTextViewText(R.id.measure, bakeArrayList.get(position).getIngredientsArrayList().get(i).getMeasure());
            ing.setTextViewText(R.id.quantity, bakeArrayList.get(position).getIngredientsArrayList().get(i).getQuantity() + "");
            remoteViews.addView(R.id.ingerdient_list, ing);
        }

        Intent intent = new Intent();
        intent.putExtra("item", position);
        remoteViews.setOnClickFillInIntent(R.id.bb, intent);
        return remoteViews;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
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
