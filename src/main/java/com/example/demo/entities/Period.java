package com.example.demo.entities;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "seasons")
public class Period {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "name")
    private String name;

    @Column(name = "league_id", nullable = false)
    private Long leagueId;
}