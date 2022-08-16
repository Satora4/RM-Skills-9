package ch.ergon.lernende.wmtippspiel.backend.team;

import ch.ergon.lernenden.wmtippspiel.backend.db.enums.TeamPhase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TeamPhaseMapperTest {

    private final TeamPhaseMapper teamPhaseMapper = new TeamPhaseMapper();

    @ParameterizedTest
    @EnumSource(TeamPhase.class)
    void shouldMapTeamPhase(TeamPhase teamPhase) {
        assertDoesNotThrow(() -> teamPhaseMapper.map(teamPhase));
    }
}