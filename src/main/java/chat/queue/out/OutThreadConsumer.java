package chat.queue.out;

import chat.queue.ConsumerThread;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * Потребитель исходящей очереди
 *
 * @author sbrf-Smirnov-VA
 * @created on 09.03.2020
 */
public class OutThreadConsumer extends ConsumerThread {

	private static final String KAFKA_PREFIX = "kafka";
	private KafkaProducer<String, String> producer;
	private String topic;

	public OutThreadConsumer(KafkaConsumer<String, String> consumer, KafkaProducer<String, String> producer, String topic) {
		super(consumer);
		this.producer = producer;
		this.topic = topic;
	}

	@Override
	protected void handle(String message) {
		producer.send(new ProducerRecord<>(topic, KAFKA_PREFIX + message));
	}
}
