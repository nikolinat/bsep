package com.bsep.admin.app.service.implementation;

import com.bsep.admin.app.dto.LogSearchDto;
import com.bsep.admin.app.model.Log;
import com.bsep.admin.app.repository.LogRepository;
import com.bsep.admin.app.service.contract.ILoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoggingService implements ILoggingService {
    private final LogRepository logRepository;

    @Autowired
    public LoggingService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public List<Log> findAll() {
        return logRepository.findAll();
    }

    @Override
    public Log create(Log log) {
        return logRepository.save(log);
    }

    @Override
    public List<Log> searchAndFilter(LogSearchDto logSearchDto) {
        List<Log> logs = findAll();

        return logs.stream().filter(log -> (logSearchDto.getLogType() == null || log.getLogType() == logSearchDto.getLogType()) &&
                (logSearchDto.getMessageRegex().isEmpty() || log.getMessage().contains(logSearchDto.getMessageRegex()) ||
                        log.getMessage().matches(logSearchDto.getMessageRegex())) &&
                (logSearchDto.getDateTime() == null || log.getDateTime().toLocalDate().isEqual(logSearchDto.getDateTime().toLocalDate()))).collect(Collectors.toList());
    }
}
