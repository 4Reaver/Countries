package com.example.reaver.countries;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Reaver on 18.07.2014.
 */
public class Country implements Parcelable {
    private boolean isChecked;
    private int area;
    private int iconID;
    private int fullFlagID;
    private int population;
    private static Context context;
    private String name;

    public Country(Context context, String name, int area, int population) {
        if ( Country.context == null ) {
            Country.context = context;
        }

        this.name = name;
        this.area = area;
        this.population = population;
        isChecked = false;
    }

    public Country (Parcel in) {
        this.name = in.readString();
        this.area = in.readInt();
        this.population = in.readInt();
        this.isChecked = in.readByte() != 0;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void invertIsChecked() {
        isChecked = !isChecked;
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

    public static int getIconID(Country country, Context context) {
        if ( country.iconID != 0 ) {
            return country.iconID;
        }

        int id;
        Resources resources = context.getResources();
        String name = country.getName();

        name = name.toLowerCase().replace(" ", "_").concat("_icon");
        id = resources.getIdentifier(name, "drawable", context.getPackageName());
        country.iconID = id;

        return id;
    }

    public static int getFullFlagID(Country country, Context context) {
        if ( country.fullFlagID != 0 ) {
            return country.fullFlagID;
        }

        int id;
        Resources resources = context.getResources();
        String name = country.getName();

        name = name.toLowerCase().replace(" ", "_").concat("_full");
        id = resources.getIdentifier(name, "drawable", context.getPackageName());
        country.fullFlagID = id;

        return id;
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
        parcel.writeByte((byte) (isChecked ? 1 : 0));
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
