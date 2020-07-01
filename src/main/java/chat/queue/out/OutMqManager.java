package chat.queue.out;

import chat.queue.DefaultMQConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Collections;

/**
 * Менеджер исходящей очереди
 *
 * @author sbrf-Smirnov-VA
 * @created on 09.03.2020
 */
public class OutMqManager {

	private KafkaProducer<String, String> kafkaProducer;
	private KafkaConsumer<String, String> consumer;
	private String outTopic;

	public OutMqManager(DefaultMQConfig config) {
		this.kafkaProducer = new KafkaProducer<>(config.getProducerProperties());;

		this.consumer = new KafkaConsumer<>(config.getConsumerProperties());
		this.consumer.subscribe(Collections.singleton(config.getDefaultInTopic()));

		this.outTopic = config.getDefaultOutTopic();
	}

	public void init() {
		new Thread(new OutThreadConsumer(consumer, kafkaProducer, outTopic)).start();
	}
}
