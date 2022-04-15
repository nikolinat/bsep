package com.bsep.admin.app.service.contract;

import com.bsep.admin.app.model.CACertificateAlias;

public interface ICACertificateAliasService {

    CACertificateAlias create(CACertificateAlias entity) throws Exception;

    CACertificateAlias getActiveCA();

    CACertificateAlias getActiveIntermediate();

    CACertificateAlias findByAlias(String alias);

    CACertificateAlias update(String alias, boolean active);
}
