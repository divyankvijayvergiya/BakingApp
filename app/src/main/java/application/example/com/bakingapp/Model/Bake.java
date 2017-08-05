package application.example.com.bakingapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Dell on 05-08-2017.
 */

public class Bake implements Parcelable {


    private String name;
    private ArrayList<Ingredients> ingredientsArrayList;
    private ArrayList<Steps>stepsArrayList;
    private String servings;
    private String images;
    public Bake(JSONObject rootObject){
        try {
            this.name=rootObject.getString("name");
            this.ingredientsArrayList=new ArrayList<>();
            JSONArray ingredientsJson=rootObject.getJSONArray("ingredients");

            for(int i=0 ; i<ingredientsJson.length() ; i++){
                ingredientsArrayList.add(new Ingredients(ingredientsJson.getJSONObject(i)));
            }
            this.stepsArrayList=new ArrayList<>();
            JSONArray stepsJson=rootObject.getJSONArray("steps");
            for(int i=0 ; i<stepsJson.length() ; i++){
                stepsArrayList.add(new Steps(stepsJson.getJSONObject(i)));
            }
            this.servings=rootObject.getString("servings");
            this.images=rootObject.getString("image");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String getName() {
        return name;
    }

    public ArrayList<Ingredients> getIngredientsArrayList() {
        return ingredientsArrayList;
    }

    public ArrayList<Steps> getStepsArrayList() {
        return stepsArrayList;
    }

    public String getServings() {
        return servings;
    }

    public String getImages() {
        return images;
    }

    protected Bake(Parcel in) {
        name = in.readString();
        if (in.readByte() == 0x01) {
            ingredientsArrayList = new ArrayList<Ingredients>();
            in.readList(ingredientsArrayList, Ingredients.class.getClassLoader());
        } else {
            ingredientsArrayList = null;
        }
        servings = in.readString();
        images = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        if (ingredientsArrayList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ingredientsArrayList);
        }
        dest.writeString(servings);
        dest.writeString(images);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Bake> CREATOR = new Parcelable.Creator<Bake>() {
        @Override
        public Bake createFromParcel(Parcel in) {
            return new Bake(in);
        }

        @Override
        public Bake[] newArray(int size) {
            return new Bake[size];
        }
    };
}