package com.bsep.securehome.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsep.securehome.model.DevicesLog;
import com.bsep.securehome.service.implementation.DevicesLogService;

@CrossOrigin()
@RestController
@RequestMapping(value = "/api/v1/devicesLog")
public class DevicesLogController {

    private DevicesLogService devicesLogService;
   
    @Autowired
    public DevicesLogController(DevicesLogService devicesLogService) {
        this.devicesLogService = devicesLogService;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody DevicesLog device) throws IOException {
        return new ResponseEntity<>( devicesLogService.create(device), HttpStatus.OK);
    }
    
}
