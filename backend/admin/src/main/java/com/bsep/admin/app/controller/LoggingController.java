package com.bsep.admin.app.controller;

import com.bsep.admin.app.annotation.LogAfterReturning;
import com.bsep.admin.app.annotation.LogAfterThrowing;
import com.bsep.admin.app.dto.LogSearchDto;
import com.bsep.admin.app.service.contract.ILoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value="/api/v1/log")
public class LoggingController {
    private final ILoggingService loggingService;

    @Autowired
    public LoggingController(ILoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @GetMapping("")
    @LogAfterThrowing(message = "ERROR read all logs")
    @LogAfterReturning(message = "SUCCESS read all logs")
    public ResponseEntity<?> findAllLogs() throws Exception {
        return new ResponseEntity<>(this.loggingService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/search-filter")
    @LogAfterThrowing(message = "ERROR search and filter logs")
    @LogAfterReturning(message = "SUCCESS search and filter logs")
    public ResponseEntity<?> searchAndFilterLogs(LogSearchDto logSearchDto) throws Exception {
        return new ResponseEntity<>(this.loggingService.searchAndFilter(logSearchDto), HttpStatus.OK);
    }
}
