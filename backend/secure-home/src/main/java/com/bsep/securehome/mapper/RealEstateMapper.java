package com.bsep.securehome.mapper;

import com.bsep.securehome.dto.RealEstateDto;
import com.bsep.securehome.model.RealEstate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RealEstateMapper {
    @Autowired
    private UserMapper userMapper;

    public RealEstateDto realEstateToRealEstateDto(RealEstate realEstate) {
        return new RealEstateDto(realEstate.getName(), realEstate.getAddress(), realEstate.getId(),  
        realEstate.getOwners().stream().map(owner -> userMapper.userToUserDto(owner)).collect(Collectors.toList()),
        realEstate.getTenants().stream().map(tenant -> userMapper.userToUserDto(tenant)).collect(Collectors.toList()));
    }
}
