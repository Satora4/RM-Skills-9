package ch.ergon.lernende.wmtippspiel.backend.team;

import ch.ergon.lernenden.wmtippspiel.backend.db.enums.TeamPhase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TeamPhaseMapperTest {

    private static final Map<TeamPhase, TeamPhaseTO> EXPECTED_TEAM_PHASE_MAPPINGS = Map.of(
            TeamPhase.GROUP_PHASE, TeamPhaseTO.GROUP_PHASE,
            TeamPhase.ROUND_OF_16, TeamPhaseTO.ROUND_OF_16,
            TeamPhase.QUARTER_FINAL, TeamPhaseTO.QUARTER_FINAL,
            TeamPhase.SEMI_FINAL, TeamPhaseTO.SEMI_FINAL,
            TeamPhase.FINAL, TeamPhaseTO.FINAL
    );

    private final TeamPhaseMapper teamPhaseMapper = new TeamPhaseMapper();

    @ParameterizedTest
    @EnumSource(TeamPhase.class)
    void shouldMapTeamPhase(TeamPhase teamPhase) {
        var expectedTeamPhaseTO = EXPECTED_TEAM_PHASE_MAPPINGS.get(teamPhase);
        Objects.requireNonNull(expectedTeamPhaseTO, "missing mapping for " + teamPhase);
        assertEquals(expectedTeamPhaseTO, teamPhaseMapper.map(teamPhase));
    }
}