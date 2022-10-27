package ch.ergon.lernende.wmtippspiel.backend.pointscalculator;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PostMapping("/calculate")
    public void calculate() {
        calculatorService.calculateScore();
        calculatorService.calculateGames();
    }
}
