package ch.ergon.lernende.wmtippspiel.backend.tip;

import ch.ergon.lernende.wmtippspiel.backend.game.Game;
import ch.ergon.lernende.wmtippspiel.backend.team.Team;
import ch.ergon.lernende.wmtippspiel.backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("tip")
public class TipController {
    private final TipRepository tipRepository;

    @Autowired
    public TipController(TipRepository tipRepository) {
        this.tipRepository = tipRepository;
    }

    @GetMapping
    public List<TipTO> getTips(@RequestParam(required = false, name = "userId") Integer userId) {
        if (userId != null) {
            return convertAllToTipTO(tipRepository.getTipsByUserId(userId));
        } else {
            return convertAllToTipTO(tipRepository.getAllTip());
        }
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addTip(@RequestBody TipTO tipTO) {
        tipRepository.addTip(convert(tipTO));
    }

    private List<TipTO> convertAllToTipTO(Collection<Tip> tips) {
        return tips.stream().map(this::convert).collect(toList());
    }

    private TipTO convert(Tip tip) {

        User user = tip.getUser();
        Game game = tip.getGame();

        TipTO tipTO = new TipTO();

        tipTO.setId(tip.getId());
        tipTO.setTipTeam1(tip.getTipTeam1());
        tipTO.setTipTeam2(tip.getTipTeam2());

        tipTO.setUserId(user.getId());
        tipTO.setFirstName(user.getFirstName());
        tipTO.setLastName(user.getLastName());
        tipTO.setEmail(user.getEmail());

        tipTO.setGameId(game.getId());
        tipTO.setGameTime(game.getGameTime());
        tipTO.setGameLocation(game.getGameLocation());
        tipTO.setTeamId1(game.getTeam1().getId());
        tipTO.setTeamCountry1(game.getTeam1().getCountry());
        tipTO.setPointsTeam1(game.getPointsTeam1());
        tipTO.setTeamId2(game.getTeam2().getId());
        tipTO.setTeamCountry2(game.getTeam2().getCountry());
        tipTO.setPointsTeam2(game.getPointsTeam2());

        return tipTO;
    }

    private Tip convert(TipTO tipTO) {

        Tip tip = new Tip();

        tip.setId(tipTO.getId());
        tip.setTipTeam1(tipTO.getTipTeam1());
        tip.setTipTeam2(tipTO.getTipTeam2());

        Game game = new Game();

        Team team1 = new Team();
        team1.setId(tipTO.getTeamId1());
        team1.setCountry(tipTO.getTeamCountry1());
        team1.setPoints(tipTO.getPointsTeam1());
        game.setTeam1(team1);

        Team team2 = new Team();
        team2.setId(tipTO.getTeamId2());
        team2.setCountry(tipTO.getTeamCountry2());
        team2.setPoints(tipTO.getPointsTeam2());
        game.setTeam2(team2);

        game.setId(tipTO.getGameId());
        game.setGameTime(tipTO.getGameTime());
        game.setGameLocation(tipTO.getGameLocation());
        game.setPointsTeam1(tipTO.getPointsTeam1());
        game.setPointsTeam2(tipTO.getPointsTeam2());
        tip.setGame(game);

        User user = new User();
        user.setId(tipTO.getId());
        user.setFirstName(tipTO.getFirstName());
        user.setLastName(tipTO.getLastName());
        user.setEmail(tipTO.getEmail());
        tip.setUser(user);

        return tip;
    }
}
