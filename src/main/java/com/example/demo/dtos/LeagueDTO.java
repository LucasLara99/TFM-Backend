package com.example.demo.dtos;


import lombok.Data;
import java.util.List;

@Data
public class LeagueDTO {
    private Long id;
    private String name;
    private String status;
    private CampusDTO campus;
    private List<GroupDTO> groups;
}

