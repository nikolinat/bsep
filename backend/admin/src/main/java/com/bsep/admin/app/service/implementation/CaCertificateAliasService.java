package com.bsep.admin.app.service.implementation;

import com.bsep.admin.app.model.CACertificateAlias;
import com.bsep.admin.app.repository.CACertificateAliasRepository;
import com.bsep.admin.app.service.contract.ICACertificateAliasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaCertificateAliasService implements ICACertificateAliasService {

    private CACertificateAliasRepository certificateRepository;


    @Autowired
    public CaCertificateAliasService(CACertificateAliasRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    @Override
    public CACertificateAlias create(CACertificateAlias entity) throws Exception {
        return this.certificateRepository.save(entity);
    }

    @Override
    public CACertificateAlias getActiveCA() {
        return this.certificateRepository.findActiveCA();
    }

    @Override
    public CACertificateAlias getActiveIntermediate() {
        return this.certificateRepository.findActiveIntermediate();
    }

    @Override
    public CACertificateAlias findByAlias(String alias) {
        return this.certificateRepository.findByAlias(alias);
    }

    @Override
    public CACertificateAlias update(String alias, boolean active) {
        CACertificateAlias caCertificateAlias = findByAlias(alias);
        caCertificateAlias.setActive(active);
        return this.certificateRepository.save(caCertificateAlias);

    }
}
