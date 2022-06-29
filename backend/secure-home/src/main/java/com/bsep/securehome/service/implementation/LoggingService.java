package com.bsep.securehome.service.implementation;

import com.bsep.securehome.dto.LogAlarm;
import com.bsep.securehome.model.Log;
import com.bsep.securehome.model.Notification;
import com.bsep.securehome.model.User;
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
    private final KieContainer kieContainer;
    private final NotificationService notificationService;
    private final UserService userService;

    @Autowired
    public LoggingService(LogRepository logRepository,
            KieContainer kieContainer,
            NotificationService notificationService,
            UserService userService) {
        this.logRepository = logRepository;
        this.kieContainer = kieContainer;
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @Override
    public List<Log> findAll() {
        return logRepository.findAll();
    }

    @Override
    public Log create(Log log) {
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(log);
        kieSession.getAgenda().getAgendaGroup("secure home log alarm").setFocus();
        kieSession.fireAllRules();
        kieSession.dispose();
        Collection<Object> ruleOutputObjects = (Collection<Object>) kieSession.getObjects();
        for (Object o : ruleOutputObjects) {
            if (o instanceof LogAlarm) {
                List<User> admins = userService.findAdmins();
                LogAlarm logAlarm = (LogAlarm) o;
                if(!logAlarm.getIsAlerted()){
                logAlarm.setIsAlerted(true);
                admins.forEach(admin -> notificationService
                        .sendNotification(
                                new Notification("notificationSocket/" + Integer.toString(admin.getId()), "Log alarm",
                                        logAlarm.getMessage())));
            }
        }
        }

        return logRepository.save(log);
    }
}
