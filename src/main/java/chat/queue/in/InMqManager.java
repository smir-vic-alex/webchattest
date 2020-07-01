package chat.queue.in;

import chat.queue.DefaultMQConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Менеджер входящей очереди
 *
 * @author sbrf-Smirnov-VA
 * @created on 09.03.2020
 */
public class InMqManager {
	private static Logger LOGGER = LoggerFactory.getLogger(InMqManager.class);
	private KafkaProducer<String, String> kafkaProducer;
	private KafkaConsumer<String, String> consumer;
	private static BlockingQueue<String> queue = new LinkedBlockingQueue<>();;
	private String inTopic;

	public InMqManager(DefaultMQConfig config) {
		this.kafkaProducer = new KafkaProducer<>(config.getProducerProperties());;

		this.consumer = new KafkaConsumer<>(config.getConsumerProperties());
		this.consumer.subscribe(Collections.singleton(config.getDefaultOutTopic()));
		this.inTopic = config.getDefaultInTopic();
	}

	public void init() {
		new Thread(new InConsumerThread(consumer, queue)).start();
	}

	public void send(String message) {
		kafkaProducer.send(new ProducerRecord<>(inTopic, message));
	}

	public String poll() {
		try {
			return queue.take();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
