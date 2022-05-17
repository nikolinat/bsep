package com.bsep.admin.app.controller;

import com.bsep.admin.app.dto.CertificateDto;
import com.bsep.admin.app.model.RevokedCertificate;
import com.bsep.admin.app.service.EmailService;
import com.bsep.admin.app.service.contract.ICertificateService;
import com.bsep.admin.crypto.pki.certificates.CertificateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
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
    private EmailService emailService;

    @Autowired
    public CertificateController(ICertificateService certificateService, EmailService emailService){
        this.certificateService = certificateService;
        this.emailService = emailService;
    }

    @PreAuthorize("hasAuthority('EDIT_CERTIFICATE')")
    @PutMapping("/{reason}")
    public ResponseEntity<?> revokeCertificate(@RequestBody CertificateDto certificate, @PathVariable String reason) throws Exception {
        certificateService.revoke(certificate, reason);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ_CERTIFICATES')")
    @GetMapping("")
    public ResponseEntity<?> getAllValidCertificates() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, ParseException, NoSuchProviderException {
        List<CertificateDto> certificates = certificateService.findAllValidCertificates();
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ_CERTIFICATES')")
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