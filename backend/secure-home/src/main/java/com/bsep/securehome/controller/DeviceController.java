package com.bsep.securehome.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsep.securehome.dto.DeviceDto;
import com.bsep.securehome.dto.MessageDto;
import com.bsep.securehome.service.implementation.DeviceService;

@CrossOrigin()
@RestController
@RequestMapping(value = "/api/v1/device", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeviceController {
    private DeviceService deviceService;
   
    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PreAuthorize("hasAuthority('READ_MY_HOMES')")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody DeviceDto device) throws IOException {
        return new ResponseEntity<>( deviceService.createDevice(device), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ_MY_HOMES')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findDevices(@PathVariable Long id) throws IOException {
        ArrayList<DeviceDto> devices = deviceService.findDevicesForRealEstate(id);
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @PostMapping("/state")
    public ResponseEntity<?> normalState(@RequestBody MessageDto message) throws IOException {
        System.out.println("TUUUUUUUUUUUUUU");
        System.out.println(message.getDateTime());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
