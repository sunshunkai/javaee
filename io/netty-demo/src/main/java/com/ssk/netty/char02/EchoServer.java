package com.ssk.netty.char02;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author 惊云
 * @date 2021/12/6 13:55
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port){
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 8088;
        new EchoServer(port).start();
    }

    public void start() throws InterruptedException {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(group).channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(serverHandler);
                        }
                    });

            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();
        }
    }
}
