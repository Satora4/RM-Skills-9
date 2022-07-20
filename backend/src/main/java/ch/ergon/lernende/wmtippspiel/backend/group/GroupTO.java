package ch.ergon.lernende.wmtippspiel.backend.group;

import ch.ergon.lernende.wmtippspiel.backend.team.Team;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GroupTO {

    @JsonProperty("groupId")
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
