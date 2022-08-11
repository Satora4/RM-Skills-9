package ch.ergon.lernende.wmtippspiel.backend.util;

public class CreateBaseUrl {
    public static String createBaseUrl(int port) {
        return "http://localhost:" + port + "/";
    }
}
