package com.example.demo.repositories;

import com.example.demo.entities.Match;
import com.example.demo.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByHomeTeam(Team team);
    List<Match> findByAwayTeam(Team team);

    @Query("SELECT m FROM Match m WHERE m.homeTeam.id = :teamId OR m.awayTeam.id = :teamId")
    List<Match> findMatchesByTeam(@Param("teamId") Long teamId);

    @Query("SELECT m FROM Match m WHERE m.homeTeam.group.id = :groupId OR m.awayTeam.group.id = :groupId")
    List<Match> findMatchesByGroupId(@Param("groupId") Long groupId);
}
