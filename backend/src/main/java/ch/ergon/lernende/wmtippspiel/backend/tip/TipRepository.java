package ch.ergon.lernende.wmtippspiel.backend.tip;

import ch.ergon.lernende.wmtippspiel.backend.game.Game;
import ch.ergon.lernende.wmtippspiel.backend.team.Team;
import ch.ergon.lernende.wmtippspiel.backend.user.User;
import ch.ergon.lernenden.wmtippspiel.backend.db.tables.GameTable;
import ch.ergon.lernenden.wmtippspiel.backend.db.tables.TeamTable;
import ch.ergon.lernenden.wmtippspiel.backend.db.tables.UserTable;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static ch.ergon.lernenden.wmtippspiel.backend.db.Tables.*;

@Repository
public class TipRepository {

    private static final UserTable USER_ALIAS = USER.as("user");
    private static final GameTable GAME_ALIAS = GAME.as("GAME");
    private static final TeamTable TEAM_ALIAS_1 = TEAM.as("t1");
    private static final TeamTable TEAM_ALIAS_2 = TEAM.as("t2");

    private final DSLContext dslContext;

    @Autowired
    public TipRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public List<Tip> getAllTip() {
        return dslContext.select(TIP.TIP_ID,
                        TIP.TIP_TEAM1,
                        TIP.TIP_TEAM2,
                        USER_ALIAS.USER_ID,
                        USER_ALIAS.FIRST_NAME,
                        USER_ALIAS.LAST_NAME,
                        USER_ALIAS.EMAIL,
                        GAME_ALIAS.GAME_ID,
                        GAME_ALIAS.GAME_TIME,
                        GAME_ALIAS.GAME_LOCATION,
                        GAME_ALIAS.POINTS_TEAM1,
                        GAME_ALIAS.POINTS_TEAM2,
                        TEAM_ALIAS_1.TEAM_ID,
                        TEAM_ALIAS_1.COUNTRY,
                        TEAM_ALIAS_2.TEAM_ID,
                        TEAM_ALIAS_2.COUNTRY)
                .from(TIP)
                .join(USER_ALIAS).on(USER_ALIAS.USER_ID.eq(TIP.USER_ID))
                .join(GAME_ALIAS).on(GAME_ALIAS.GAME_ID.eq(TIP.GAME_ID))
                .join(TEAM_ALIAS_1).on(TEAM_ALIAS_1.TEAM_ID.eq(GAME_ALIAS.TEAM_ID1))
                .join(TEAM_ALIAS_2).on(TEAM_ALIAS_2.TEAM_ID.eq(GAME_ALIAS.TEAM_ID2))
                .fetch(this::convert);
    }

    private Tip convert(Record record) {
        Tip tip = new Tip();
        tip.setId(record.get(TIP.TIP_ID));
        tip.setTipTeam1(record.get(TIP.TIP_TEAM1));
        tip.setTipTeam2(record.get(TIP.TIP_TEAM2));

        User user = new User();
        user.setId(record.get(USER_ALIAS.USER_ID));
        user.setFirstName(record.get(USER_ALIAS.FIRST_NAME));
        user.setLastName(record.get(USER_ALIAS.LAST_NAME));
        user.setEmail(record.get(USER_ALIAS.EMAIL));
        tip.setUser(user);

        Game game = new Game();
        game.setId(record.get(GAME_ALIAS.GAME_ID));
        game.setGameTime(record.get(GAME_ALIAS.GAME_TIME));
        game.setGameLocation((record.get(GAME_ALIAS.GAME_LOCATION)));
        game.setPointsTeam1(record.get(GAME_ALIAS.POINTS_TEAM1));
        game.setPointsTeam2(record.get(GAME_ALIAS.POINTS_TEAM2));

        Team team1 = new Team();
        team1.setId(record.get(TEAM_ALIAS_1.TEAM_ID));
        team1.setCountry(record.get(TEAM_ALIAS_1.COUNTRY));
        game.setTeam1(team1);

        Team team2 = new Team();
        team2.setId(record.get(TEAM_ALIAS_2.TEAM_ID));
        team2.setCountry(record.get(TEAM_ALIAS_2.COUNTRY));
        game.setTeam2(team2);

        tip.setGame(game);

        return tip;

    }
}
