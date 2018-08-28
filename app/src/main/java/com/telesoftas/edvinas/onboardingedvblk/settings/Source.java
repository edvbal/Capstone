package com.telesoftas.edvinas.onboardingedvblk.settings;

public class Source {
    private String id;
    private final String name;

    public Source(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Source(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
