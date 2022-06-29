package com.bsep.admin.app.service.implementation;

import com.bsep.admin.app.dto.LogAlarm;
import com.bsep.admin.app.dto.LogSearchDto;
import com.bsep.admin.app.model.Log;
import com.bsep.admin.app.model.Notification;
import com.bsep.admin.app.model.User;
import com.bsep.admin.app.repository.LogRepository;
import com.bsep.admin.app.service.contract.ILoggingService;
import com.bsep.admin.app.service.contract.INotificationService;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoggingService implements ILoggingService {
    private final LogRepository logRepository;
    private final KieContainer kieContainer;
    private final INotificationService notificationService;
    private final UserService userService;

    @Autowired
    public LoggingService(LogRepository logRepository,
            KieContainer kieContainer,
            INotificationService notificationService,
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
        kieSession.getAgenda().getAgendaGroup("adminLogAlarm").setFocus();
        kieSession.fireAllRules();
        kieSession.dispose();
        Collection<Object> ruleOutputObjects = (Collection<Object>) kieSession.getObjects();
        for (Object o : ruleOutputObjects) {
            if (o instanceof LogAlarm) {
                List<User> admins = userService.findAdmins();
                LogAlarm logAlarm = (LogAlarm) o;
                logAlarm.setIsAlerted(true);
                System.out.println("OVDE SAMAMAMAMAMA");
                admins.forEach(admin -> notificationService
                        .sendNotification(
                                new Notification("notificationSocket/" + Integer.toString(admin.getId()), "Log alarm",
                                        logAlarm.getMessage())));
            }
        }

        return logRepository.save(log);
    }

    @Override
    public List<Log> searchAndFilter(LogSearchDto logSearchDto) {
        List<Log> logs = findAll();

        return logs.stream()
                .filter(log -> (logSearchDto.getLogType() == null || log.getLogType() == logSearchDto.getLogType()) &&
                        (logSearchDto.getMessageRegex().isEmpty()
                                || log.getMessage().contains(logSearchDto.getMessageRegex()) ||
                                log.getMessage().matches(logSearchDto.getMessageRegex()))
                        &&
                        (logSearchDto.getDateTime() == null
                                || log.getDateTime().toLocalDate().isEqual(logSearchDto.getDateTime().toLocalDate()) &&
                                        (logSearchDto.getApplicationName().equals(log.getApplicationName()))))
                .collect(Collectors.toList());
    }
}
