package chat.handlers;

import chat.dao.ChatService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.ServerWebSocket;

/**
 * Обработчик БД
 *
 * @author sbrf-Smirnov-VA
 * @created on 10.03.2020
 */
public class DBHandler implements Handler<Message<Object>> {

	private Vertx vertx;
	private ChatService chatService;
	private ServerWebSocket event;

	public DBHandler(Vertx vertx, ChatService chatService, ServerWebSocket event) {
		this.vertx = vertx;
		this.chatService = chatService;
		this.event = event;
	}

	@Override
	public void handle(Message<Object> message) {
		vertx.executeBlocking(getPromiseHandler(message), getAsyncResultHandler());
	}

	private Handler<AsyncResult<Object>> getAsyncResultHandler() {
		return res -> {
			if (res.succeeded()) {
				event.writeTextMessage(((chat.dao.Message) res.result()).getId().toString());
			} else {
				res.cause().printStackTrace();
			}
		};
	}

	private Handler<Promise<Object>> getPromiseHandler(Message<Object> message) {
		return promise -> {
			chat.dao.Message msg = new chat.dao.Message(message.body().toString());
			chatService.addOrUpdate(msg);
			promise.complete(msg);
		};
	}
}
