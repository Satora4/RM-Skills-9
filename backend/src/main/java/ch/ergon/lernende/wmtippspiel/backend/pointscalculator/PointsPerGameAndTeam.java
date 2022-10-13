package ch.ergon.lernende.wmtippspiel.backend.pointscalculator;

import java.util.Objects;

/**
 * Holds the points for both teams of a game.
 */
public record PointsPerGameAndTeam (int pointsTeam1, int pointsTeam2) {}