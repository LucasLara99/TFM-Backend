package com.example.demo.services;

import com.example.demo.entities.League;
import com.example.demo.repositories.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeagueService {
    @Autowired
    private LeagueRepository leagueRepository;

    public List<League> getAllLeagues() {
        return leagueRepository.findAll();
    }
}
