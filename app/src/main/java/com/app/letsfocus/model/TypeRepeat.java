package com.app.letsfocus.model;

public class TypeRepeat {
    private int id;
    private String name;
    private boolean active;

    public TypeRepeat(int id, String name, boolean active)
    {
        this.id = id;
        this.name = name;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }
}
