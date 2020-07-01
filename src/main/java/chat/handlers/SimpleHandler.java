package chat.handlers;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.ServerWebSocket;

/**
 * Прстой хендлер
 *
 * @author sbrf-Smirnov-VA
 * @created on 10.03.2020
 */
public class SimpleHandler implements Handler<Message<Object>> {

	private ServerWebSocket event;

	public SimpleHandler(ServerWebSocket event) {
		this.event = event;
	}

	@Override
	public void handle(Message<Object> message) {
		event.writeTextMessage((String) message.body());
	}
}
