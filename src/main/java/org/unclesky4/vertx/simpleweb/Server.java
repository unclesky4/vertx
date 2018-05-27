package org.unclesky4.vertx.simpleweb;

import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.unclesky4.vertx.Runner;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.templ.ThymeleafTemplateEngine;

/**
 * @ClassName: Server 
 * @Description: 普通Http Web 服务端
 * @author: unclesky4
 * @date: May 25, 2018 10:28:45 PM
 */
public class Server extends AbstractVerticle {
	
	public static void main(String[] args) {
		Runner.run(Server.class);
	}

	@Override
	public void start() throws Exception {
		
		//Router（路由器） -- 包含多个Route(路由),定义http请求的分发
		Router router = Router.router(Runner.getVertx());
		
		//定义模版引擎
		ThymeleafTemplateEngine templateEngine = ThymeleafTemplateEngine.create();
		// 定时模板解析器,表示从类加载路径下找模板
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        // 设置模板的前缀，我们设置的是templates目录
        templateResolver.setPrefix("templates");
        // 设置后缀为.html文件
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCacheable(false);
        templateEngine.getThymeleafTemplateEngine().setTemplateResolver(templateResolver);
		
		router.route("/").handler(RoutingContext -> {
			RoutingContext.response().putHeader("content-type", "text/html").end("Hello World!");
		});
		
		router.route("/index").handler(routingContext -> {
			routingContext.put("username", "huang zhibiao");
			templateEngine.render(routingContext, "", "/index", res -> {
                if (res.succeeded()) {
                    routingContext.response().putHeader("Content-Type", "text/html").end(res.result());
                } else {
                    routingContext.fail(res.cause());
                }
            });
		});
		
		router.route("/assets/*").handler(StaticHandler.create("assets"));
		
		Runner.getVertx().createHttpServer().requestHandler(router::accept).listen(8080);
		
	}

}
