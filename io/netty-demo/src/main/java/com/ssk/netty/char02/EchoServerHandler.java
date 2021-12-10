package com.ssk.netty.char02;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

/**
 * @author 惊云
 * @date 2021/12/6 13:49
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

     @Override
     public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
          ByteBuf in = (ByteBuf)msg;
          System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));
          ctx.write(in);
     }

     @Override
     public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
          ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                  .addListener(ChannelFutureListener.CLOSE);
     }

     @Override
     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
         cause.printStackTrace();
         ctx.close();
     }

}
