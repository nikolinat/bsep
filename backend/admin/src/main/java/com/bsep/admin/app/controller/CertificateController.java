package com.bsep.admin.app.controller;

import com.bsep.admin.app.annotation.LogAfterReturning;
import com.bsep.admin.app.annotation.LogAfterThrowing;
import com.bsep.admin.app.dto.CertificateDto;
import com.bsep.admin.app.service.contract.ICertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('EDIT_CERTIFICATE')")
    @LogAfterThrowing(message = "ERROR revoke certificate")
    @LogAfterReturning(message = "SUCCESS revoke certificate")
    @PutMapping("/{reason}")
    public ResponseEntity<?> revokeCertificate(@RequestBody CertificateDto certificate, @PathVariable String reason) throws Exception {
        certificateService.revoke(certificate, reason);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ_CERTIFICATES')")
    @LogAfterThrowing(message = "ERROR read all valid certificates")
    @LogAfterReturning(message = "SUCCESS read all valid certificates")
    @GetMapping("")
    public ResponseEntity<?> getAllValidCertificates() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, ParseException, NoSuchProviderException {
        List<CertificateDto> certificates = certificateService.findAllValidCertificates();
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ_CERTIFICATES')")
    @LogAfterThrowing(message = "ERROR read all revoked certificates")
    @LogAfterReturning(message = "SUCCESS read all revoked certificates")
    @GetMapping("/revoked")
    public ResponseEntity<?> getAllRevokedCertificates() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, ParseException, NoSuchProviderException {
        List<CertificateDto> certificates = certificateService.findAllRevokedCertificates();
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    @GetMapping("/create")
    @LogAfterThrowing(message = "ERROR create root")
    @LogAfterReturning(message = "SUCCESS create root")
    public ResponseEntity<?> createAll() throws Exception {
        certificateService.createAll();
        return new ResponseEntity<>( HttpStatus.OK);
    }




}