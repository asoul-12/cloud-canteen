package my.asoul.chat_server.server.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 4512
 * @date 2022/10/1 9:59
 */
@Component
public class NettyServerHandlerInitializer extends ChannelInitializer<NioSocketChannel> {

    @Autowired
    private AuthHandler authHandler;

    @Autowired
    private NettyServerHandler nettyServerHandler;

    @Autowired
    private MessageDispatcher messageDispatcher;


    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ch.pipeline().addLast(new LoggingHandler())
                // HTTP 请求解码和响应编码
                .addLast(new HttpServerCodec())
                // todo know what to do
                .addLast(new ChunkedWriteHandler())
                // HTTP 对象聚合完整对象
                .addLast(new HttpObjectAggregator(24 * 1024))
                //  使用redis鉴权
                .addLast(authHandler)
                // WebSocket支持
                .addLast(new WebSocketServerProtocolHandler("/webSocket"))
                // 服务端Handler
                .addLast(nettyServerHandler)
                // 消息处理
                .addLast(messageDispatcher);
    }
}
