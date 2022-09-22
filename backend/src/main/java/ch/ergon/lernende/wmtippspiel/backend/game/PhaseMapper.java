package ch.ergon.lernende.wmtippspiel.backend.game;

import ch.ergon.lernenden.wmtippspiel.backend.db.enums.Phase;
import org.springframework.stereotype.Component;

@Component
public class PhaseMapper {
    public PhaseTO map(Phase phase) {
        return switch (phase) {
            case GROUP_PHASE -> PhaseTO.GROUP_PHASE;
            case ROUND_OF_16 -> PhaseTO.ROUND_OF_16;
            case QUARTER_FINAL -> PhaseTO.QUARTER_FINAL;
            case SEMI_FINAL -> PhaseTO.SEMI_FINAL;
            case FINAL -> PhaseTO.FINAL;
        };
    }
}
