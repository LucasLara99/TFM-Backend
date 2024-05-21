package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;


@Entity
@Getter
@Setter
@Table(name = "seasons")
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date", nullable = false)
    private String startDate;

    @Column(name = "end_date", nullable = false)
    private String endDate;

    @Column(name = "name")
    private String name;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;
}