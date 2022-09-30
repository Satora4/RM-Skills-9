package ch.ergon.lernende.wmtippspiel.backend.tip;

import ch.ergon.lernende.wmtippspiel.backend.game.Game;
import ch.ergon.lernende.wmtippspiel.backend.game.GameRepository;
import ch.ergon.lernende.wmtippspiel.backend.user.User;
import ch.ergon.lernenden.wmtippspiel.backend.db.tables.TeamTable;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static ch.ergon.lernenden.wmtippspiel.backend.db.Tables.*;

@Repository
public class TipRepository {

    private static final TeamTable TEAM_ALIAS_1 = TEAM.as("t1");
    private static final TeamTable TEAM_ALIAS_2 = TEAM.as("t2");

    private final DSLContext dslContext;

    @Autowired
    public TipRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public List<Tip> getAllTip() {
        return getTips(DSL.noCondition());
    }

    public List<Tip> getTipsByUserId(int userId) {
        return getTips(USER.USER_ID.eq(userId));
    }

    private List<Tip> getTips(Condition condition) {
        return getTipsFromDB(condition);
    }

    private List<Tip> getTipsFromDB(Condition condition) {
        return dslContext.select(TIP.TIP_ID,
                        TIP.TIP_TEAM1,
                        TIP.TIP_TEAM2,
                        TIP.POINTS,
                        USER.USER_ID,
                        USER.FIRST_NAME,
                        USER.LAST_NAME,
                        USER.EMAIL,
                        USER.POINTS,
                        GAME.GAME_ID,
                        GAME.GAME_TIME,
                        GAME.GAME_LOCATION,
                        GAME.GOALS_TEAM1,
                        GAME.GOALS_TEAM2,
                        GAME.PHASE,
                        TEAM_ALIAS_1.TEAM_ID,
                        TEAM_ALIAS_1.COUNTRY,
                        TEAM_ALIAS_2.TEAM_ID,
                        TEAM_ALIAS_2.COUNTRY)
                .from(TIP)
                .join(USER).on(USER.USER_ID.eq(TIP.USER_ID))
                .join(GAME).on(GAME.GAME_ID.eq(TIP.GAME_ID))
                .join(TEAM_ALIAS_1).on(TEAM_ALIAS_1.TEAM_ID.eq(GAME.TEAM1_ID))
                .join(TEAM_ALIAS_2).on(TEAM_ALIAS_2.TEAM_ID.eq(GAME.TEAM2_ID))
                .where(condition)
                .fetch(this::convert);
    }

    public void addTip(Tip tip) {
        dslContext.insertInto(TIP)
                .set(TIP.USER_ID, tip.getUser().getId())
                .set(TIP.TIP_TEAM1, tip.getTipTeam1())
                .set(TIP.TIP_TEAM2, tip.getTipTeam2())
                .set(TIP.GAME_ID, tip.getGame().getId())
                .execute();
    }

    public void putTip(Tip tip) {
        dslContext.update(TIP)
                .set(TIP.TIP_TEAM1, tip.getTipTeam1())
                .set(TIP.TIP_TEAM2, tip.getTipTeam2())
                .where(TIP.GAME_ID.eq(tip.getGame().getId()).and(TIP.USER_ID.eq(tip.getUser().getId())))
                .execute();
    }

    public void updateTipPoints(Tip tip) {
        dslContext.update(TIP)
                .set(TIP.POINTS, tip.getPoints())
                .where(TIP.TIP_ID.eq(tip.getId()))
                .execute();
    }

    private Tip convert(Record record) {
        Tip tip = new Tip();
        tip.setId(record.get(TIP.TIP_ID));
        tip.setTipTeam1(record.get(TIP.TIP_TEAM1));
        tip.setTipTeam2(record.get(TIP.TIP_TEAM2));
        tip.setPoints(record.get(TIP.POINTS));

        User user = new User();
        user.setId(record.get(USER.USER_ID));
        user.setFirstName(record.get(USER.FIRST_NAME));
        user.setLastName(record.get(USER.LAST_NAME));
        user.setEmail(record.get(USER.EMAIL));
        user.setPoints(record.get(USER.POINTS));
        tip.setUser(user);

        Game game = new Game();
        game.setId(record.get(GAME.GAME_ID));
        game.setGameTime(record.get(GAME.GAME_TIME));
        game.setGameLocation(record.get(GAME.GAME_LOCATION));
        game.setPhase(record.get(GAME.PHASE));

        if (record.get(GAME.GOALS_TEAM1) != null) {
            game.setPointsTeam1(record.get(GAME.GOALS_TEAM1));
        }

        if (record.get(GAME.GOALS_TEAM2) != null) {
            game.setPointsTeam2(record.get(GAME.GOALS_TEAM2));
        }

        GameRepository.convertTeams(record, game, TEAM_ALIAS_1, TEAM_ALIAS_2);

        tip.setGame(game);

        return tip;
    }
}
