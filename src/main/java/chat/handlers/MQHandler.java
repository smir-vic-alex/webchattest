package chat.handlers;

import chat.queue.in.InMqManager;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.ServerWebSocket;

/**
 * Обработчик очереди
 *
 * @author sbrf-Smirnov-VA
 * @created on 10.03.2020
 */
public class MQHandler implements Handler<Message<Object>> {

	private Vertx vertx;
	private InMqManager inMqManager;
	private ServerWebSocket event;

	public MQHandler(Vertx vertx, InMqManager inMqManager, ServerWebSocket event) {
		this.vertx = vertx;
		this.inMqManager = inMqManager;
		this.event = event;
	}

	@Override
	public void handle(Message<Object> message) {
		vertx.executeBlocking(getPromiseHandler(message), getAsyncResultHandler());
	}

	private Handler<AsyncResult<Object>> getAsyncResultHandler() {
		return res -> {
			if (res.succeeded()) {
				event.writeTextMessage(res.result().toString());
			} else {
				res.cause().printStackTrace();
			}
		};
	}

	private Handler<Promise<Object>> getPromiseHandler(Message<Object> message) {
		return promise -> {
			inMqManager.send(message.body().toString());
			String answer = inMqManager.poll();
			promise.complete(answer);
		};
	}
}
