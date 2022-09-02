package ch.ergon.lernende.wmtippspiel.backend.security.authentication;

public record User(String user, String mail) {

    public static final User UNKNOWN_USER = new User("unknown", "unknown");

    @Override
    public String toString() {
        return "User{user='" + user + '\'' + ", mail='" + mail + '\'' + '}';
    }
}
