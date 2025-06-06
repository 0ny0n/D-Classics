package model;

import android.os.Parcel;
import android.os.Parcelable;

public class Store implements Parcelable {
    private String id;
    private String name;
    private int imageResId; // Use String for URL if loading from network
    private String popularity; // e.g., "83" or some metric
    private String type; // "Popular", "Near You"

    public Store(String id, String name, int imageResId, String popularity, String type) {
        this.id = id;
        this.name = name;
        this.imageResId = imageResId;
        this.popularity = popularity;
        this.type = type;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public int getImageResId() { return imageResId; }
    public String getPopularity() { return popularity; }
    public String getType() { return type; }

    // Parcelable implementation
    protected Store(Parcel in) {
        id = in.readString();
        name = in.readString();
        imageResId = in.readInt();
        popularity = in.readString();
        type = in.readString();
    }

    public static final Creator<Store> CREATOR = new Creator<Store>() {
        @Override
        public Store createFromParcel(Parcel in) {
            return new Store(in);
        }

        @Override
        public Store[] newArray(int size) {
            return new Store[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(imageResId);
        dest.writeString(popularity);
        dest.writeString(type);
    }
}
