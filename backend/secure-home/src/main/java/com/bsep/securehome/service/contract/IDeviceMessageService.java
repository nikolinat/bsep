package com.bsep.securehome.service.contract;

import com.bsep.securehome.dto.SearchFilterDeviceMessagesDto;
import com.bsep.securehome.model.DeviceMessage;

import java.io.FileNotFoundException;
import java.util.List;

public interface IDeviceMessageService {
    DeviceMessage create(DeviceMessage deviceMessage);

    List<DeviceMessage> findByRealEstateId(Long realEstateId) throws FileNotFoundException;

    List<DeviceMessage> findAll();

    List<DeviceMessage> filterAndSearch(Long realEstateId, SearchFilterDeviceMessagesDto searchFilterDeviceMessagesDto) throws FileNotFoundException;
}
