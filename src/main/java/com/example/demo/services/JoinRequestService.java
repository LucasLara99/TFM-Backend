package com.example.demo.services;

import com.example.demo.entities.JoinRequest;
import com.example.demo.entities.Team;
import com.example.demo.entities.User;
import com.example.demo.repositories.JoinRequestRepository;
import com.example.demo.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JoinRequestService {
    @Autowired
    private JoinRequestRepository joinRequestRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserService userService;

    public JoinRequest createJoinRequest(User user, Team team) {
        // Verificar si el usuario es el capitán del equipo
        if (user.equals(team.getCaptain())) {
            // Si es así, lanzar una excepción
            throw new IllegalArgumentException("The team captain cannot request to join his/her own team");
        }

        // Verificar si ya existe una solicitud de unión para el mismo usuario y equipo
        Optional<JoinRequest> existingJoinRequest = joinRequestRepository.findByUserAndTeam(user, team);
        if (existingJoinRequest.isPresent()) {
            // Si existe, lanzar una excepción
            throw new IllegalArgumentException("Join request already exists for the user and team");
        }

        // Si no existe, crear una nueva solicitud de unión
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setUser(user);
        joinRequest.setTeam(team);
        joinRequest.setStatus(JoinRequest.Status.PENDING);
        return joinRequestRepository.save(joinRequest);
    }

    public List<JoinRequest> getPendingJoinRequestsByTeamId(Long teamId) {
        return joinRequestRepository.findByTeamIdAndStatus(teamId, JoinRequest.Status.PENDING);
    }

    public void acceptJoinRequest(Long requestId, Long userId) {
        JoinRequest joinRequest = joinRequestRepository.findById(requestId).orElse(null);
        if (joinRequest != null) {
            User captain = userService.getUserById(userId);
            Team team = joinRequest.getTeam();

            // Verificar si el usuario es el capitán del equipo
            if (!captain.equals(team.getCaptain())) {
                throw new IllegalArgumentException("Only the team captain can handle join requests");
            }

            // Obtener el usuario que hizo la solicitud de unión
            User user = joinRequest.getUser();

            joinRequest.setStatus(JoinRequest.Status.ACCEPTED);
            joinRequestRepository.save(joinRequest);

            // Añadir al usuario que hizo la solicitud de unión al equipo
            team.getUsers().add(user);
            team.setCurrent_users(team.getCurrent_users() + 1); // Incrementar el número de usuarios
            teamRepository.save(team);
        }
    }

    public void rejectJoinRequest(Long requestId, Long userId) {
        JoinRequest joinRequest = joinRequestRepository.findById(requestId).orElse(null);
        if (joinRequest != null) {
            User user = userService.getUserById(userId);
            Team team = joinRequest.getTeam();

            // Verificar si el usuario es el capitán del equipo
            if (!user.equals(team.getCaptain())) {
                throw new IllegalArgumentException("Only the team captain can handle join requests");
            }

            joinRequest.setStatus(JoinRequest.Status.REJECTED);
            joinRequestRepository.save(joinRequest);
        }
    }

    public JoinRequest getJoinRequestById(Long requestId) {
        return joinRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Join request not found with id: " + requestId));
    }
}