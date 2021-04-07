package com.haiyan.deflower.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author haiyan
 **/
@Slf4j
@ServerEndpoint("/ws/{userId}")
@Component
public class WebSocketServer {

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的
     */
    private static int onlineCount = 0;

    /**
     * concurrent 包的线程安全Set，用来存放每个客户端对应的 myWebSocket对象
     * 根据userId来获取对应的 WebSocket
     */
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 接收 sid
     */
    private String userId = "";


    /**
     * 连接建立成功调用的方法
     *
     * @param session
     * @param userId
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;

        webSocketMap.put(userId, this);
        log.info("webSocketMap -> " + JSON.toJSONString(webSocketMap));

        addOnlineCount(); // 在线数 +1
        log.info("有新窗口开始监听:" + userId + ",当前在线人数为" + getOnlineCount());

        try {
            sendMessage(JSON.toJSONString("连接成功"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApiException("websocket IO异常！！！！");
        }

    }

    /**
     * 关闭连接
     */

    @OnClose
    public void onClose() {
        if (webSocketMap.get(this.userId) != null) {
            webSocketMap.remove(this.userId);
            subOnlineCount(); // 人数 -1
            log.info("有一连接关闭，当前在线人数为：" + getOnlineCount());
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口" + userId + "的信息：" + message);

        if (StringUtils.isNotBlank(message)) {
            try {
                // 解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                // 追加发送人（防窜改）
                jsonObject.put("fromUserId", this.userId);
                jsonObject.put("sendTime",new Date());
                String toUserId = jsonObject.getString("toUserId");
                // 传送给对应 toUserId 用户的 WebSocket
                if (StringUtils.isNotBlank(toUserId) && webSocketMap.containsKey(toUserId)) {
                    webSocketMap.get(toUserId).sendMessage(jsonObject.toJSONString());
                } else {
                    // 否则不在这个服务器上，发送到 MySQL 或者 Redis
                    log.info("请求的userId：" + toUserId + "不在该服务器上");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误：" + this.userId + "，原因：" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 群发自定义消息
     *
     * @param message
     * @param userId
     * @throws IOException
     */
    public static void sendInfo(String message, @PathParam("userId") String userId) throws IOException {

        // 遍历集合，可设置为推送给指定sid，为 null 时发送给所有人
        Iterator entrys = webSocketMap.entrySet().iterator();
        while (entrys.hasNext()) {
            Map.Entry entry = (Map.Entry) entrys.next();

            if (userId == null) {
                webSocketMap.get(entry.getKey()).sendMessage(message);
                log.info("发送消息到：" + entry.getKey() + "，消息：" + message);
            } else if (entry.getKey().equals(userId)) {
                webSocketMap.get(entry.getKey()).sendMessage(message);
                log.info("发送消息到：" + entry.getKey() + "，消息：" + message);
            }

        }


    }

    private static synchronized int getOnlineCount() {
        return onlineCount;
    }

    private static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    private static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}