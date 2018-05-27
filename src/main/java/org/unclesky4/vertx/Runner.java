package org.unclesky4.vertx;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * @ClassName: Runner
 * @Description:
 * @author: unclesky4
 * @date: May 25, 2018 10:30:21 PM
 */
public class Runner {

	private static final String WEB_EXAMPLES_DIR = "vertx";
	private static final String WEB_EXAMPLES_JAVA_DIR = WEB_EXAMPLES_DIR + "/src/main/java/";
	
	private volatile static Vertx vertx;
	
	public static Vertx getVertx() {
		if (vertx == null) {
			return Vertx.vertx();
		}
		return vertx;
	}

	public static void runClustered(Class clazz) {
		run(WEB_EXAMPLES_JAVA_DIR, clazz, new VertxOptions().setClustered(true), null);
	}

	public static void run(Class clazz) {
		run(WEB_EXAMPLES_JAVA_DIR, clazz, new VertxOptions().setClustered(false), null);
	}

	public static void run(Class clazz, DeploymentOptions options) {
		run(WEB_EXAMPLES_JAVA_DIR, clazz, new VertxOptions().setClustered(false), options);
	}

	public static void run(String exampleDir, Class clazz, VertxOptions options,
			DeploymentOptions deploymentOptions) {
		run(exampleDir + clazz.getPackage().getName().replace(".", "/"), clazz.getName(), options,
				deploymentOptions);
	}

	public static void run(String exampleDir, String verticleID, VertxOptions options,
			DeploymentOptions deploymentOptions) {
		if (options == null) {
			// Default parameter
			options = new VertxOptions();
		}
		// Smart cwd detection

		// Based on the current directory (.) and the desired directory
		// (exampleDir), we try to compute the vertx.cwd
		// directory:
		try {
			// We need to use the canonical file. Without the file name is .
			File current = new File(".").getCanonicalFile();
			if (exampleDir.startsWith(current.getName()) && !exampleDir.equals(current.getName())) {
				exampleDir = exampleDir.substring(current.getName().length() + 1);
			}
		} catch (IOException e) {
			// Ignore it.
		}

		System.setProperty("vertx.cwd", exampleDir);
		Consumer<Vertx> runner = vertx -> {
			try {
				if (deploymentOptions != null) {
					vertx.deployVerticle(verticleID, deploymentOptions);
				} else {
					vertx.deployVerticle(verticleID);
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		};
		if (options.isClustered()) {
			Vertx.clusteredVertx(options, res -> {
				if (res.succeeded()) {
					vertx = res.result();
					runner.accept(vertx);
				} else {
					res.cause().printStackTrace();
				}
			});
		} else {
			vertx = Vertx.vertx(options);
			runner.accept(vertx);
		}
	}
}
