package chat.server.ws;

import chat.app.ApplicationConfig;
import chat.handlers.WSHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;


/**
 * Vert.X Сервер
 *
 * @author sbrf-Smirnov-VA
 * @created on 08.03.2020
 */
public class VertXServerWS extends AbstractVerticle {

	private WSHandler wsHandler;
	private Integer port;

	public VertXServerWS(WSHandler wsHandler, ApplicationConfig config) {
		this.wsHandler = wsHandler;
		this.port = config.getVertXPort();
	}

	@Override
	public void start(Promise<Void> fut) {
		getVertx()
				.createHttpServer()
				.webSocketHandler(wsHandler)
				.listen(port, resultHandler(fut));

		wsHandler.setVertx(getVertx());
	}

	private Handler<AsyncResult<HttpServer>> resultHandler(Promise<Void> fut) {
		return result -> {
			if (result.succeeded()) {
				fut.complete();
			} else {
				fut.fail(result.cause());
			}
		};
	}
}
