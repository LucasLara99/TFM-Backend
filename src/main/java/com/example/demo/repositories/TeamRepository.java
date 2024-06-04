package com.example.demo.repositories;

import com.example.demo.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByGroup_Id(Long groupId);
}