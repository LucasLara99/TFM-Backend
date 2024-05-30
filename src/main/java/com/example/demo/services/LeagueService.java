package com.example.demo.services;

import com.example.demo.entities.Group;
import com.example.demo.entities.League;
import com.example.demo.entities.Team;
import com.example.demo.entities.User;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LeagueService {
    @Autowired
    private LeagueRepository leagueRepository;
    @Autowired
    private SeasonRepository seasonRepository;
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<League> getAllLeaguesWithDetails() {
        return leagueRepository.findAll();
    }

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    public League getLeague(int id) {
        Optional<League> league = leagueRepository.findById((long) id);
        if (league.isPresent()) {
            League foundLeague = league.get();
            foundLeague.setSeasons(seasonRepository.findByLeagueId(foundLeague.getId()));
            foundLeague.setRegistrationPeriods(registrationRepository.findByLeagueId(foundLeague.getId()));
            foundLeague.setGroups(groupRepository.findByLeagueIdWithTeams(foundLeague.getId()));
            return foundLeague;
        } else {
            return null;
        }
    }

    public Group getGroupById(Long groupId) {
        return groupRepository.findById(groupId).orElse(null);
    }

    public Team getTeamById(Long teamId) {
        return teamRepository.findById(teamId).orElse(null);
    }

    public boolean userHasTeamInGroup(Long userId, Long groupId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<Team> teams = user.getTeams();
        for (Team team : teams) {
            if (team.getGroup().getId().equals(groupId)) {
                return true;
            }
        }
        return false;
    }
}
