package chat.dao;


/**
 * Сервис по сохранению сообщений в бд
 *
 * @author sbrf-Smirnov-VA
 * @created on 09.03.2020
 */
public class ChatService extends BusinessService {

	public void addOrUpdate(Message message) {
		saveOrUpdate(message, Message.class);
	}
}
