package com.example.demo.requests;

import com.example.demo.entities.Campus;
import lombok.Data;

@Data
public class MatchUpdateRequest {
    private String date;
    private String time;
    private String location;
    private String homeTeamResult;
    private String awayTeamResult;
    private Campus campus;
}