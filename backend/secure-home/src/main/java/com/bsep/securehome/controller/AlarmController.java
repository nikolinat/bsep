package com.bsep.securehome.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsep.securehome.annotation.LogAfterReturning;
import com.bsep.securehome.annotation.LogAfterThrowing;
import com.bsep.securehome.dto.AlarmDto;
import com.bsep.securehome.service.implementation.AlarmService;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

@CrossOrigin()
@RestController
@RequestMapping(value = "/api/v1/alarm", produces = MediaType.APPLICATION_JSON_VALUE)
public class AlarmController {

    private AlarmService alarmService;

    @Autowired
    public AlarmController(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @PreAuthorize("hasAuthority('CREATE_ALARM')")
    @LogAfterThrowing(message = "ERROR create alarm")
    @LogAfterReturning(message = "SUCCESS create alarm")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody AlarmDto alarm) throws IOException {
        return new ResponseEntity<>(alarmService.createAlarm(alarm), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ_ALARMS')")
    @LogAfterThrowing(message = "ERROR read alarms by real estate id")
    @LogAfterReturning(message = "SUCCESS read alarms by real estate id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getAlarms(@PathVariable Long id) throws IOException {
        ArrayList<AlarmDto> alarms = alarmService.findAlarmsForRealEstate(id);
        return new ResponseEntity<>(alarms, HttpStatus.OK);
    }
}
