package com.bsep.admin.app.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsep.admin.app.annotation.LogAfterReturning;
import com.bsep.admin.app.annotation.LogAfterThrowing;
import com.bsep.admin.app.dto.RuleLogDto;
import com.bsep.admin.app.service.implementation.RuleService;

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

    @PreAuthorize("hasAuthority('CREATE_RULE_LOG')")
    @LogAfterThrowing(message = "ERROR create rule for log")
    @LogAfterReturning(message = "SUCCESS create rule for log")
    @PostMapping("/log")
    public ResponseEntity<?> createRuleLog(@RequestBody RuleLogDto ruleDto) throws Exception {
        ruleService.logTemplate(ruleDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
