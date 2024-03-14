package com.arctic.aurora;

public class Program {

    private final int seq;
    private final String name;
    private final int time;

    public Program(int seq, String name, int time) {
        this.seq = seq;
        this.name = name;
        this.time = time;
    }

    // getter and setter
    public int getSeq() {
        return seq;
    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }
}
