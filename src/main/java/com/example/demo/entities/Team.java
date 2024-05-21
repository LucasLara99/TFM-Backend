package com.example.demo.entities;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "schedule", nullable = false)
    private String schedule;

    @Column(name = "location")
    private String location;

    @Column(name = "max_places", nullable = false)
    private int maxPlaces;

    @Column(name = "current_users", nullable = false)
    private int currentUsers;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}