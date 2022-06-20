package com.bsep.securehome.service.contract;

import java.util.List;

import com.bsep.securehome.dto.AlarmDto;
import com.bsep.securehome.model.DeviceMessage;

public interface IDeviceMessageService {
    DeviceMessage create(DeviceMessage deviceMessage, List<AlarmDto> alarms);
}
