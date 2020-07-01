package chat.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Конфигурация приложения
 *
 * @author sbrf-Smirnov-VA
 * @created on 09.03.2020
 */
public class ApplicationConfig {

	private static Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);
	private static final String ERROR_MESSAGE = "Не удалось загрузить настройки";
	private static final String PORT_KEY = "vert.x.server.port";
	private static final String GROUP_ID = "kafka.group.id";
	private static final String BOOTSTRAP_SERVERS = "kafka.bootstrap.servers";
	public static final String DEFAULT_IN_TOPIC = "kafka.default.in.topic";
	public static final String DEFAULT_OUT_TOPIC = "kafka.default.out.topic";
	public static final String CONSUMER_ADDRESS_KEY = "vert.x.default.consumer.address";

	private static Map<String, String> properties;
	private String fileName;

	public ApplicationConfig(String fileName) {
		this.fileName = fileName;
	}

	public Map<String, String> getProperties() {

		if (properties == null) {
			properties = loadPropertiesFromFile();
		}

		return properties;
	}

	public Map<String, String> loadPropertiesFromFile() {

		Map<String, String> map = new HashMap<>();

		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {

			Properties properties = new Properties();
			properties.load(inputStream);

			for (Map.Entry<Object, Object> entry : properties.entrySet()) {
				map.put((String) entry.getKey(), (String) entry.getValue());
			}

			return map;
		} catch (Exception e) {
			LOGGER.error(ERROR_MESSAGE, e);
			throw new RuntimeException(ERROR_MESSAGE, e);
		}
	}

	public Integer getVertXPort() {
		return Integer.valueOf(getProperties().get(PORT_KEY));
	}

	public String getKafkaGroupId() {
		return getProperties().get(GROUP_ID);
	}

	public String getKafkaBootStrapServers() {
		return getProperties().get(BOOTSTRAP_SERVERS);
	}

	public String getDefaultInTopic() {
		return getProperties().get(DEFAULT_IN_TOPIC);
	}

	public String getDefaultOutTopic() {
		return getProperties().get(DEFAULT_OUT_TOPIC);
	}

	public String getConsumerDefaultAddress() {
		return getProperties().get(CONSUMER_ADDRESS_KEY);
	}
}
