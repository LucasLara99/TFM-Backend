package com.example.demo.services;

import com.example.demo.dtos.TeamDTO;
import com.example.demo.entities.Team;
import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<TeamDTO> getUserTeams(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<Team> teams = user.getTeams();
        List<TeamDTO> teamDTOs = new ArrayList<>();
        for (Team team : teams) {
            TeamDTO teamDTO = new TeamDTO();
            teamDTO.setId(team.getId());
            teamDTO.setName(team.getName());
            teamDTO.setSchedule(team.getSchedule());
            teamDTO.setLocation(team.getLocation());
            teamDTO.setMaxPlaces(team.getMaxPlaces());
            teamDTO.setCurrentUsers(team.getCurrentUsers());
            teamDTO.setGroupId(team.getGroup().getId());
            teamDTOs.add(teamDTO);
        }
        return teamDTOs;
    }
}
