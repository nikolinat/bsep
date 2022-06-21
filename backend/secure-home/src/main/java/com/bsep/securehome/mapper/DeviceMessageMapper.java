package com.bsep.securehome.mapper;

import com.bsep.securehome.dto.DeviceDto;
import com.bsep.securehome.dto.DeviceMessageDto;
import com.bsep.securehome.model.DeviceMessage;
import com.bsep.securehome.service.implementation.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeviceMessageMapper {
    private DeviceService deviceService;

    @Autowired
    public DeviceMessageMapper(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    public DeviceMessageDto fromDeviceMessageToDeviceMessageDto(DeviceMessage deviceMessage, Long realEstateId) throws Exception {
        List<DeviceDto> deviceDtoList = deviceService.findDevicesForRealEstate(realEstateId).stream().filter(deviceDto -> deviceDto.getId().equals(deviceMessage.getDeviceId()))
                .collect(Collectors.toList());

        return new DeviceMessageDto(deviceMessage.getDeviceId(), deviceDtoList.get(0).getName(), deviceMessage.getType(),
                deviceMessage.getMessage(), deviceMessage.getDateTime(), deviceMessage.isAlarm());
    }

}
