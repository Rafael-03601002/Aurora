package com.arctic.aurora;

public class Diet {
    private final String name;
    private final int calories;

    public Diet(String name, int calories) {
        this.name = name;
        this.calories = calories;
    }

    public String getName() {
        return name;
    }
    public int getCalories() {
        return calories;
    }
}
