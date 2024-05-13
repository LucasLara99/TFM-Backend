package com.example.demo.controllers;

import com.example.demo.entities.League;
import com.example.demo.services.LeagueService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/leagues")
public class LeagueController {

    private final LeagueService leagueService;

    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping("/all")
    public List<League> getAllLeagues() {
        return leagueService.getAllLeagues();
    }
}