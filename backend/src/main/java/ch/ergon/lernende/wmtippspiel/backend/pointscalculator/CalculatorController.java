package ch.ergon.lernende.wmtippspiel.backend.pointscalculator;

import ch.ergon.lernende.wmtippspiel.backend.user.CurrentUser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {

    private final CalculatorService calculatorService;
    private final CurrentUser currentUser;

    public CalculatorController(CalculatorService calculatorService, CurrentUser currentUser) {
        this.calculatorService = calculatorService;
        this.currentUser = currentUser;
    }

    @PostMapping("/calculate")
    public HttpStatus calculate() {
        if (currentUser.getUser().isAdministrator()) {
            calculatorService.calculateScore();
            calculatorService.calculateGames();
            return HttpStatus.OK;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }
}
