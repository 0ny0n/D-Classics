package model;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private String id;
    private String title;
    private String author;
    private String synopsis;
    private int coverResourceId;
    private String price;
    private String category;

    // This constructor has all the fields your other activities need
    public Book(String id, String title, String author, int coverResourceId, String synopsis, String price, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.coverResourceId = coverResourceId;
        this.synopsis = synopsis;
        this.price = price;
        this.category = category;
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getSynopsis() { return synopsis; }
    public int getCoverResourceId() { return coverResourceId; }
    public String getPrice() { return price; }
    public String getCategory() { return category; }


    // --- PARCELABLE IMPLEMENTATION ---
    protected Book(Parcel in) {
        id = in.readString();
        title = in.readString();
        author = in.readString();
        coverResourceId = in.readInt();
        synopsis = in.readString();
        price = in.readString();
        category = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeInt(coverResourceId);
        dest.writeString(synopsis);
        dest.writeString(price);
        dest.writeString(category);
    }
}