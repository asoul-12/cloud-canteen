package my.asoul.chat_server.server.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import my.asoul.chat_server.entity.Invocation;
import my.asoul.chat_server.entity.Message;
import my.asoul.chat_server.utils.TokenUtil;
import my.asoul.takeout.model.base.BaseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author 4512
 * @date 2022/10/1 14:40
 */
@Component
@Slf4j
@ChannelHandler.Sharable
public class AuthHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private NettyServerManager manager;

    /**
     * 鉴权处理器
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *            belongs to
     * @param msg the message to handle
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) {
        String token = extractToken(msg.uri());
        if (token != null && Boolean.TRUE.equals(redisTemplate.hasKey(token))) {
            log.info("[auth][{}]验证成功 移除Auth", ctx.channel().id());
            ctx.pipeline().remove(AuthHandler.class);
            // 使用user的ID标识channel
            BaseUser baseUser = TokenUtil.analysisToken(token);
            manager.add(baseUser.getId(), ctx.channel());
            ctx.fireChannelRead(msg.setUri("/webSocket").retain());
        } else {
            log.warn("[auth][{}]验证失败", ctx.channel().id());
            Invocation invocation = new Invocation("AUTH", new Message().setContent("验证失败"));
            ctx.writeAndFlush(JSON.toJSONString(invocation));
        }
    }


    /**
     * 从uri中提取token
     *
     * @param uri 路径
     * @return token
     */
    public String extractToken(String uri) {
        String token = null;
        String webSocketPath = "/webSocket";
        if (uri.startsWith(webSocketPath)) {
            int index = uri.lastIndexOf("token");
            token = uri.substring(index + 6);
        }
        return token;
    }


}
