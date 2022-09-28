package ch.ergon.lernende.wmtippspiel.backend.security.authentication;

public record IamUser(String user, String mail) {

    public static final IamUser UNKNOWN_USER = new IamUser("unknown", "unknown");

    @Override
    public String toString() {
        return "User{user='" + user + '\'' + ", mail='" + mail + '\'' + '}';
    }
}
