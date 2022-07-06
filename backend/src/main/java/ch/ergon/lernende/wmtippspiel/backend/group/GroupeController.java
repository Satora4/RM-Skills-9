package ch.ergon.lernende.wmtippspiel.backend.group;

import ch.ergon.lernenden.wmtippspiel.backend.db.tables.records.GroupRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GroupeController {
    private GroupRepository groupRepository;

    @Autowired
    public GroupeController(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @GetMapping("/group")
    public List<GroupTO> getAllGroups() {
        return groupRepository.getAllGroups().stream().map(this::convert).collect(Collectors.toList());
    }

    private GroupTO convert(GroupRecord groupRecord) {
        GroupTO groupTO = new GroupTO();
        groupTO.setId(groupRecord.getGroupId());
        groupTO.setName(groupRecord.getName());
        return groupTO;
    }
}
