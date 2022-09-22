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

    @PatchMapping()
    public void updateTip(@RequestBody TipTO tipTO) {
        if (tipTO.getPointsTeam1() == null && tipTO.getPointsTeam2() == null) {
            tipRepository.putTip(convert(tipTO));
        } else {
            throw new IllegalArgumentException("the game has already been played");
        }
    }

    @GetMapping
    public List<TipTO> getTips(@RequestParam(required = false, name = "userId") Integer userId) {
        if (userId != null) {
            return convert(tipRepository.getTipsByUserId(userId));
        } else {
            return convert(tipRepository.getAllTip());
        }
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addTip(@RequestBody TipTO tipTO) {
        if (tipTO.getPointsTeam1() == null && tipTO.getPointsTeam2() == null) {
            tipRepository.addTip(convert(tipTO));
        } else {
            throw new IllegalArgumentException("the game has already been played");
        }
    }

    private List<TipTO> convert(Collection<Tip> tips) {
        return tips.stream().map(this::convert).collect(toList());
    }

    private TipTO convert(Tip tip) {

        User user = tip.getUser();
        Game game = tip.getGame();

        TipTO tipTO = new TipTO();

        tipTO.setId(tip.getId());
        tipTO.setTipTeam1(tip.getTipTeam1());
        tipTO.setTipTeam2(tip.getTipTeam2());
        tipTO.setPoints(tip.getPoints());

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
        tip.setPoints(tipTO.getPoints());

        Game game = new Game();

        Team team1 = new Team();
        team1.setId(tipTO.getTeamId1());
        team1.setCountry(tipTO.getTeamCountry1());
        if (tipTO.getPointsTeam1() != null) {
            team1.setPoints(tipTO.getPointsTeam1());
        }
        game.setTeam1(team1);

        Team team2 = new Team();
        team2.setId(tipTO.getTeamId2());
        team2.setCountry(tipTO.getTeamCountry2());
        if (tipTO.getPointsTeam2() != null) {
            team2.setPoints(tipTO.getPointsTeam2());
        }
        game.setTeam2(team2);

        game.setId(tipTO.getGameId());
        game.setGameTime(tipTO.getGameTime());
        game.setGameLocation(tipTO.getGameLocation());
        if (tipTO.getPointsTeam1() != null && tipTO.getPointsTeam2() != null) {
            game.setPointsTeam1(tipTO.getPointsTeam1());
            game.setPointsTeam2(tipTO.getPointsTeam2());
        }

        tip.setGame(game);

        User user = new User();
        user.setId(tipTO.getUserId());
        user.setFirstName(tipTO.getFirstName());
        user.setLastName(tipTO.getLastName());
        user.setEmail(tipTO.getEmail());
        tip.setUser(user);
        System.out.println(tip);
        return tip;
    }
}
