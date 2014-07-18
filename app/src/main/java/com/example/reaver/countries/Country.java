package com.example.reaver.countries;

/**
 * Created by Reaver on 18.07.2014.
 */
public class Country {
    private String name;
    private int area;
    private int population;

    public Country(String name, int area, int population) {
        this.name = name;
        this.area = area;
        this.population = population;
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
}
