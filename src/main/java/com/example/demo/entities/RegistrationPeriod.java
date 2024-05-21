package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;


@Entity
@Getter
@Setter
@Table(name = "registration_periods")
public class RegistrationPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date", nullable = false)
    private String startDate;

    @Column(name = "end_date", nullable = false)
    private String endDate;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "period", nullable = false)
    private String period;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;
}