package chat.queue.in;

import chat.queue.ConsumerThread;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.concurrent.BlockingQueue;

/**
 * Потребитель для входящей очереди
 *
 * @author sbrf-Smirnov-VA
 * @created on 09.03.2020
 */
public class InConsumerThread extends ConsumerThread {

	private BlockingQueue<String> queue;

	public InConsumerThread(KafkaConsumer<String, String> consumer, BlockingQueue<String> queue) {
		super(consumer);
		this.queue = queue;
	}

	@Override
	protected void handle(String message) {
		try {
			queue.put(message);
		} catch (InterruptedException e) {
			LOGGER.error(ERROR, e);
		}
	}
}
