package com.bsep.securehome.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsep.securehome.model.Notification;
import com.bsep.securehome.service.contract.INotificationService;
import com.bsep.securehome.sockets.SocketsModule;

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
