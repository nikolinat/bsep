package com.bsep.securehome.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsep.securehome.repository.DevicesLogRepository;
import com.bsep.securehome.service.contract.IDevicesLogService;
import com.bsep.securehome.model.DevicesLog;


@Service
public class DevicesLogService implements IDevicesLogService {
    
    private final DevicesLogRepository devicesLogRepository;

    @Autowired
    public DevicesLogService(DevicesLogRepository devicesLogRepository) {
        this.devicesLogRepository = devicesLogRepository;
    }

    @Override
    public List<DevicesLog> findAll() {
        return devicesLogRepository.findAll();
    }

    @Override
    public DevicesLog create(DevicesLog log) {
        return devicesLogRepository.save(log);
    }
}

