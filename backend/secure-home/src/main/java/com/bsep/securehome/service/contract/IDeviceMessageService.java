package com.bsep.securehome.service.contract;

import java.util.List;

import com.bsep.securehome.dto.AlarmDto;
import com.bsep.securehome.dto.SearchFilterDeviceMessagesDto;
import com.bsep.securehome.model.DeviceMessage;

import java.io.FileNotFoundException;

public interface IDeviceMessageService {
    DeviceMessage create(DeviceMessage deviceMessage, List<AlarmDto> alarms, Long realEstateId);

    List<DeviceMessage> findByRealEstateId(Long realEstateId) throws FileNotFoundException;

    List<DeviceMessage> findAll();

    List<DeviceMessage> filterAndSearch(Long realEstateId, SearchFilterDeviceMessagesDto searchFilterDeviceMessagesDto) throws FileNotFoundException;
}
