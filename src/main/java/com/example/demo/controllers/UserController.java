package com.example.demo.controllers;

import com.example.demo.dtos.TeamDTO;
import com.example.demo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}/teams")
    public ResponseEntity<?> getUserTeams(@PathVariable Long userId) {
        try {
            List<TeamDTO> teams = userService.getUserTeams(userId);
            return new ResponseEntity<>(teams, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving user's teams: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}