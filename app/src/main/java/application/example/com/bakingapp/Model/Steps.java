package application.example.com.bakingapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dell on 05-08-2017.
 */

public class Steps implements Parcelable {
    private int id;
    private String shortDescription;
    private String description;
    public String videoUrl;
    private String thumbnailUrl;
    public Steps(JSONObject stepObj){
        try {
            this.id=stepObj.getInt("id");
            this.shortDescription=stepObj.getString("shortDescription");
            this.description=stepObj.getString("description");
            this.videoUrl=stepObj.getString("videoURL");
            this.thumbnailUrl=stepObj.getString("thumbnailURL");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }


    protected Steps(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoUrl = in.readString();
        thumbnailUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoUrl);
        dest.writeString(thumbnailUrl);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Steps> CREATOR = new Parcelable.Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel in) {
            return new Steps(in);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };
}