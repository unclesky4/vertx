package org.unclesky4.vertx.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

/**
 * @ClassName: Server01 
 * @Description: 创建Server.java，主要进行路由功能，将请求转发给服务
 * @author: unclesky4
 * @date: May 27, 2018 11:15:37 AM
 */
public class Server01 extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		Router router=Router.router(vertx);
        
        /*vertx.setPeriodic(1000, id->{
            	System.out.println("timer");  //每1000毫秒，打印一次，也就是说1000毫秒的逻辑执行完成，后面的就会被触发
        	}
        );*/
        
        router.routeWithRegex("/sp1").handler(  //对url请求进行拦截并处理
		    //ctx应用上下文
		    ctx -> vertx.eventBus().<String> send(  //vertx.eventBus()事件总线，可以与参数里面的地址进行通信，能够在所有vertx之间进行通信
		                                            //<String> 向服务发送的Message里面包含的类型。可以是对象等
		            Service01.URL01,                //请求转发的地址，交由相应的服务对事件进行处理
		            "Hello Vertx1",                 //Message中的内容。有上面的String决定<String>
		            result -> {                     //服务将事件处理完成，返回的结果
		                if(result.succeeded())   //处理成功后执行的逻辑
		                    System.out.println(result.result().body());
		                //ctx.reroute("");可以进行二级路由
		                ctx.response()  //响应，和面可以跟响应内容，如setstatus()等
		                .end();
		            }
		    )
		);
        
        router.routeWithRegex("/sp2").handler(
	        ctx -> vertx.eventBus().<String> send(
	                Service01.URL02,
	                "Hello Vertx2",
	                result -> {
	                    if(result.succeeded())   //如果成功，后台将会通知来执行这个。
	                        System.out.println(result.result().body());
	                    ctx.response()
	                    .end();
	                }
	        )
	    );
        
        router.routeWithRegex("/sp3").handler(
                ctx -> vertx.eventBus().<String> send(
                        Service02.URL02,
                        "Hello Vertx3",
                        result -> {
                            if(result.succeeded())   //如果成功，后台将会通知来执行这个。
                                System.out.println(result.result().body());
                            System.out.println("testi3");
                            ctx.response()
                            .end();
                        }
                        )
                );
        
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
	}
}
