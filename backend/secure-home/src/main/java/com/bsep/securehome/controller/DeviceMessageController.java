package com.bsep.securehome.controller;

import com.bsep.securehome.annotation.LogAfterReturning;
import com.bsep.securehome.annotation.LogAfterThrowing;
import com.bsep.securehome.dto.DeviceMessageDto;
import com.bsep.securehome.dto.SearchFilterDeviceMessagesDto;
import com.bsep.securehome.exception.MissingEntityException;
import com.bsep.securehome.mapper.DeviceMessageMapper;
import com.bsep.securehome.model.DeviceMessage;
import com.bsep.securehome.service.implementation.DeviceMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping(value = "/api/v1/device-message", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeviceMessageController {
    private final DeviceMessageService deviceMessageService;
    private final DeviceMessageMapper deviceMessageMapper;

    @Autowired
    public DeviceMessageController(DeviceMessageService deviceMessageService,
                                   DeviceMessageMapper deviceMessageMapper) {
        this.deviceMessageService = deviceMessageService;
        this.deviceMessageMapper = deviceMessageMapper;
    }

    @PreAuthorize("hasAuthority('READ_REAL_ESTATES_BY_TENANT_OWNER')")
    @LogAfterThrowing(message = "ERROR read device messages by real estate id")
    @LogAfterReturning(message = "SUCCESS read device messages by real estate id")
    @GetMapping(value = "/by-real-estate/{realEstateId}")
    public ResponseEntity<?> readByRealEstate(@PathVariable Long realEstateId) throws Exception {
        List<DeviceMessage> deviceMessageList = deviceMessageService.findByRealEstateId(realEstateId);
        List<DeviceMessageDto> deviceMessageDtos = new ArrayList<>();
        deviceMessageList.forEach(deviceMessage -> {
            try {
                deviceMessageDtos.add(deviceMessageMapper.fromDeviceMessageToDeviceMessageDto(deviceMessage, realEstateId));
            } catch (Exception e) {
                throw new MissingEntityException("The real estate with given id does not exist.");
            }
        });
        return new ResponseEntity<>(deviceMessageDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ_REAL_ESTATES_BY_TENANT_OWNER')")
    @LogAfterThrowing(message = "ERROR search and filter device messages")
    @LogAfterReturning(message = "SUCCESS search and filter device messages")
    @GetMapping(value = "/search-filter/{realEstateId}")
    public ResponseEntity<?> searchAndFilter(@PathVariable Long realEstateId, SearchFilterDeviceMessagesDto searchFilterDeviceMessagesDto) throws Exception {
        List<DeviceMessage> deviceMessageList = deviceMessageService.filterAndSearch(realEstateId, searchFilterDeviceMessagesDto);
        List<DeviceMessageDto> deviceMessageDtos = new ArrayList<>();
        deviceMessageList.forEach(deviceMessage -> {
            try {
                deviceMessageDtos.add(deviceMessageMapper.fromDeviceMessageToDeviceMessageDto(deviceMessage, realEstateId));
            } catch (Exception e) {
                throw new MissingEntityException("The real estate with given id does not exist.");
            }
        });
        return new ResponseEntity<>(deviceMessageDtos, HttpStatus.OK);
    }
}
