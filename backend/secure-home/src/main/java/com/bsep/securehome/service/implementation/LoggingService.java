package com.bsep.securehome.service.implementation;

import com.bsep.securehome.model.DeviceMessage;
import com.bsep.securehome.model.Log;
import com.bsep.securehome.repository.DeviceMessageRepository;
import com.bsep.securehome.repository.LogRepository;
import com.bsep.securehome.service.contract.ILoggingService;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class LoggingService implements ILoggingService {
    private final LogRepository logRepository;
    private DeviceMessageRepository deviceMessageRepository;
    private final KieContainer kieContainer;

    @Autowired
    public LoggingService(LogRepository logRepository, DeviceMessageRepository deviceMessageRepository,
            KieContainer kieContainer) {
        this.logRepository = logRepository;
        this.deviceMessageRepository = deviceMessageRepository;
        this.kieContainer = kieContainer;
    }

    @Override
    public List<Log> findAll() {
        return logRepository.findAll();
    }

    @Override
    public Log create(Log log) {
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(log);
        kieSession.getAgenda().getAgendaGroup("alarm").setFocus();
        kieSession.fireAllRules();
        kieSession.dispose();
        Collection<Object> ruleOutputObjects = (Collection<Object>) kieSession.getObjects();
		for(Object o : ruleOutputObjects) {
			if( o instanceof DeviceMessage) {
				deviceMessageRepository.save((DeviceMessage) o);
			}
		}
		
        return logRepository.save(log);
    }
}
