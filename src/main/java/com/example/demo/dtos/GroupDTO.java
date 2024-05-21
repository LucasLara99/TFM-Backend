package com.example.demo.dtos;

import lombok.Data;

import java.util.List;

@Data
public class GroupDTO {
    private Long id;
    private String name;
    private List<TeamDTO> teams;
}
