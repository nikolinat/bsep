package com.bsep.admin.app.service.implementation;

import com.bsep.admin.app.dto.LogSearchDto;
import com.bsep.admin.app.model.Log;
import com.bsep.admin.app.repository.LogRepository;
import com.bsep.admin.app.service.contract.ILoggingService;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoggingService implements ILoggingService {
    private final LogRepository logRepository;
    private final KieContainer kieContainer;

    @Autowired
    public LoggingService(LogRepository logRepository, KieContainer kieContainer) {
        this.logRepository = logRepository;
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
        kieSession.getAgenda().getAgendaGroup("newRule").setFocus();
        kieSession.fireAllRules();
        kieSession.dispose();
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
