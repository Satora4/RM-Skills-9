package ch.ergon.lernende.wmtippspiel.backend.game;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class GamesWithDateTO {
    private LocalDate date;
    private List<Game> games;

    public GamesWithDateTO() {
        games = new ArrayList<>();
    }

    public LocalDate getGroupDate() {
        return date;
    }

    public void setGroupDate(LocalDate groupDate) {
        this.date = groupDate;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}


