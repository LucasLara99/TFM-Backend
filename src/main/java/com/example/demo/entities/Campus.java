package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "campuses")
@JsonIgnoreProperties("leagues")
public class Campus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "campus")
    private List<League> leagues;
}