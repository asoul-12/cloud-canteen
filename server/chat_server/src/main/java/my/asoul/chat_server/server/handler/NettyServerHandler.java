package my.asoul.chat_server.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 4512
 * @date 2022/10/1 11:34
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private NettyServerManager manager;



    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        manager.remove(ctx.channel());
    }

    /**
     * catch exception - pipeline
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("[exceptionCaught][连接({}) 发生异常:{}]", ctx.channel().id(), cause);
        ctx.channel().close();
    }
}
