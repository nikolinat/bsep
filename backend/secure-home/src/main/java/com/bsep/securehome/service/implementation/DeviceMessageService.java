package com.bsep.securehome.service.implementation;

import java.util.Collection;
import java.util.List;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsep.securehome.dto.AlarmDto;
import com.bsep.securehome.model.DeviceMessage;
import com.bsep.securehome.model.RealEstate;
import com.bsep.securehome.model.User;
import com.bsep.securehome.repository.DeviceMessageRepository;
import com.bsep.securehome.repository.RealEstateRepository;
import com.bsep.securehome.service.EmailService;
import com.bsep.securehome.service.contract.IDeviceMessageService;

@Service
public class DeviceMessageService implements IDeviceMessageService {

    private final DeviceMessageRepository deviceMessageRepository;
    private RealEstateRepository realEstateRepository;
    private final KieContainer kieContainer;
    private EmailService emailService;

    @Autowired
    public DeviceMessageService(DeviceMessageRepository deviceMessageRepository, KieContainer kieContainer,
            RealEstateRepository realEstateRepository, EmailService emailService) {
        this.deviceMessageRepository = deviceMessageRepository;
        this.kieContainer = kieContainer;
        this.realEstateRepository = realEstateRepository;
        this.emailService = emailService;
    }

    @Override
    public DeviceMessage create(DeviceMessage deviceMessage, List<AlarmDto> alarms, Long realEstateId) {
        if (alarms.size() != 0) {
            KieSession kieSession = kieContainer.newKieSession();
            kieSession.insert(deviceMessage);
            alarms.forEach(alarm -> kieSession.insert(alarm));
            kieSession.getAgenda().getAgendaGroup("alarm").setFocus();
            kieSession.fireAllRules();
            kieSession.dispose();
        }
        if(deviceMessage.isAlarm()){
           RealEstate realEstate= realEstateRepository.findById(realEstateId).orElse(null);
           Collection<User> owners = realEstate.getOwners();
           for(User owner: owners){
            emailService.sendEmailMessageFromDevice(owner.getEmailAddress(), deviceMessage.getMessage());
           }
        }
        return deviceMessageRepository.save(deviceMessage);
    }

}
