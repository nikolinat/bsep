package com.bsep.securehome.service.contract;

import com.bsep.securehome.dto.UpdateRealEstateDto;
import com.bsep.securehome.model.RealEstate;

import java.util.List;

public interface IRealEstateService {

    List<RealEstate> findAll();

    RealEstate update(UpdateRealEstateDto updateRealEstateDto);
}
