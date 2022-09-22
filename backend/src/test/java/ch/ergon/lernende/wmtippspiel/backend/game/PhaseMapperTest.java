package ch.ergon.lernende.wmtippspiel.backend.game;

import ch.ergon.lernenden.wmtippspiel.backend.db.enums.Phase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PhaseMapperTest {

    private static final Map<Phase, PhaseTO> EXPECTED_TEAM_PHASE_MAPPINGS = Map.of(
            Phase.GROUP_PHASE, PhaseTO.GROUP_PHASE,
            Phase.ROUND_OF_16, PhaseTO.ROUND_OF_16,
            Phase.QUARTER_FINAL, PhaseTO.QUARTER_FINAL,
            Phase.SEMI_FINAL, PhaseTO.SEMI_FINAL,
            Phase.FINAL, PhaseTO.FINAL
    );

    private final PhaseMapper phaseMapper = new PhaseMapper();

    @ParameterizedTest
    @EnumSource(Phase.class)
    void shouldMapPhase(Phase Phase) {
        var expectedTeamPhaseTO = EXPECTED_TEAM_PHASE_MAPPINGS.get(Phase);
        Objects.requireNonNull(expectedTeamPhaseTO, "missing mapping for " + Phase);
        assertEquals(expectedTeamPhaseTO, phaseMapper.map(Phase));
    }
}