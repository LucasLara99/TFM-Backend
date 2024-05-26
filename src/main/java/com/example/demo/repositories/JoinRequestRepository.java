package com.example.demo.repositories;

import com.example.demo.entities.JoinRequest;
import com.example.demo.entities.JoinRequest.Status;
import com.example.demo.entities.Team;
import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    List<JoinRequest> findByTeamIdAndStatus(Long teamId, Status status);

    Optional<JoinRequest> findByUserAndTeam(User user, Team team);
}