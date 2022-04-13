package com.bsep.admin.app.controller;

import com.bsep.admin.app.service.contract.ICertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value="/api/v1/certificate")
public class CertificateController {
    private ICertificateService certificateService;

    @Autowired
    public CertificateController(ICertificateService certificateService){
        this.certificateService = certificateService;
    }

//    @GetMapping("")
//    public ResponseEntity<?> getCertificates(){
//
//    }
}