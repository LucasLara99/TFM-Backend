package com.example.demo.requests;

import com.example.demo.entities.Team;
import lombok.Data;

@Data
public class TeamCreationRequest {
    private Team team;
    private Long userId;
}
