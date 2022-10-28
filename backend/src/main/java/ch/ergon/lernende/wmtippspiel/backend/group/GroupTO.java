package ch.ergon.lernende.wmtippspiel.backend.group;

import ch.ergon.lernende.wmtippspiel.backend.team.Team;

import java.util.List;

public class GroupTO {
    
    private int id;
    private String name;
    private List<Team> groupMembers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Team> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<Team> groupMembers) {
        this.groupMembers = List.copyOf(groupMembers);
    }
}
