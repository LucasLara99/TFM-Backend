package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @ManyToOne
    @JoinColumn(name = "campus_id")
    private Campus campus;

    @Column(name = "status")
    private String status;

    @JsonManagedReference
    @OneToMany(mappedBy = "league")
    private List<Season> seasons;

    @JsonManagedReference
    @OneToMany(mappedBy = "league")
    private List<RegistrationPeriod> registrationPeriods;

    @JsonManagedReference
    @OneToMany(mappedBy = "league")
    private List<Group> groups;
}
