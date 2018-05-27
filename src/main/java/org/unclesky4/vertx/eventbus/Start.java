package org.unclesky4.vertx.eventbus;

import io.vertx.core.Vertx;

public class Start {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new Server01());
		vertx.deployVerticle(new Service01());
		vertx.deployVerticle(new Service02());
	}

}
