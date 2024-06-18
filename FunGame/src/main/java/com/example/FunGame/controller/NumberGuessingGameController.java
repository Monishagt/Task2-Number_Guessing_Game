package com.example.FunGame.controller;





import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Controller
public class NumberGuessingGameController {
    private int numberToGuess;
    private int attempts;
    private final int MAX_ATTEMPTS = 10;

    public NumberGuessingGameController() {
        resetGame();
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Guess a number between 1 and 100:");
        model.addAttribute("attempts", attempts);
        model.addAttribute("playAgain", false);
        return "index";
    }

    @PostMapping("/guess")
    public String guess(@RequestParam int guess, Model model) {
        attempts++;
        String message;
        boolean playAgain = false;

        if (guess < numberToGuess) {
            message = "Too low! Try again.";
        } else if (guess > numberToGuess) {
            message = "Too high! Try again.";
        } else {
            message = "Congratulations! You guessed the number.";
            playAgain = true;
        }

        if (attempts >= MAX_ATTEMPTS && guess != numberToGuess) {
            message = "You've reached the maximum attempts. The number was: " + numberToGuess;
            playAgain = true;
        }

        model.addAttribute("message", message);
        model.addAttribute("attempts", attempts);
        model.addAttribute("playAgain", playAgain);

        return "index";
    }

    @PostMapping("/reset")
    public String reset(Model model) {
        resetGame();
        model.addAttribute("message", "Guess a number between 1 and 100:");
        model.addAttribute("attempts", attempts);
        model.addAttribute("playAgain", false);
        return "index";
    }

    private void resetGame() {
        Random random = new Random();
        numberToGuess = random.nextInt(100) + 1;
        attempts = 0;
    }
}
