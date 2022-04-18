package com.bsep.admin.app.controller;

import com.bsep.admin.app.dto.GenerateCertificateDto;
import com.bsep.admin.app.model.CertificateSigningRequest;
import com.bsep.admin.app.service.contract.ICertificateSigningRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value="/api/v1/csr")
public class CertificateSigningRequestController {
    private ICertificateSigningRequestService certificateSigningRequestService;

    @Autowired
    public CertificateSigningRequestController(ICertificateSigningRequestService certificateSigningRequestService) {
        this.certificateSigningRequestService = certificateSigningRequestService;
    }

    @PostMapping("")
    public ResponseEntity<CertificateSigningRequest> createCertificateSigningRequest(@RequestBody CertificateSigningRequest certificateSigningRequest)
            throws Exception {
        return new ResponseEntity<>(certificateSigningRequestService.create(certificateSigningRequest), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<CertificateSigningRequest>> getCertificateSigningRequest() {
        return new ResponseEntity<>(certificateSigningRequestService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/decline/{id}")
    public ResponseEntity<?> declineCertificateSigningRequest(@PathVariable Integer id, @RequestBody String reasonForDeclining) throws Exception {
        certificateSigningRequestService.declineCertificateSigningRequest(id, reasonForDeclining);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/accept/{id}")
    public ResponseEntity<?> acceptCertificateSigningRequest(@PathVariable Integer id, @Valid @RequestBody GenerateCertificateDto
            generateCertificateDto) throws Exception {
        certificateSigningRequestService.acceptCertificateSigningRequest(id, generateCertificateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
