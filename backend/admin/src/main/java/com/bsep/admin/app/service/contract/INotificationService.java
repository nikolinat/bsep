package com.bsep.admin.app.service.contract;

import com.bsep.admin.app.model.Notification;

public interface INotificationService {
    void sendNotification(Notification notification);
}
