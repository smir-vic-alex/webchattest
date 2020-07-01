package chat.handlers;

import chat.app.ApplicationConfig;
import chat.dao.ChatService;
import chat.queue.in.InMqManager;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.ServerWebSocket;

/**
 * WS хендлер
 *
 * @author sbrf-Smirnov-VA
 * @created on 09.03.2020
 */
public class WSHandler implements Handler<ServerWebSocket> {

	private String consumerAddress;
	private Vertx vertx;
	private ChatService chatService;
	private InMqManager inMqManager;

	public WSHandler(InMqManager inMqManager, ApplicationConfig config, ChatService chatService) {
		this.inMqManager = inMqManager;
		this.consumerAddress = config.getConsumerDefaultAddress();
		this.chatService = chatService;
	}

	@Override
	public void handle(ServerWebSocket event) {
		bus().consumer(consumerAddress, message -> new SimpleHandler(event).handle(message));
		bus().consumer(consumerAddress, message -> new DBHandler(getVertx(), chatService, event).handle(message));
		bus().consumer(consumerAddress, message -> new MQHandler(getVertx(), inMqManager, event).handle(message));

		event.textMessageHandler(message -> bus().publish(consumerAddress, message));
	}

	public void setVertx(Vertx vertx) {
		this.vertx = vertx;
	}

	public Vertx getVertx() {
		return vertx;
	}

	private EventBus bus() {
		return getVertx().eventBus();
	}
}
