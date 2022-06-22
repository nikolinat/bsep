package com.bsep.securehome.sockets;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bsep.securehome.model.Notification;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

@Component
public class SocketsModule {
    private final SocketIOServer server;
    private final Map<String, SocketIONamespace> namespaces;

    @Autowired
    public SocketsModule(SocketIOServer server) {
        this.server = server;
        this.namespaces = new HashMap<>();
    }

    public void tryToRegisterNameSpace(String namespaceName) {
        String namespacePath = "/" + namespaceName;
        if (namespaces.containsKey(namespacePath)) {
            return;
        }

        System.out.println(namespacePath);
        SocketIONamespace namespace = server.addNamespace(namespacePath);

        namespace.addConnectListener(onConnected());
        namespace.addDisconnectListener(onDisconnected());
        System.out.println("TU SAM");
        namespaces.put(namespaceName, namespace);
    }

    private ConnectListener onConnected() {
        return client -> {
            HandshakeData handshakeData = client.getHandshakeData();
            System.out.format("Client[%s] - Connected to sockets module through '%s'", client.getSessionId().toString(),
                    handshakeData.getUrl());
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> System.out.format("Client[%s] - Disconnected from sockets module.",
                client.getSessionId().toString());
    }

    public void brodcast(String namespaceName, Notification notification) {
        SocketIONamespace namespace = namespaces.get(namespaceName);

        if (namespace == null) {
            return;
        }

        namespace.getBroadcastOperations().sendEvent("event", notification);
    }
}
