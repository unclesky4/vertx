package org.unclesky4.vertx.eventbus;

import io.vertx.core.AbstractVerticle;

/**
 * @ClassName: Service01 
 * @Description: 创建服务，处理相应的事件,也要继承AbstractVerticle
 * @author: unclesky4
 * @date: May 27, 2018 11:15:14 AM
 */
public class Service01 extends AbstractVerticle {
	public static final String URL01="VERTX_HELLO_SERVER01";
    public static final String URL02="VERTX_HELLO_SERVER02";
    
    public void start(){
    	System.out.println(vertx.toString());
    	
        vertx.eventBus().consumer(URL01, 
                msg -> {
                    System.out.println(URL01+" 接收到消息"+msg.body()); //处理请求消息
                    msg.reply(URL01+"成功处理消息");  //返回给result
                  }
                );
        vertx.eventBus().consumer(URL02, 
                msg -> {
                    System.out.println(URL02+" 接收到消息"+msg.body());
                    msg.reply(URL02+"成功处理消息");  //返回给result
                }
        );
    }
}
