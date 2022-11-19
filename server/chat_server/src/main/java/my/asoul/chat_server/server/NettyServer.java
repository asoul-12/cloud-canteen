package my.asoul.chat_server.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import my.asoul.chat_server.server.handler.NettyServerHandlerInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author 4512
 * @date 2022/10/8 20:34
 */
@Component
@Slf4j
public class NettyServer {

    @Value("${netty.port}")
    private int port;

    @Autowired
    private NettyServerHandlerInitializer nettyServerHandlerInitializer;

    private NioEventLoopGroup master = new NioEventLoopGroup(1);
    private NioEventLoopGroup worker = new NioEventLoopGroup();

    private Channel channel;

    @PostConstruct
    public void start() throws InterruptedException {
        ChannelFuture future = new ServerBootstrap()
                .group(master, worker)
                .channel(NioServerSocketChannel.class)
                // 设置服务端 accept 队列大小
                .option(ChannelOption.SO_BACKLOG, 1024)
                // TCP Keepalive 机制，实现 TCP 层级的心跳保活功能
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                // 允许较小的数据包的发送，降低延迟
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(nettyServerHandlerInitializer)
                .bind(port)
                .sync();
        if (future.isSuccess()) {
            log.info("[start][Netty Server 启动在 {} 端口]", port);
        }

    }

    @PreDestroy
    public void shutdown() {
        master.shutdownGracefully();
        worker.shutdownGracefully();
        log.info("[info][Netty Server shutdown]");
    }


}
