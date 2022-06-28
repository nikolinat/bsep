package com.bsep.securehome.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.bsep.securehome.annotation.LogAfterReturning;
import com.bsep.securehome.annotation.LogAfterThrowing;
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
import com.bsep.securehome.dto.SearchDeviceDto;
import com.bsep.securehome.model.DevicesLog;
import com.bsep.securehome.service.implementation.DeviceMessageService;
import com.bsep.securehome.service.implementation.DeviceService;

@CrossOrigin()
@RestController
@RequestMapping(value = "/api/v1/device", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeviceController {
    private DeviceService deviceService;
    private DeviceMessageService deviceMessageService;

    @Autowired
    public DeviceController(DeviceService deviceService, DeviceMessageService deviceMessageService) {
        this.deviceService = deviceService;
        this.deviceMessageService = deviceMessageService;
    }

    @PreAuthorize("hasAuthority('CREATE_DEVICE')")
    @LogAfterThrowing(message = "ERROR create device")
    @LogAfterReturning(message = "SUCCESS create device")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody DeviceDto device) throws IOException {
        return new ResponseEntity<>(deviceService.createDevice(device), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('READ_DEVICES', 'READ_REAL_ESTATES_BY_TENANT_OWNER')")
    @LogAfterThrowing(message = "ERROR read devices by real estate id")
    @LogAfterReturning(message = "SUCCESS read devices by real estate id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getDevices(@PathVariable Long id) throws IOException {
        ArrayList<DeviceDto> devices = deviceService.findDevicesForRealEstate(id);
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

    @PostMapping("/state")
    @LogAfterThrowing(message = "ERROR add message to file")
    @LogAfterReturning(message = "SUCCESS add message to file")
    public ResponseEntity<?> getMessageFromDevice(@RequestBody MessageDto message) throws IOException {
        String decryptedMessage = deviceService.decrypt(message.getMessage());
        message.setMessage(decryptedMessage);
        deviceMessageService.addMessageToFile(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/devices")
    public ResponseEntity<List<DevicesLog>> searchDevices(SearchDeviceDto searchDeviceDto) throws IOException {
        List<DevicesLog> devices =  deviceService.fetchReportForDevices(searchDeviceDto);

        return new ResponseEntity<>(devices, HttpStatus.OK);
    }

}
