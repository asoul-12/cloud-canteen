package my.asoul.chat_server.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import my.asoul.chat_server.entity.Invocation;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 4512
 * @date 2022/10/1 12:33
 */
@Slf4j
@Component
public class NettyServerManager {

    public static final AttributeKey<Long> CHANNEL_ATTR_KEY_USER = AttributeKey.newInstance("user");

    private ConcurrentHashMap<Long, Channel> userChannels = new ConcurrentHashMap<>();

    private ConcurrentHashMap<ChannelId, Long> channels = new ConcurrentHashMap<>();

    public void add(Long userId, Channel channel) {
        channel.attr(CHANNEL_ATTR_KEY_USER).set(userId);
        channels.put(channel.id(), userId);
        userChannels.put(userId, channel);
        log.info("[add][一个AUTH连接({})加入]", channel.id());
    }

    public void remove(Channel channel) {
        if (channel.hasAttr(CHANNEL_ATTR_KEY_USER)) {
            Long userId = channels.get(channel.id());
            channels.remove(channel.id());
            userChannels.remove(userId);
            log.info("[remove][一个连接({})离开]", channel.id());
        } else {
            log.info("[remove][一个未通过AUTH的连接({})离开]", channel.id());
        }
    }


    /**
     * 向指定用户发送消息
     *
     * @param userId    用户
     * @param textFrame 消息体
     */
    public Boolean send(Long userId, TextWebSocketFrame textFrame) {
        // 获得用户对应的 Channel
        Channel channel = userChannels.get(userId);
        if (channel == null) {
            log.error("[send][连接{}不存在][将对离线信息进行持久化]",userId);
            return false;
        }
        if (!channel.isActive()) {
            log.error("[send][连接({})未激活][将对离线信息进行持久化]", channel.id());
            return false;
        }
        // 发送消息
        channel.writeAndFlush(textFrame);
        return true;
    }


    /**
     * 向所有用户发送消息
     *
     * @param invocation 消息体
     */
    public void sendAll(Invocation invocation) {
        for (Channel channel : userChannels.values()) {
            if (!channel.isActive()) {
                log.error("[send][连接({})未激活]", channel.id());
                return;
            }
            // 发送消息
            channel.writeAndFlush(invocation);
        }
    }
}
