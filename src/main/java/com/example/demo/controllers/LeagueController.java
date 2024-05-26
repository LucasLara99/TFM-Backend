package com.example.demo.controllers;

import com.example.demo.entities.*;
import com.example.demo.requests.TeamCreationRequest;
import com.example.demo.services.JoinRequestService;
import com.example.demo.services.LeagueService;
import com.example.demo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leagues")
public class LeagueController {

    private final LeagueService leagueService;
    private final UserService userService;
    private final JoinRequestService joinRequestService;

    public LeagueController(LeagueService leagueService, UserService userService,
                            JoinRequestService joinRequestService) {
        this.leagueService = leagueService;
        this.userService = userService;
        this.joinRequestService = joinRequestService;
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
    public ResponseEntity<?> createTeamInGroup(@PathVariable Long leagueId, @PathVariable Long groupId, @RequestBody TeamCreationRequest teamCreationRequest) {
        try {
            Team team = teamCreationRequest.getTeam();
            Long userId = teamCreationRequest.getUserId();

            if (team == null) {
                return new ResponseEntity<>("Team data is missing", HttpStatus.BAD_REQUEST);
            }

            User user = userService.getUserById(userId);
            if (user == null) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            Group group = leagueService.getGroupById(groupId);
            if (group != null && group.getLeague().getId().equals(leagueId)) {
                for (Team existingTeam : group.getTeams()) {
                    if (existingTeam.getName().equals(team.getName())) {
                        return new ResponseEntity<>("Team name already exists in the group", HttpStatus.CONFLICT);
                    }
                }

                team.setGroup(group);
                team.setCurrent_users(1); // Set the current number of users to 1
                team.setCaptain(user); // Set the user as the captain of the team
                Team createdTeam = leagueService.createTeam(team);

                // Add the team to the user
                List<Team> userTeams = user.getTeams();
                userTeams.add(createdTeam);
                user.setTeams(userTeams);
                userService.saveUser(user); // Save the user with the new team

                return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Group not found in the league", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating team: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Endpoint para enviar solicitudes para unirse a un equipo
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{teamId}/requests")
    public ResponseEntity<?> requestToJoinTeam(@PathVariable Long teamId, @RequestBody Long userId) {
        try {
            User user = userService.getUserById(userId);
            if (user == null) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            Team team = leagueService.getTeamById(teamId);
            if (team == null) {
                return new ResponseEntity<>("Team not found", HttpStatus.NOT_FOUND);
            }

            JoinRequest joinRequest = joinRequestService.createJoinRequest(user, team);
            return new ResponseEntity<>(joinRequest, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error requesting to join team: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para que el capitán del equipo pueda ver todas las solicitudes pendientes
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{teamId}/requests")
    public ResponseEntity<?> getJoinRequests(@PathVariable Long teamId) {
        try {
            List<JoinRequest> joinRequests = joinRequestService.getPendingJoinRequestsByTeamId(teamId);
            return new ResponseEntity<>(joinRequests, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error getting join requests: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para que el capitán del equipo pueda aceptar o rechazar una solicitud
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{teamId}/requests/{requestId}/{action}")
    public ResponseEntity<?> handleJoinRequest(@PathVariable Long teamId, @PathVariable Long requestId, @PathVariable String action, @RequestBody Long userId) {
        try {
            JoinRequest joinRequest = joinRequestService.getJoinRequestById(requestId); // Obtener la solicitud de unión

            // Verificar si la solicitud de unión pertenece al equipo especificado
            if (!joinRequest.getTeam().getId().equals(teamId)) {
                return new ResponseEntity<>("Join request does not belong to the specified team", HttpStatus.BAD_REQUEST);
            }

            if (action.equalsIgnoreCase("accept")) {
                joinRequestService.acceptJoinRequest(requestId, userId); // Pasar el userId al método acceptJoinRequest
            } else if (action.equalsIgnoreCase("reject")) {
                joinRequestService.rejectJoinRequest(requestId, userId); // Pasar el userId al método rejectJoinRequest
            } else {
                return new ResponseEntity<>("Invalid action", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error handling join request: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}