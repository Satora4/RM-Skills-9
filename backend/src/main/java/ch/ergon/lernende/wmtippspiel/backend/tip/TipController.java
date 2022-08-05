package ch.ergon.lernende.wmtippspiel.backend.tip;

import ch.ergon.lernende.wmtippspiel.backend.game.Game;
import ch.ergon.lernende.wmtippspiel.backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class TipController {
    private final TipRepository tipRepository;

    @Autowired
    public TipController(TipRepository tipRepository) {
        this.tipRepository = tipRepository;
    }

    @GetMapping("tip")
    public List<TipTO> getTips(@RequestParam(required = false, name = "userId") Integer userId) {
        if (userId != null) {
            return convertAll(tipRepository.getTipsByUserId(userId));
        } else {
            return convertAll(tipRepository.getAllTip());
        }
    }

    private List<TipTO> convertAll(Collection<Tip> tips) {
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
}
