package ch.ergon.lernende.wmtippspiel.backend.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GroupController {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupController(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @GetMapping("/groups")
    public List<GroupTO> getAllGroups() {
        return groupRepository.getAllGroups().stream().map(Group::toGroupTO).collect(Collectors.toList());
    }
}
