package com.work.selll.service;

import org.hibernate.annotations.OnDelete;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/websocket")
public class WebSocket {
    private Session session;
    private static CopyOnWriteArraySet<WebSocket> copyOnWriteArraySet =new CopyOnWriteArraySet<>();
    @OnOpen
    public void onOpen(Session session)
    {
        this.session=session;
        copyOnWriteArraySet.add(this);
    }

    @OnClose
    public void onClose()
    {
        copyOnWriteArraySet.remove(this);
    }
    @OnMessage
    public void onMessage(String message)
    {

    }
    public void  sendMessage(String message)
    {
        for (WebSocket webSocket : copyOnWriteArraySet) {
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
