package com.bsep.admin.app.controller;

import com.bsep.admin.app.dto.CertificateDto;
import com.bsep.admin.app.model.RevokedCertificate;
import com.bsep.admin.app.service.contract.ICertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.text.ParseException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value="/api/v1/certificate")
public class CertificateController {
    private ICertificateService certificateService;

    @Autowired
    public CertificateController(ICertificateService certificateService){
        this.certificateService = certificateService;
    }

    @PutMapping("/{reason}")
    public ResponseEntity<?> revokeCertificate(@RequestBody CertificateDto certificate, @PathVariable String reason) throws Exception {
        System.out.println(certificate.getSerialNumber());
        certificateService.revoke(certificate, reason);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllValidCertificates() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, ParseException, NoSuchProviderException {
        List<CertificateDto> certificates = certificateService.findAllValidCertificates();
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    @GetMapping("/revoked")
    public ResponseEntity<?> getAllRevokedCertificates() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, ParseException, NoSuchProviderException {
        List<CertificateDto> certificates = certificateService.findAllRevokedCertificates();
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    @GetMapping("/create")
    public ResponseEntity<?> createAll() throws Exception {
        certificateService.createAll();
        return new ResponseEntity<>( HttpStatus.OK);
    }




}