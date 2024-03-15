package com.arctic.aurora;

public class Program {

    private final String name;
    private final int time;

    public Program(String name, int time) {
        this.name = name;
        this.time = time;
    }

    // getter and setter
    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }
}
