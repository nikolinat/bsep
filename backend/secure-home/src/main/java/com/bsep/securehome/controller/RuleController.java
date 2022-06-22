package com.bsep.securehome.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsep.securehome.dto.RuleDto;
import com.bsep.securehome.service.implementation.RuleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

@CrossOrigin()
@RestController
@RequestMapping(value = "/api/v1/rule", produces = MediaType.APPLICATION_JSON_VALUE)
public class RuleController {

    private RuleService ruleService;

    @Autowired
    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @PreAuthorize("hasAuthority('CREATE_DEVICE')")
    @PostMapping("")
    public ResponseEntity<?> createRule(@RequestBody RuleDto ruleDto) throws Exception {
        ruleService.template(ruleDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
