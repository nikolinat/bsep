package com.bsep.admin.app.dto;


import com.bsep.admin.app.validators.constraints.ExtendedKeyUsageConstraint;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class GenerateCertificateDto {
    @NotNull(message = "End date should not be null or empty")
    private Date startDate;
    @NotNull(message = "End date should not be null or empty")
    private Date endDate;
    private List<Integer> keyUsagesExtension;
    @Valid
    private GeneralNames generalNamesForAuthorityKeyIdentifier;
    @Valid
    private GeneralNames subjectAlternativeNames;
    @ExtendedKeyUsageConstraint
    private List<String> extendedKeyUsages;
    private BigInteger requireExplicitPolicy;
    private BigInteger inhibitPolicyMapping;
    private List<GeneralSubtreeElement> permitedSubtrees;
    private List<GeneralSubtreeElement> excludedSubtrees;
    @Valid
    private GeneralNames issuerAlternativeNames;

    public GenerateCertificateDto() {

    }

    public GenerateCertificateDto(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public GenerateCertificateDto(Date startDate, Date endDate, List<Integer> keyUsagesExtension,
                                  GeneralNames generalNamesForAuthorityKeyIdentifier, GeneralNames subjectAlternativeNames,
                                  List<String> extendedKeyUsages, BigInteger requireExplicitPolicy, BigInteger inhibitPolicyMapping,
                                  List<GeneralSubtreeElement> permitedSubtrees, List<GeneralSubtreeElement> excludedSubtrees, GeneralNames issuerAlternativeNames) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.keyUsagesExtension = keyUsagesExtension;
        this.generalNamesForAuthorityKeyIdentifier = generalNamesForAuthorityKeyIdentifier;
        this.subjectAlternativeNames = subjectAlternativeNames;
        this.extendedKeyUsages = extendedKeyUsages;
        this.requireExplicitPolicy = requireExplicitPolicy;
        this.inhibitPolicyMapping = inhibitPolicyMapping;
        this.permitedSubtrees = permitedSubtrees;
        this.excludedSubtrees = excludedSubtrees;
        this.issuerAlternativeNames = issuerAlternativeNames;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Integer> getKeyUsagesExtension() {
        return keyUsagesExtension;
    }

    public void setKeyUsagesExtension(List<Integer> keyUsagesExtension) {
        this.keyUsagesExtension = keyUsagesExtension;
    }

    public GeneralNames getGeneralNamesForAuthorityKeyIdentifier() {
        return generalNamesForAuthorityKeyIdentifier;
    }

    public void setGeneralNamesForAuthorityKeyIdentifier(GeneralNames generalNamesForAuthorityKeyIdentifier) {
        this.generalNamesForAuthorityKeyIdentifier = generalNamesForAuthorityKeyIdentifier;
    }

    public GeneralNames getSubjectAlternativeNames() {
        return subjectAlternativeNames;
    }

    public void setSubjectAlternativeNames(GeneralNames subjectAlternativeNames) {
        this.subjectAlternativeNames = subjectAlternativeNames;
    }

    public List<String> getExtendedKeyUsages() {
        return extendedKeyUsages;
    }

    public void setExtendedKeyUsages(List<String> extendedKeyUsages) {
        this.extendedKeyUsages = extendedKeyUsages;
    }

    public BigInteger getRequireExplicitPolicy() {
        return requireExplicitPolicy;
    }

    public void setRequireExplicitPolicy(BigInteger requireExplicitPolicy) {
        this.requireExplicitPolicy = requireExplicitPolicy;
    }

    public BigInteger getInhibitPolicyMapping() {
        return inhibitPolicyMapping;
    }

    public void setInhibitPolicyMapping(BigInteger inhibitPolicyMapping) {
        this.inhibitPolicyMapping = inhibitPolicyMapping;
    }

    public List<GeneralSubtreeElement> getPermitedSubtrees() {
        return permitedSubtrees;
    }

    public void setPermitedSubtrees(List<GeneralSubtreeElement> permitedSubtrees) {
        this.permitedSubtrees = permitedSubtrees;
    }

    public List<GeneralSubtreeElement> getExcludedSubtrees() {
        return excludedSubtrees;
    }

    public void setExcludedSubtrees(List<GeneralSubtreeElement> excludedSubtrees) {
        this.excludedSubtrees = excludedSubtrees;
    }

    public GeneralNames getIssuerAlternativeNames() {
        return issuerAlternativeNames;
    }

    public void setIssuerAlternativeNames(GeneralNames issuerAlternativeNames) {
        this.issuerAlternativeNames = issuerAlternativeNames;
    }
}
