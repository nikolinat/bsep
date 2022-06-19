package com.bsep.securehome.service.contract;

import com.bsep.securehome.dto.UpdateRealEstateDto;
import com.bsep.securehome.model.RealEstate;

import java.util.List;

public interface IRealEstateService {

    List<RealEstate> findAll();

    RealEstate update(UpdateRealEstateDto updateRealEstateDto);

    RealEstate removeTenant(Integer id, Long realEstateId) throws Exception;

    List<RealEstate> findRealEstatesByOwner(Integer ownerId);

    List<RealEstate> findRealEstatesByTenant(Integer tenantId);
}
