package com.bsep.securehome.service.contract;

import com.bsep.securehome.model.Log;

import java.util.List;

public interface ILoggingService {

    List<Log> findAll();

    Log create(Log log);
}
