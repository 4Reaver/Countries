package com.example.reaver.countries;

/**
 * Created by Reaver on 31.07.2014.
 */
public class Person {
    private boolean isChecked;
    private String name;
    private String residence;

    public Person(String name, String residence) {
        this.name = name;
        this.residence = residence;
        isChecked = false;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public void invertIsChecked() {
        this.isChecked = !isChecked;
    }

    public String getName() {
        return name;
    }

    public String getResidence() {
        return residence;
    }
}
