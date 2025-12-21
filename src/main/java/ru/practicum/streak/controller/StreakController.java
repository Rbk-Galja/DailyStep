package ru.practicum.streak.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.streak.service.StreakService;

@RestController
@RequestMapping("/habits/recommendation")
@RequiredArgsConstructor
public class StreakController {
    private final StreakService streakService;

    @GetMapping
    String getRecommendation() {
        return streakService.getRecommendation();
    }

    @GetMapping("/category")
    String getRecommendationByCategory() {
        return streakService.getRecommendationByActivityCategory();
    }
}
