package com.arctic.aurora;

public class Diet {
    private final String name;
    private final float calories;

    public Diet(String name, float calories) {
        this.name = name;
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public float getCalories() {
        return calories;
    }
}
