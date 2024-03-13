package com.arctic.aurora;

public class Program {

    private final String weekday;
    private final String name;
    private final int time;

    public Program(String weekday, String name, int time) {
        this.weekday = weekday;
        this.name = name;
        this.time = time;
    }

    // getter and setter
    public String getWeekday() {
        return weekday;
    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }
}
