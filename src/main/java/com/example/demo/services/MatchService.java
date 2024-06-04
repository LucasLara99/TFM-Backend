package com.example.demo.services;

import com.example.demo.entities.Campus;
import com.example.demo.entities.Group;
import com.example.demo.entities.Match;
import com.example.demo.entities.Team;
import com.example.demo.repositories.CampusRepository;
import com.example.demo.repositories.MatchRepository;
import com.example.demo.repositories.GroupRepository;
import com.example.demo.requests.MatchUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        Campus campus = campusRepository.findById(1L).orElseThrow(() -> new RuntimeException("Campus not found"));
        List<Match> existingMatches = matchRepository.findMatchesByGroupId(groupId);
        if (!existingMatches.isEmpty()) {
            throw new RuntimeException("Matches have already been generated for this group");
        }

        for (int i = 0; i < teams.size(); i++) {
            for (int j = 0; j < teams.size(); j++) {
                if (i != j) {
                    Match match = new Match();
                    match.setHomeTeam(teams.get(i));
                    match.setAwayTeam(teams.get(j));
                    match.setDate("01/06/2024");
                    match.setTime("18:00");
                    match.setCampus(campus);
                    match.setHomeTeamResult("-");
                    match.setAwayTeamResult("-");
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

    public List<Match> getMatchesByTeam(Long teamId) {
        return matchRepository.findMatchesByTeam(teamId);
    }

    public Match updateMatch(Long matchId, MatchUpdateRequest updatedMatch) {
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new RuntimeException("Match not found"));

        if (updatedMatch.getDate() != null) {
            LocalDate parsedDate = LocalDate.parse(updatedMatch.getDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = parsedDate.format(formatter);
            match.setDate(formattedDate);
        }
        if (updatedMatch.getTime() != null) {
            LocalTime parsedTime;
            if (updatedMatch.getTime().length() == 5) {
                parsedTime = LocalTime.parse(updatedMatch.getTime(), DateTimeFormatter.ofPattern("HH:mm"));
            } else {
                parsedTime = LocalTime.parse(updatedMatch.getTime(), DateTimeFormatter.ofPattern("HH:mm"));
            }
            match.setTime(String.valueOf(parsedTime));
        }
        if (updatedMatch.getCampus() != null) {
            Campus campus = campusRepository.findById(updatedMatch.getCampus().getId()).orElseThrow(() -> new RuntimeException("Campus not found"));
            match.setCampus(campus);
        }
        if (updatedMatch.getHomeTeamResult() != null && !updatedMatch.getHomeTeamResult().equals("-")) {
            int parsedHomeTeamResult = Integer.parseInt(updatedMatch.getHomeTeamResult());
            match.setHomeTeamResult(String.valueOf(parsedHomeTeamResult));
        }
        if (updatedMatch.getAwayTeamResult() != null && !updatedMatch.getAwayTeamResult().equals("-")) {
            int parsedAwayTeamResult = Integer.parseInt(updatedMatch.getAwayTeamResult());
            match.setAwayTeamResult(String.valueOf(parsedAwayTeamResult));
        }

        return matchRepository.save(match);
    }
}