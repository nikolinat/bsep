package com.bsep.securehome.service.contract;

import java.util.List;

import com.bsep.securehome.model.DevicesLog;

public interface IDevicesLogService {

    List<DevicesLog> findAll();

    DevicesLog create(DevicesLog log);
}
