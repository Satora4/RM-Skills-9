package ch.ergon.lernende.wmtippspiel.backend.team;

import ch.ergon.lernenden.wmtippspiel.backend.db.enums.TeamPhase;
import org.springframework.stereotype.Component;

@Component
public class TeamPhaseMapper {
    public TeamPhaseTO map(TeamPhase teamPhase) {
        return TeamPhaseTO.valueOf(teamPhase.name());
    }
}
