package chat.dao;

/**
 * pojo сообщения
 *
 * @author sbrf-Smirnov-VA
 * @created on 09.03.2020
 */
public class Message {
	private Long id;
	private String value;

	public Message() {
	}

	public Message(String value) {
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
