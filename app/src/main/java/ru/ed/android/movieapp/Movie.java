package ru.ed.android.movieapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    public String getImage() {
        return image;
    }

    private String image;
    private String title;

    public Movie(String image, String title) {
        this.image = image;
        this.title = title;
    }

    protected Movie(Parcel in) {
        image = in.readString();
        title = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(title);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                '}';
    }
}
