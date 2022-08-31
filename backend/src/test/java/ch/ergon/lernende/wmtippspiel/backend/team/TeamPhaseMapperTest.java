package ch.ergon.lernende.wmtippspiel.backend.team;

import ch.ergon.lernenden.wmtippspiel.backend.db.enums.Phase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PhaseMapperTest {

    private static final Map<Phase, TeamPhaseTO> EXPECTED_TEAM_PHASE_MAPPINGS = Map.of(
            Phase.GROUP_PHASE, TeamPhaseTO.GROUP_PHASE,
            Phase.ROUND_OF_16, TeamPhaseTO.ROUND_OF_16,
            Phase.QUARTER_FINAL, TeamPhaseTO.QUARTER_FINAL,
            Phase.SEMI_FINAL, TeamPhaseTO.SEMI_FINAL,
            Phase.FINAL, TeamPhaseTO.FINAL
    );

    private final TeamPhaseMapper teamPhaseMapper = new TeamPhaseMapper();

    @ParameterizedTest
    @EnumSource(Phase.class)
    void shouldMapPhase(Phase Phase) {
        var expectedTeamPhaseTO = EXPECTED_TEAM_PHASE_MAPPINGS.get(Phase);
        Objects.requireNonNull(expectedTeamPhaseTO, "missing mapping for " + Phase);
        assertEquals(expectedTeamPhaseTO, teamPhaseMapper.map(Phase));
    }
}