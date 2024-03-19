package com.arctic.aurora;

public class Explore {
    private final String userName;
    private final String uri;
    private final String description;
    private final String date;


    public Explore(String userName, String uri, String description, String date) {
        this.userName = userName;
        this.uri = uri;
        this.description = description;
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public String getUri() {
        return uri;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }
}
