package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.List;

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

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "league_id", nullable = false)
    private League league;

    @JsonManagedReference
    @OneToMany(mappedBy = "group")
    private List<Team> teams;
}
