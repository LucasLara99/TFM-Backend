package com.example.demo.services;

import com.example.demo.entities.League;
import com.example.demo.repositories.LeagueRepository;
import com.example.demo.repositories.PeriodRepository;
import com.example.demo.repositories.RegistrationRepository;
import com.example.demo.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeagueService {
    @Autowired
    private LeagueRepository leagueRepository;
    @Autowired
    private PeriodRepository periodRepository;
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private GroupRepository groupRepository;

    public List<League> getAllLeagues() {
        return leagueRepository.findAll();
    }

    public League getLeague(int id) {
        Optional<League> league = leagueRepository.findById((long) id);
        if (league.isPresent()) {
            League foundLeague = league.get();
            foundLeague.setPeriods(periodRepository.findByLeagueId(foundLeague.getId()));
            foundLeague.setRegistrations(registrationRepository.findByLeagueId(foundLeague.getId()));
            foundLeague.setGroups(groupRepository.findByLeagueId(foundLeague.getId()));
            return foundLeague;
        } else {
            return null;
        }
    }
}