package com.example.demo.entities;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "leagues")
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "season")
    private String season;

    @Column(name = "campus")
    private String campus;

    @Column(name = "status")
    private String status;

    @Setter
    @Transient
    private List<Period> periods;

    @Setter
    @Transient
    private List<Registration> registrations;

    @Setter
    @Transient
    private List<Group> groups;
}