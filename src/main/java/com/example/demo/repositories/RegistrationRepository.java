package com.example.demo.repositories;

import com.example.demo.entities.RegistrationPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<RegistrationPeriod, Long> {
    List<RegistrationPeriod> findByLeagueId(Long leagueId);
}