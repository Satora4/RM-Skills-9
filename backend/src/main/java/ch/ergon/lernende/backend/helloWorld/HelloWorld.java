package ch.ergon.lernende.backend.helloWorld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("helloWorld")
public class HelloWorld {
    @GetMapping
    public String helloWorld() {
        return "Hello World";
    }
}
