package com.bsep.securehome.controller;


import com.bsep.securehome.dto.RealEstateDto;
import com.bsep.securehome.dto.SearchFilterUserDto;
import com.bsep.securehome.dto.UpdateRealEstateDto;
import com.bsep.securehome.dto.UserDto;
import com.bsep.securehome.mapper.RealEstateMapper;
import com.bsep.securehome.model.RealEstate;
import com.bsep.securehome.model.User;
import com.bsep.securehome.service.implementation.RealEstateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin()
@RestController
@RequestMapping(value = "/api/v1/realestates", produces = MediaType.APPLICATION_JSON_VALUE)
public class RealEstateController {

    private RealEstateService realEstateService;
    private RealEstateMapper realEstateMapper;

    @Autowired
    public RealEstateController(RealEstateService realEstateService, RealEstateMapper realEstateMapper) {
        this.realEstateService = realEstateService;
        this.realEstateMapper = realEstateMapper;
    }

    @PreAuthorize("hasAuthority('READ_REAL_ESTATES')")
    @GetMapping("")
    public ResponseEntity<List<RealEstateDto>> getRealEstates() {
        List<RealEstate> realEstates = realEstateService.findAll();
        List<RealEstateDto> realEstateDto = realEstates.stream().map(realEstate ->
                realEstateMapper.realEstateToRealEstateDto(realEstate)).collect(Collectors.toList());
        return new ResponseEntity<>(realEstateDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ_REAL_ESTATES')")
    @PutMapping("")
    public ResponseEntity<?> updateRealEstate(@RequestBody UpdateRealEstateDto updateRealEstateDto) {
        RealEstate realEstate = realEstateService.update(updateRealEstateDto);
        return new ResponseEntity<>(realEstate, HttpStatus.OK);
    }




}