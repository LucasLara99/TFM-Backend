package com.example.demo.services;

import com.example.demo.entities.Campus;
import com.example.demo.entities.Group;
import com.example.demo.entities.Match;
import com.example.demo.entities.Team;
import com.example.demo.repositories.CampusRepository;
import com.example.demo.repositories.MatchRepository;
import com.example.demo.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private CampusRepository campusRepository;

    public void generateMatches(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
        List<Team> teams = group.getTeams();
        Campus campus = campusRepository.findById(1L).orElseThrow(() -> new RuntimeException("Campus not found")); // Replace with actual campus id

        for (int i = 0; i < teams.size(); i++) {
            for (int j = 0; j < teams.size(); j++) {
                if (i != j) {
                    Match match = new Match();
                    match.setHomeTeam(teams.get(i));
                    match.setAwayTeam(teams.get(j));
                    match.setDate("2024-04-31");
                    match.setTime("18:00:00");
                    match.setCampus(campus);
                    matchRepository.save(match);
                }
            }
        }
    }

    public List<Match> getMatchesByGroup(Long groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
        List<Team> teams = group.getTeams();
        List<Match> matches = new ArrayList<>();
        for (Team team : teams) {
            matches.addAll(matchRepository.findByHomeTeam(team));
            matches.addAll(matchRepository.findByAwayTeam(team));
        }
        return matches;
    }
}