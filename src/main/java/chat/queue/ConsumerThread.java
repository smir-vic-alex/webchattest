package chat.queue;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * базовый класс потребителя
 *
 * @author sbrf-Smirnov-VA
 * @created on 09.03.2020
 */
public abstract class ConsumerThread implements Runnable {

	protected static final String ERROR = "Ошибка";
	protected static Logger LOGGER = LoggerFactory.getLogger(ConsumerThread.class);
	protected KafkaConsumer<String, String> consumer;

	private static final String MESSAGE = "Topic - %s, Partition - %d, Value: %s";
	private static final int MILLIS = 10;

	private final AtomicBoolean isActive = new AtomicBoolean(false);

	public ConsumerThread(KafkaConsumer<String, String> consumer) {
		this.consumer = consumer;
	}

	@Override
	public void run() {
		isActive.set(true);
		try {
			while (isActive.get()) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(MILLIS));
				for (ConsumerRecord<String, String> record : records) {
					LOGGER.info(String.format(MESSAGE, record.topic(), record.partition(), record.value()));
					handle(record.value());
				}
			}
		} catch (Exception e) {
			LOGGER.error(ERROR, e);
		} finally {

			consumer.close();
		}
	}

	public void stop() {
		isActive.set(false);
	}

	protected abstract void handle(String message);
}
