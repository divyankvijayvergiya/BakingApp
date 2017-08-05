package application.example.com.bakingapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dell on 05-08-2017.
 */

public class Ingredients implements Parcelable {


    private double quantity;
    private String measure;
    private String ingredient;
    public Ingredients(JSONObject ingredientsObj){
        try {
            this.quantity=ingredientsObj.getDouble("quantity");
            this.measure=ingredientsObj.getString("measure");
            this.ingredient=ingredientsObj.getString("ingredient");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    protected Ingredients(Parcel in) {
        quantity = in.readDouble();
        measure = in.readString();
        ingredient = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Ingredients> CREATOR = new Parcelable.Creator<Ingredients>() {
        @Override
        public Ingredients createFromParcel(Parcel in) {
            return new Ingredients(in);
        }

        @Override
        public Ingredients[] newArray(int size) {
            return new Ingredients[size];
        }
    };
}