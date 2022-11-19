package my.asoul.chat_server.server.handler;


import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import my.asoul.chat_server.config.JacksonObjectMapper;
import my.asoul.chat_server.entity.Invocation;
import my.asoul.chat_server.entity.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author 4512
 * @date 2022/10/1 18:17
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class MessageDispatcher extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Autowired
    private NettyServerManager manager;

    @Autowired
    private JacksonObjectMapper mapper;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 解析message协议体
        Invocation invocation = mapper.readValue(msg.text(), Invocation.class);
        String type = invocation.getType();
            Message message = invocation.getMessage();
            Long receiveId = message.getReceiveUserId();
            // 加工信息
            message.setSendTime(LocalDateTime.now());
            invocation.setMessage(message);
            // 信息队列
            rocketMQTemplate.convertAndSend("test", invocation);
            // TODO 组装响应体 （责任链）
            String json = mapper.writeValueAsString(invocation);
            TextWebSocketFrame frame = new TextWebSocketFrame(json);
            if (manager.send(receiveId, frame)) {
                log.info("[product][chat message]{}", json);
            }
    }
}


