package chat.dao;

import chat.hibernate.HibernateExecutor;

/**
 * Базовый сервис работы с бд
 *
 * @author sbrf-Smirnov-VA
 * @created on 09.03.2020
 */
public class BusinessService {

	public <T> void saveOrUpdate(Object object, Class<T> type){
		new HibernateExecutor<T>().execute((session) ->
				{
					session.saveOrUpdate(object);
					return null;
				}
		);
	}
}
