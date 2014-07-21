package com.example.reaver.countries;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Reaver on 18.07.2014.
 */
public class Country implements Parcelable {
    private String name;
    private int area;
    private int population;

    public Country(String name, int area, int population) {
        this.name = name;
        this.area = area;
        this.population = population;
    }

    public Country (Parcel in) {
        this.name = in.readString();
        this.area = in.readInt();
        this.population = in.readInt();
    }

    public String getName() {
        return name;
    }

    public int getArea() {
        return area;
    }

    public int getPopulation() {
        return population;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(area);
        parcel.writeInt(population);
    }

    public static  final Parcelable.Creator<Country> CREATOR =
            new Parcelable.Creator<Country> () {
        public Country createFromParcel (Parcel in) {
            return new Country(in);
        }

        public Country[] newArray(int size) {
            return new Country[size];
        }
    };
}
