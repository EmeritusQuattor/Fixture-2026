package com.mundial.model;

public class Match {
    private final int id;
    private final int visitorId;
    private final int localId;
    private final int groupId;
    private final String phase;
    private final String date;
    private final int localGoals;
    private final int visitorGoals;
    private final String status;

    public Match(int id, int visitorId,int localId, int groupId, String phase, String date,
                 int localGoals, int visitorGoals, String status){
        this.id = id;
        this.visitorId = visitorId;
        this.localId = localId;
        this.groupId = groupId;
        this.phase = phase;
        this.date = date;
        this.localGoals = localGoals;
        this.visitorGoals = visitorGoals;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getVisitorId() {
        return visitorId;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getPhase() {
        return phase;
    }

    public String getDate() {
        return date;
    }

    public int getLocalGoals() {
        return localGoals;
    }

    public int getVisitorGoals() {
        return visitorGoals;
    }

    public String getStatus() {
        return status;
    }

    public int getLocalId() {
        return localId;
    }
}
