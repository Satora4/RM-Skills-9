package ch.ergon.lernende.wmtippspiel.backend.group;

import ch.ergon.lernenden.wmtippspiel.backend.db.Tables;
import ch.ergon.lernenden.wmtippspiel.backend.db.tables.records.GroupRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupRepository {

    private final DSLContext dslContext;

    @Autowired
    public GroupRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public List<Group> getAllGroups() {
        return dslContext.selectFrom(Tables.GROUP).fetch(this::convert);
    }

    private Group convert(GroupRecord groupRecord) {
        Group group = new Group();
        group.setId(groupRecord.getGroupId());
        group.setName(groupRecord.getName());
        return group;
    }
}
