package ch.ergon.lernende.wmtippspiel.backend.tip;

import ch.ergon.lernende.wmtippspiel.backend.game.Game;
import ch.ergon.lernende.wmtippspiel.backend.game.GameRepository;
import ch.ergon.lernende.wmtippspiel.backend.team.Team;
import ch.ergon.lernende.wmtippspiel.backend.user.CurrentUser;
import ch.ergon.lernende.wmtippspiel.backend.user.User;
import ch.ergon.lernende.wmtippspiel.backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("tip")
public class TipController {
    private final TipRepository tipRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final CurrentUser currentUser;

    @Autowired
    public TipController(TipRepository tipRepository, GameRepository gameRepository, UserRepository userRepository, CurrentUser currentUser) {
        this.tipRepository = tipRepository;
        this.userRepository = userRepository;
        this.currentUser = currentUser;
        this.gameRepository = gameRepository;
    }

    @GetMapping
    public List<TipTO> getTips() {
        if (currentUser != null) {
            return convert(tipRepository.getTipsByUserMail(currentUser.getUser().getEmail()));
        } else {
            throw new RuntimeException("no tips available");
        }
    }

    @PatchMapping
    public HttpStatus updateTip(@RequestBody TipTO tipTO) {
        if (isValidTip(tipTO)) {
            tipRepository.putTip(convert(tipTO));
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @PostMapping
    public HttpStatus addTip(@RequestBody TipTO tipTO) {
        tipTO.setUserId(userRepository.getForMail(currentUser.getUser().getEmail()).getUserId());
        if (isValidTip(tipTO)) {
            tipRepository.addTip(convert(tipTO));
            return HttpStatus.CREATED;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @DeleteMapping
    public HttpStatus deleteTip(@RequestBody int tipId) {
        tipRepository.deleteTip(tipId);
        return HttpStatus.OK;
    }

    private boolean isValidTip(TipTO tipTO) {
        Game game = gameRepository.getGame(tipTO.getGameId());
        return game.getGoalsTeam1() == null && game.getGoalsTeam2() == null && game.getGameTime().isAfter(LocalDateTime.now());
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

        tipTO.setUserId(user.getUserId());
        tipTO.setFirstName(user.getFirstName());
        tipTO.setLastName(user.getLastName());
        tipTO.setEmail(user.getEmail());
        tipTO.setUserPoints(user.getPoints());

        tipTO.setGameId(game.getId());
        tipTO.setGameTime(game.getGameTime());
        tipTO.setGameLocation(game.getGameLocation());
        tipTO.setTeamId1(game.getTeam1().getId());
        tipTO.setTeamCountry1(game.getTeam1().getCountry());
        tipTO.setGoalsTeam1(game.getGoalsTeam1());
        tipTO.setTeamId2(game.getTeam2().getId());
        tipTO.setTeamCountry2(game.getTeam2().getCountry());
        tipTO.setGoalsTeam2(game.getGoalsTeam2());
        tipTO.setPhase(game.getPhase());

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
        if (tipTO.getGoalsTeam1() != null) {
            team1.setPoints(tipTO.getGoalsTeam1());
        }
        game.setTeam1(team1);

        Team team2 = new Team();
        team2.setId(tipTO.getTeamId2());
        team2.setCountry(tipTO.getTeamCountry2());
        if (tipTO.getGoalsTeam2() != null) {
            team2.setPoints(tipTO.getGoalsTeam2());
        }
        game.setTeam2(team2);

        game.setId(tipTO.getGameId());
        game.setGameTime(tipTO.getGameTime());
        game.setGameLocation(tipTO.getGameLocation());
        if (tipTO.getGoalsTeam1() != null && tipTO.getGoalsTeam2() != null) {
            game.setGoalsTeam1(tipTO.getGoalsTeam1());
            game.setGoalsTeam2(tipTO.getGoalsTeam2());
        }

        tip.setGame(game);

        User user = new User();
        user.setUserId(tipTO.getUserId());
        user.setFirstName(tipTO.getFirstName());
        user.setLastName(tipTO.getLastName());
        user.setEmail(tipTO.getEmail());
        tip.setUser(user);
        System.out.println(tip);
        return tip;
    }
}
