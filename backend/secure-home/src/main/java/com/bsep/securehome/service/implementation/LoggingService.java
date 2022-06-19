package com.bsep.securehome.service.implementation;

import com.bsep.securehome.model.Log;
import com.bsep.securehome.repository.LogRepository;
import com.bsep.securehome.service.contract.ILoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
