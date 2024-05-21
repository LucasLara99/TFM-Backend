package com.example.demo.repositories;

import com.example.demo.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("SELECT g FROM Group g LEFT JOIN FETCH g.teams WHERE g.league.id = :leagueId")
    List<Group> findByLeagueIdWithTeams(@Param("leagueId") Long leagueId);
}