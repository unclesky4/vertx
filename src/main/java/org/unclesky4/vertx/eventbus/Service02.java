package org.unclesky4.vertx.eventbus;

import io.vertx.core.AbstractVerticle;

public class Service02 extends AbstractVerticle {
	public static final String URL02="VERTX_HELLO_SERVER03";
    
    public void start(){
    	System.out.println(vertx.toString());
    	
        vertx.eventBus().consumer(URL02,   //处理总线发送过来的相应的事件
                msg -> {                   //msg消息发送过来的消息
                    System.out.println(URL02 + "接受到消息："+msg.body()); //处理请求消息
                    msg.reply(URL02 + "成功处理消息 ");  //对事件进行返回结果，相当于事件中的result
                  }
                );
    }
}
