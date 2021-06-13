package org.yeauty.support;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.util.MultiValueMap;
import org.yeauty.annotation.*;
import org.yeauty.pojo.Session;

import java.io.IOException;
import java.util.Map;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote WebSocketServerTemplate description：模板类
 * <br>使用时继承此类，并且在类上添加{@link ServerEndpoint}注解
 * <br>该类只是提供对应注解的修饰方法可以使用的形参
 * <br> onOpen、onClose、onError、onMessage 必须由子类实现
 * @since 2021/6/13 11:41
 */
public abstract class WebSocketServerTemplate {
    @BeforeHandshake
    public void handshake(Session session, HttpHeaders headers, @RequestParam String req, @RequestParam MultiValueMap<?, ?> reqMap, @PathVariable String arg, @PathVariable Map<?, ?> pathMap) {
        System.out.println("do something before handshake");
    }

    @OnOpen
    public abstract void onOpen(Session session, HttpHeaders headers, @RequestParam String req, @RequestParam MultiValueMap<?, ?> reqMap, @PathVariable String arg, @PathVariable Map<?, ?> pathMap);

    @OnClose
    public abstract void onClose(Session session) throws IOException;

    @OnError
    public abstract void onError(Session session, Throwable throwable);

    @OnMessage
    public abstract void onMessage(Session session, String message);

    @OnBinary
    public void onBinary(Session session, byte[] bytes) {
        System.out.println("default implement,if you want to use this method,you should override this method");
    }

    @OnEvent
    public void onEvent(Session session, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    System.out.println("read idle");
                    break;
                case WRITER_IDLE:
                    System.out.println("write idle");
                    break;
                case ALL_IDLE:
                    System.out.println("all idle");
                    break;
                default:
                    break;
            }
        }
    }
}
