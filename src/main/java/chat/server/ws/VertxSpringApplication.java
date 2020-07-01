package chat.server.ws;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

import javax.annotation.PostConstruct;

/**
 * Деплой Vert.X сервера
 *
 * @author sbrf-Smirnov-VA
 * @created on 08.03.2020
 */
public class VertxSpringApplication {

	private Verticle verticle;

	public VertxSpringApplication(Verticle verticle) {
		this.verticle = verticle;
	}

	@PostConstruct
	private void deploy()
	{
		Vertx.vertx().deployVerticle(verticle);
	}
}
