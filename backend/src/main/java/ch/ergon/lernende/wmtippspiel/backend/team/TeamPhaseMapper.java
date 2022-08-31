package ch.ergon.lernende.wmtippspiel.backend.team;

import ch.ergon.lernenden.wmtippspiel.backend.db.enums.Phase;
import org.springframework.stereotype.Component;

@Component
public class TeamPhaseMapper {
    public TeamPhaseTO map(Phase teamPhase) {
        return switch (teamPhase) {
            case GROUP_PHASE -> TeamPhaseTO.GROUP_PHASE;
            case ROUND_OF_16 -> TeamPhaseTO.ROUND_OF_16;
            case QUARTER_FINAL -> TeamPhaseTO.QUARTER_FINAL;
            case SEMI_FINAL -> TeamPhaseTO.SEMI_FINAL;
            case FINAL -> TeamPhaseTO.FINAL;
        };
    }
}
