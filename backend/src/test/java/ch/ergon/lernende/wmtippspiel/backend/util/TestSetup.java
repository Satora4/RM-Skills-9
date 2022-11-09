package ch.ergon.lernende.wmtippspiel.backend.util;

import ch.ergon.lernende.wmtippspiel.backend.tip.TipTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public abstract class TestSetup {
    protected HttpHeaders httpHeaders = new HttpHeaders();
    protected HttpEntity<TipTO> entity;

    protected void setUp() {
        httpHeaders.set("X-Forwarded-User", "jvontobe");
        httpHeaders.set("X-Forwarded-Mail", "joel.vontobel@ergon.ch");

        entity = new HttpEntity<>(null, httpHeaders);
    }

    protected static String createBaseUrl(int port) {
        return "http://localhost:" + port + "/";
    }
}
