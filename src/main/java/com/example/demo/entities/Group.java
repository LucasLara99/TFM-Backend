package com.example.demo.entities;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "`groups`")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "schedule", nullable = false)
    private String schedule;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "max_places", nullable = false)
    private int maxPlaces;

    @Column(name = "current_users", nullable = false)
    private int currentUsers;

    @Transient
    private String status;

    @Column(name = "league_id", nullable = false)
    private Long leagueId;

    public String getStatus() {
        return currentUsers == maxPlaces ? "Completo" : "Libre";
    }
}