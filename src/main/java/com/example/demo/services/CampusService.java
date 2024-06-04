package com.example.demo.services;

import com.example.demo.entities.Campus;
import com.example.demo.repositories.CampusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampusService {

    private final CampusRepository campusRepository;

    @Autowired
    public CampusService(CampusRepository campusRepository) {
        this.campusRepository = campusRepository;
    }

    public List<Campus> getAllCampuses() {
        return campusRepository.findAll();
    }
}