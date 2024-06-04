package com.example.demo.controllers;

import com.example.demo.entities.Match;
import com.example.demo.requests.MatchUpdateRequest;
import com.example.demo.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping("/generate/{groupId}")
    public ResponseEntity<?> generateMatches(@PathVariable Long groupId) {
        try {
            matchService.generateMatches(groupId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error generating matches: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<?> getMatchesByGroup(@PathVariable Long groupId) {
        try {
            List<Match> matches = matchService.getMatchesByGroup(groupId);
            return new ResponseEntity<>(matches, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving matches: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<?> getMatchesByTeam(@PathVariable Long teamId) {
        try {
            List<Match> matches = matchService.getMatchesByTeam(teamId);
            return new ResponseEntity<>(matches, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving matches: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{matchId}")
    public ResponseEntity<?> updateMatch(@PathVariable Long matchId, @RequestBody MatchUpdateRequest matchUpdateRequest) {
        try {
            Match match = matchService.updateMatch(matchId, matchUpdateRequest);
            return new ResponseEntity<>(match, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating match: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}