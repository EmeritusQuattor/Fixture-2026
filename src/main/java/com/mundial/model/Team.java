package com.mundial.model;

public class Team {
    private final int id;
    private final String name;
    private final String country;
    private final int groupId;

    public Team(int id, String name, String country, int groupId){
        this.id = id;
        this.name = name;
        this.country = country;
        this.groupId = groupId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public int getGroupId() {
        return groupId;
    }
}
