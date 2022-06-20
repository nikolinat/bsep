package com.bsep.securehome.service.implementation;

import java.util.List;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsep.securehome.dto.AlarmDto;
import com.bsep.securehome.model.DeviceMessage;
import com.bsep.securehome.repository.DeviceMessageRepository;
import com.bsep.securehome.service.contract.IDeviceMessageService;

@Service
public class DeviceMessageService implements IDeviceMessageService {

    private final DeviceMessageRepository deviceMessageRepository;
    private final KieContainer kieContainer;

    @Autowired
    public DeviceMessageService(DeviceMessageRepository deviceMessageRepository, KieContainer kieContainer) {
        this.deviceMessageRepository = deviceMessageRepository;
        this.kieContainer = kieContainer;
    }

    @Override
    public DeviceMessage create(DeviceMessage deviceMessage, List<AlarmDto> alarms) {
        if (alarms.size() != 0) {
            KieSession kieSession = kieContainer.newKieSession();
            kieSession.insert(deviceMessage);
            alarms.forEach(alarm -> kieSession.insert(alarm));
            kieSession.getAgenda().getAgendaGroup("alarm").setFocus();
            kieSession.fireAllRules();
            kieSession.dispose();
        }
        return deviceMessageRepository.save(deviceMessage);
    }

}
