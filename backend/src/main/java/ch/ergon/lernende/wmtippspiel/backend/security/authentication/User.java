package ch.ergon.lernende.wmtippspiel.backend.security.authentication;

public final class User {

    private final String user;
    private final String mail;

    public User(String user, String mail) {
        this.user = user;
        this.mail = mail;
    }

    public String getUser() {
        return user;
    }

    public String getMail() {
        return mail;
    }

    @Override
    public String toString() {
        return "User{" +
                "user='" + user + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
