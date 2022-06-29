package com.bsep.admin.app.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsep.admin.app.model.Notification;
import com.bsep.admin.app.service.contract.INotificationService;
import com.bsep.admin.app.sockets.SocketsModule;

@Service
public class NotificationService implements INotificationService {
    private SocketsModule sockets;

    @Autowired
    public NotificationService(SocketsModule sockets) {
        this.sockets = sockets;
    }

    @Override
    public void sendNotification(Notification notification) {
        sockets.brodcast(String.valueOf(notification.getId()), notification);
    }

}
