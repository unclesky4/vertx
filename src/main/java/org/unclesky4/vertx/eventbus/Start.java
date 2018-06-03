package org.unclesky4.vertx.eventbus;

import io.vertx.core.Vertx;

/**
 * 
 * @ClassName: Start 
 * @Description: 测试消息总线
 * @author: unclesky4
 * @date: Jun 3, 2018 4:22:16 PM
 */
public class Start {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new Server01());
		vertx.deployVerticle(new Service01());
		vertx.deployVerticle(new Service02());
	}

}
