package com.example.demo.repositories;


import com.example.demo.entities.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {
    List<Season> findByLeagueId(Long leagueId);
}