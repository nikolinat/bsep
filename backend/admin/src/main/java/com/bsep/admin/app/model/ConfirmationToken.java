package com.bsep.admin.app.model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

// mozda ovu klasu smestiti u neki drugi paket, samo ne znam koji
@Entity
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="token_id")
    private long tokenId;

    @Column(name="confirmation_token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne(targetEntity = CertificateSigningRequest.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "certificate_signing_request")
    private CertificateSigningRequest certificateSigningRequest;

    public ConfirmationToken() {}

    public ConfirmationToken(CertificateSigningRequest certificateSigningRequest){
        this.certificateSigningRequest = certificateSigningRequest;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }

    public long getTokenId() {
        return tokenId;
    }

    public void setTokenId(long tokenId) {
        this.tokenId = tokenId;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public CertificateSigningRequest getCertificateSigningRequest() {
        return certificateSigningRequest;
    }

    public void setCertificateSigningRequest(CertificateSigningRequest certificateSigningRequest) {
        this.certificateSigningRequest = certificateSigningRequest;
    }
}
