package chat.queue;

import chat.app.ApplicationConfig;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурация для двух очередей (in и out)
 *
 * @author sbrf-Smirnov-VA
 * @created on 09.03.2020
 */
public class DefaultMQConfig {

	private Map<String, Object> producerProperties;
	private Map<String, Object> consumerProperties;
	private String defaultInTopic;
	private String defaultOutTopic;

	public DefaultMQConfig(ApplicationConfig config) {

		producerProperties = new HashMap<>(4);
		producerProperties.put(CommonClientConfigs.GROUP_ID_CONFIG, config.getKafkaGroupId());
		producerProperties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, config.getKafkaBootStrapServers());
		producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		consumerProperties = new HashMap<>(4);
		consumerProperties.put(CommonClientConfigs.GROUP_ID_CONFIG, config.getKafkaGroupId());
		consumerProperties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, config.getKafkaBootStrapServers());
		consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

		defaultInTopic = config.getDefaultInTopic();
		defaultOutTopic = config.getDefaultOutTopic();
	}

	public Map<String, Object> getProducerProperties() {
		return producerProperties;
	}

	public String getDefaultInTopic() {
		return defaultInTopic;
	}

	public String getDefaultOutTopic() {
		return defaultOutTopic;
	}

	public Map<String, Object> getConsumerProperties() {
		return consumerProperties;
	}
}
