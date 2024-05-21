package com.example.demo.controllers;

import com.example.demo.entities.Group;
import com.example.demo.entities.League;
import com.example.demo.entities.Team;
import com.example.demo.entities.User;
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
            List<League> leagues = leagueService.getAllLeaguesWithDetails();
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

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{leagueId}/groups/{groupId}/teams")
    public ResponseEntity<?> createTeamInGroup(@PathVariable Long leagueId, @PathVariable Long groupId, @RequestBody Team team) {
        try {
            Group group = leagueService.getGroupById(groupId);
            if (group != null && group.getLeague().getId().equals(leagueId)) {
                team.setGroup(group);
                Team createdTeam = leagueService.createTeam(team);
                return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Group not found in the league", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating team: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{teamId}/join")
    public ResponseEntity<?> joinTeam(@PathVariable Long teamId, @RequestBody Long userId) {
        try {
            User user = leagueService.joinTeam(userId, teamId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error joining team: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
