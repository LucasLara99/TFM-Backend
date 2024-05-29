package com.example.demo.repositories;

import com.example.demo.entities.Match;
import com.example.demo.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByHomeTeam(Team team);
    List<Match> findByAwayTeam(Team team);
}
