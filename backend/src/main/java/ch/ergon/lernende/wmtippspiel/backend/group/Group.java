package ch.ergon.lernende.wmtippspiel.backend.group;

import ch.ergon.lernende.wmtippspiel.backend.team.Team;

import java.util.List;
import java.util.Objects;

public class Group {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return id == group.id && Objects.equals(name, group.name) && Objects.equals(groupMembers, group.groupMembers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
