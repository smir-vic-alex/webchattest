package chat.hibernate;

import org.hibernate.Session;

/**
 * @author sbrf-Smirnov-VA
 * @created on 09.03.2020
 */
public interface HibernateAction<T> {
    T execute(Session session);
}
