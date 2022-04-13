package com.bsep.admin.app.service.contract;

import com.bsep.admin.app.model.Certificate;

import java.util.List;

public interface ICertificateService {
    List<Certificate> findAll();
    Certificate findBySerialNumber(String serialNumber) throws Exception;
    Certificate create(Certificate entity) throws Exception;
    Certificate update(Certificate entity, String id) throws Exception;
}
