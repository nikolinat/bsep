package com.bsep.securehome.service.contract;

import com.bsep.securehome.model.Notification;

public interface INotificationService {
    void sendNotification(Notification notification);
}
