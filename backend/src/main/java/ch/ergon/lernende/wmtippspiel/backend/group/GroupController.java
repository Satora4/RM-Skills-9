package ch.ergon.lernende.wmtippspiel.backend.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GroupController {
    private GroupRepository groupRepository;

    @Autowired
    public GroupController(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @GetMapping("/group")
    public List<GroupTO> getAllGroups() {
        return groupRepository.getAllGroups().stream().map(this::convert).collect(Collectors.toList());
    }

    private GroupTO convert(Group group) {
        GroupTO groupTO = new GroupTO();
        groupTO.setId(group.getId());
        groupTO.setName(group.getName());
        return groupTO;
    }
}
