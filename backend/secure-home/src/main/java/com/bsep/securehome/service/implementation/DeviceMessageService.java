package com.bsep.securehome.service.implementation;

import java.util.Collection;
import java.util.List;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bsep.securehome.dto.AlarmDto;
import com.bsep.securehome.model.DeviceMessage;
import com.bsep.securehome.model.Notification;
import com.bsep.securehome.model.RealEstate;
import com.bsep.securehome.model.User;
import com.bsep.securehome.repository.DeviceMessageRepository;
import com.bsep.securehome.repository.RealEstateRepository;
import com.bsep.securehome.service.EmailService;
import com.bsep.securehome.dto.DeviceDto;
import com.bsep.securehome.dto.MessageDto;
import com.bsep.securehome.dto.SearchFilterDeviceMessagesDto;
import com.bsep.securehome.service.contract.IDeviceMessageService;
import com.bsep.securehome.service.contract.INotificationService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class DeviceMessageService implements IDeviceMessageService {

    private final DeviceMessageRepository deviceMessageRepository;
    private RealEstateRepository realEstateRepository;
    private final KieContainer kieContainer;
    private final DeviceService deviceService;
    private INotificationService notificationService;

    @Autowired
    public DeviceMessageService(DeviceMessageRepository deviceMessageRepository, KieContainer kieContainer,
            RealEstateRepository realEstateRepository, EmailService emailService,
            DeviceService deviceService,
            INotificationService notificationService) {
        this.deviceMessageRepository = deviceMessageRepository;
        this.kieContainer = kieContainer;
        this.realEstateRepository = realEstateRepository;
        this.deviceService = deviceService;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional
    public DeviceMessage create(DeviceMessage deviceMessage, List<AlarmDto> alarms, Long realEstateId) {
        // if (alarms.size() != 0) {
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(deviceMessage);
        alarms.forEach(alarm -> kieSession.insert(alarm));
        kieSession.getAgenda().getAgendaGroup("alarm").setFocus();
        kieSession.fireAllRules();
        kieSession.dispose();
        // }
        if (deviceMessage.isAlarm()) {
            RealEstate realEstate = realEstateRepository.findById(realEstateId).orElse(null);
            Collection<User> owners = realEstate.getOwners();
            for (User owner : owners) {
                // emailService.sendEmailMessageFromDevice(owner.getEmailAddress(),
                // deviceMessage.getMessage());
                notificationService
                        .sendNotification(
                                new Notification("notificationSocket/" + Integer.toString(owner.getId()), "Alarm",
                                        deviceMessage.getMessage()));
            }
        }
        return deviceMessageRepository.save(deviceMessage);
    }

    @Override
    public List<DeviceMessage> findByRealEstateId(Long realEstateId) throws FileNotFoundException {
        List<DeviceDto> deviceDtos = deviceService.findDevicesForRealEstate(realEstateId);

        List<DeviceMessage> deviceMessages = findAll();

        List<String> devicesIds = new ArrayList<>();
        if (deviceDtos != null) {
            deviceDtos.forEach(deviceDto -> devicesIds.add(deviceDto.getId()));
        }

        return deviceMessages.stream().filter(deviceMessage -> devicesIds.contains(deviceMessage.getDeviceId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<DeviceMessage> findAll() {
        return deviceMessageRepository.findAll();
    }

    @Override
    public List<DeviceMessage> filterAndSearch(Long realEstateId,
            SearchFilterDeviceMessagesDto searchFilterDeviceMessagesDto) throws FileNotFoundException {
        List<DeviceMessage> deviceMessagesForRealEstate = findByRealEstateId(realEstateId);

        return deviceMessagesForRealEstate.stream()
                .filter(deviceMessage -> (searchFilterDeviceMessagesDto.getType() == null
                        || deviceMessage.getType() == searchFilterDeviceMessagesDto.getType()) &&
                        (searchFilterDeviceMessagesDto.isAlarm() == null
                                || deviceMessage.isAlarm() == searchFilterDeviceMessagesDto.isAlarm())
                        &&
                        (searchFilterDeviceMessagesDto.getMessage().isEmpty()
                                || deviceMessage.getMessage().contains(searchFilterDeviceMessagesDto.getMessage()))
                        &&
                        (searchFilterDeviceMessagesDto.getDateTime() == null || searchFilterDeviceMessagesDto
                                .getDateTime().toLocalDate().isEqual(deviceMessage.getDateTime().toLocalDate())))
                .collect(Collectors.toList());
    }

    @Override
    public void addMessageToFile(MessageDto messageDto) throws IOException {
        String filePath = "src/main/java/files/messages/messages.txt";
        File file = new File(filePath);
        if (file.exists()) {
            Files.write(Paths.get(filePath), messageDto.toString().getBytes(), StandardOpenOption.APPEND);
        } else {
            Files.write(Paths.get(filePath), messageDto.toString().getBytes(), StandardOpenOption.CREATE);
        }
    }
}
