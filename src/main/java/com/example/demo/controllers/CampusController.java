package com.example.demo.controllers;

import com.example.demo.entities.Campus;
import com.example.demo.services.CampusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/campuses")
public class CampusController {

    private final CampusService campusService;

    @Autowired
    public CampusController(CampusService campusService) {
        this.campusService = campusService;
    }

    @GetMapping
    public ResponseEntity<List<Campus>> getAllCampuses() {
        List<Campus> campuses = campusService.getAllCampuses();
        return new ResponseEntity<>(campuses, HttpStatus.OK);
    }
}