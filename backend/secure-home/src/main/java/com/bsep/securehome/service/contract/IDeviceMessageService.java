package com.bsep.securehome.service.contract;

import java.util.List;

import com.bsep.securehome.dto.AlarmDto;
import com.bsep.securehome.dto.DeviceReportDto;
import com.bsep.securehome.dto.MessageDto;
import com.bsep.securehome.dto.SearchDeviceDto;
import com.bsep.securehome.dto.SearchFilterDeviceMessagesDto;
import com.bsep.securehome.model.DeviceMessage;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IDeviceMessageService {
    DeviceMessage create(DeviceMessage deviceMessage, List<AlarmDto> alarms, Long realEstateId);

    List<DeviceMessage> findByRealEstateId(Long realEstateId) throws FileNotFoundException;

    List<DeviceMessage> findAll();

    List<DeviceMessage> filterAndSearch(Long realEstateId, SearchFilterDeviceMessagesDto searchFilterDeviceMessagesDto)
            throws FileNotFoundException;

    void addMessageToFile(MessageDto messageDto) throws IOException;

    List<DeviceReportDto> report(SearchDeviceDto searchDeviceDto) throws FileNotFoundException;
}
