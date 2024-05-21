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
    private UserRepository userRepository;
    @Autowired
    private TeamRepository teamRepository;

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
            foundLeague.setGroups(groupRepository.findByLeagueId(foundLeague.getId()));
            return foundLeague;
        } else {
            return null;
        }
    }

    public User joinTeam(Long userId, Long teamId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Team not found"));

        if (team.getCurrent_users() < team.getMax_places()) {
            team.setCurrent_users(team.getCurrent_users() + 1);
            teamRepository.save(team);
            user.getTeams().add(team);
            userRepository.save(user);
        } else {
            throw new RuntimeException("No places available in the team");
        }

        return user;
    }

    public Group getGroupById(Long groupId) {
        return groupRepository.findById(groupId).orElse(null);
    }
}
