package com.example.demo.dtos;

import lombok.Data;

@Data
public class TeamDTO {
    private Long id;
    private String name;
    private String schedule;
    private String location;
    private int maxPlaces;
    private int currentUsers;
    private Long groupId;
}
