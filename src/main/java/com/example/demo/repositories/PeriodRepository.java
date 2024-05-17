package com.example.demo.repositories;

import com.example.demo.entities.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodRepository extends JpaRepository<Period, Long> {
    List<Period> findByLeagueId(Long leagueId);
}