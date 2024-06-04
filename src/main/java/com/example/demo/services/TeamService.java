package com.example.demo.services;

import com.example.demo.entities.Team;
import com.example.demo.entities.User;
import com.example.demo.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public List<User> getTeamMembers(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Team not found"));
        return team.getUsers();
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public List<Team> getTeamsByGroupId(Long groupId) {
        return teamRepository.findByGroup_Id(groupId);
    }
}