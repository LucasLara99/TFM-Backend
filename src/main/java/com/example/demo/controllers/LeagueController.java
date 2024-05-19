package com.example.demo.controllers;

import com.example.demo.entities.Group;
import com.example.demo.entities.League;
import com.example.demo.services.LeagueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leagues")
public class LeagueController {

    private final LeagueService leagueService;

    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllLeagues() {
        try {
            List<League> leagues = leagueService.getAllLeagues();
            return new ResponseEntity<>(leagues, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving leagues: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLeague(@PathVariable int id) {
        try {
            League league = leagueService.getLeague(id);
            if (league != null) {
                return new ResponseEntity<>(league, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("League not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving league: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{leagueId}/groups")
    public ResponseEntity<?> createGroup(@PathVariable Long leagueId, @RequestBody Group group) {
        try {
            group.setLeagueId(leagueId);
            Group createdGroup = leagueService.createGroup(group);
            return new ResponseEntity<>(createdGroup, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating group: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}