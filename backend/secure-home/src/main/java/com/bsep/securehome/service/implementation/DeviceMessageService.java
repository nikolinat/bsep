package com.bsep.securehome.service.implementation;

import com.bsep.securehome.dto.DeviceDto;
import com.bsep.securehome.dto.SearchFilterDeviceMessagesDto;
import com.bsep.securehome.model.DeviceMessage;
import com.bsep.securehome.repository.DeviceMessageRepository;
import com.bsep.securehome.service.contract.IDeviceMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceMessageService implements IDeviceMessageService{
    private final DeviceService deviceService;
    private final DeviceMessageRepository deviceMessageRepository;

    @Autowired
    public DeviceMessageService(DeviceService deviceService,
                                DeviceMessageRepository deviceMessageRepository) {
        this.deviceService = deviceService;
        this.deviceMessageRepository = deviceMessageRepository;
    }

    @Override
    public DeviceMessage create(DeviceMessage deviceMessage) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DeviceMessage> findByRealEstateId(Long realEstateId) throws FileNotFoundException {
        List<DeviceDto> deviceDtos = deviceService.findDevicesForRealEstate(realEstateId);

        List<DeviceMessage> deviceMessages = findAll();

        List<String> devicesIds = new ArrayList<>();
        if(deviceDtos != null) {
            deviceDtos.forEach(deviceDto -> devicesIds.add(deviceDto.getId()));
        }

        return  deviceMessages.stream().filter(deviceMessage -> devicesIds.contains(deviceMessage.getDeviceId())).collect(Collectors.toList());
    }

    @Override
    public List<DeviceMessage> findAll() {
        return deviceMessageRepository.findAll();
    }

    @Override
    public List<DeviceMessage> filterAndSearch(Long realEstateId, SearchFilterDeviceMessagesDto searchFilterDeviceMessagesDto) throws FileNotFoundException {
        List<DeviceMessage> deviceMessagesForRealEstate = findByRealEstateId(realEstateId);

        return deviceMessagesForRealEstate.stream().filter(deviceMessage ->
                (searchFilterDeviceMessagesDto.getType() == null || deviceMessage.getType() == searchFilterDeviceMessagesDto.getType()) &&
                        (searchFilterDeviceMessagesDto.isAlarm() == null || deviceMessage.isAlarm() == searchFilterDeviceMessagesDto.isAlarm()) &&
                        (searchFilterDeviceMessagesDto.getMessage().isEmpty() || deviceMessage.getMessage().contains(searchFilterDeviceMessagesDto.getMessage())) &&
                        (searchFilterDeviceMessagesDto.getDateTime() == null || searchFilterDeviceMessagesDto.getDateTime().toLocalDate().isEqual(deviceMessage.getDateTime().toLocalDate())))
                .collect(Collectors.toList());
    }

}
