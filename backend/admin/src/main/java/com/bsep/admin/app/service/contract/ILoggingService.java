package com.bsep.admin.app.service.contract;

import com.bsep.admin.app.dto.LogSearchDto;
import com.bsep.admin.app.model.Log;

import java.util.List;

public interface ILoggingService {
    List<Log> findAll();
    Log create(Log log);
    List<Log> searchAndFilter(LogSearchDto logSearchDto);
}
